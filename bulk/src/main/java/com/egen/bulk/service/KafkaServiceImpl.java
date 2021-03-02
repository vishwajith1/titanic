package com.egen.bulk.service;

import com.egen.bulk.entity.BaseOrder;
import com.egen.bulk.entity.Payment;
import com.egen.bulk.entity.SubOrder;
import com.egen.bulk.models.CreateOrderDTO;
import com.egen.bulk.models.ModifyOrderDTO;
import com.egen.bulk.repositories.BaseOrderRepository;
import com.egen.bulk.repositories.PaymentRepository;
import com.egen.bulk.repositories.SubOrderRepository;
import com.egen.bulk.utils.OrderStatus;
import com.egen.bulk.utils.Utils;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Log4j2
@Transactional
public class KafkaServiceImpl implements KafkaService {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    Gson gson;

    int counter = 0;
    int max_count = 2;

    Stack<String> createStack = new Stack<>();
    Stack<String> modifyStack = new Stack<>();
    Stack<String> cancelStack = new Stack<>();

    List<CreateOrderDTO> createOrderList = new ArrayList<>();
    List<ModifyOrderDTO> modifyOrderList = new ArrayList<>();
    List<Integer> cancelOrderList = new ArrayList<>();

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @Autowired
    private BaseOrderRepository baseOrderRepository;

    @Autowired
    private SubOrderRepository subOrderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public String publishMessage(String topic, Object anyObject) {
        String convertedString = null;
        try {
            convertedString = gson.toJson(anyObject);
            kafkaTemplate.send(topic, convertedString);
            log.info("Data Sent to Kafka: " + convertedString);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return convertedString;
    }

    @KafkaListener(topics = {"CREATE"}, groupId = "group-id", autoStartup = "false")
    public void listenCreate(String message) {
        log.info("Data Received From Kafka: " + message);
        createStack.push(message);
        this.checkCounterAndStopListener();
    }

    @KafkaListener(topics = {"MODIFY"}, groupId = "group-id", autoStartup = "false")
    public void listenModify(String message) {
        log.info("Data Received From Kafka: " + message);
        modifyStack.push(message);
        this.checkCounterAndStopListener();
    }

    @KafkaListener(topics = {"CANCEL"}, groupId = "group-id", autoStartup = "false")
    public void listenCancel(String message) {
        log.info("Data Received From Kafka: " + message);
        cancelStack.push(message);
        this.checkCounterAndStopListener();
    }

    @Scheduled(fixedDelay = 30 * 1000)
    public void scheduledMethod() throws InterruptedException {
        this.startListen();
        TimeUnit.SECONDS.sleep(10);
        this.stopListen();
    }

    public void startListen() {
        log.info("Started Listening : " + System.currentTimeMillis());
        kafkaListenerEndpointRegistry.start();
    }

    public void stopListen() {
        if (kafkaListenerEndpointRegistry.isRunning()) {
            kafkaListenerEndpointRegistry.stop();
            log.info("Stopped Listening : " + System.currentTimeMillis());
            this.startCapturing();
        }
    }

    public void checkCounterAndStopListener() {
        counter++;
        if (counter == max_count) {
            counter = 0;
            this.stopListen();
        }
    }

    public void startCapturing() {
        try {
            this.captureCreateRequests();
            this.captureModifyRequests();
            this.captureBulkRequests();
            this.processDataAndPersist();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void captureCreateRequests() throws InterruptedException {
        log.info("Bulk Create Started !");
        while (!createStack.isEmpty()) {
            createOrderList.add(gson.fromJson(createStack.pop(), CreateOrderDTO.class));
        }
    }

    public void captureModifyRequests() throws InterruptedException {
        log.info("Bulk Modify Started !");
        while (!modifyStack.isEmpty()) {
            modifyOrderList.add(gson.fromJson(modifyStack.pop(), ModifyOrderDTO.class));
        }
    }

    public void captureBulkRequests() throws InterruptedException {
        log.info("Bulk Cancel Started !");
        while (!cancelStack.isEmpty()) {
            cancelOrderList.add(gson.fromJson(cancelStack.pop(), Integer.class));
        }
    }

    public void processDataAndPersist() {
        List<SubOrder> subOrderIdsToBeDeleted = new ArrayList<>();
        List<SubOrder> subOrderList = new ArrayList<>();
        List<Payment> paymentList = new ArrayList<>();
        List<Integer> baseOrdersToBeCanceled = cancelOrderList;
        this.processCreateRequest(subOrderList, paymentList);
        this.processModifyRequest(subOrderList, subOrderIdsToBeDeleted);

        if(subOrderList.size() > 0) {
            subOrderRepository.saveAll(subOrderList);
        }
        if(subOrderIdsToBeDeleted.size() > 0) {
            subOrderRepository.deleteAll(subOrderIdsToBeDeleted);
        }
        if(paymentList.size() > 0) {
            paymentRepository.saveAll(paymentList);
        }
        if(baseOrdersToBeCanceled.size() > 0) {
            baseOrderRepository.cancelOrdersWithIds(baseOrdersToBeCanceled.stream().mapToInt(i -> i).toArray(), OrderStatus.CANCELED.getStatus());
        }

        this.cancelOrderList = new ArrayList<>();
        this.createOrderList = new ArrayList<>();
        this.modifyOrderList = new ArrayList<>();
    }

    public void processModifyRequest(List<SubOrder> subOrderList, List<SubOrder> subOrderIdsToBeDeleted) {
        this.modifyOrderList.stream().forEach(modifyOrderList -> {
            modifyOrderList.getSubOrdersMap().entrySet().stream().forEach(entity -> {
                Integer subOrderId = entity.getKey();
                if (entity.getValue().getQuantity() > 0) {
                    Optional<SubOrder> subOrder = subOrderRepository.findById(subOrderId);
                    subOrder.ifPresent(subOrderObj -> {
                        subOrderObj.setQuantity(entity.getValue().getQuantity());
                        subOrderObj.setShippingAddressId(entity.getValue().getShippingAddressId());
                        subOrderObj.setModifiedOn(Utils.getCurrentTimeInCST());
                        subOrderList.add(subOrderObj);
                    });
                } else {
                    SubOrder subOrder = new SubOrder();
                    subOrder.setId(subOrderId);
                    subOrderIdsToBeDeleted.add(subOrder);
                }
            });
        });
    }

    public void processCreateRequest(List<SubOrder> subOrderList, List<Payment> paymentList) {
        this.createOrderList.stream().forEach(createOrderDTO -> {
            BaseOrder baseOrder = new BaseOrder();
            baseOrder.setStatus(OrderStatus.INITIATED.getStatus());
            baseOrder.setCustomerId(createOrderDTO.getCustomerId());
            baseOrder.setCreatedOn(Utils.getCurrentTimeInCST());
            baseOrder = baseOrderRepository.saveAndFlush(baseOrder);
            Integer baseOrderId = baseOrder.getId();
            List<SubOrder> createdSubOrders = new ArrayList<>();
            createOrderDTO.getItems().entrySet().stream().forEach(
                    entity -> {
                        SubOrder subOrder = new SubOrder();
                        subOrder.setBaseOrderId(baseOrderId);
                        subOrder.setItemId(entity.getKey());
                        subOrder.setShippingAddressId(entity.getValue().getShippingAddressId());
                        subOrder.setQuantity(entity.getValue().getQuantity());
                        subOrder.setStatus(OrderStatus.INITIATED.getStatus());
                        subOrder.setCreatedOn(Utils.getCurrentTimeInCST());
                        subOrderList.add(subOrder);
                    });

            createOrderDTO.getPayments().stream().forEach(
                    paymentRef -> {
                        Payment payment = new Payment();
                        payment.setBaseOrderId(baseOrderId);
                        payment.setAmount(paymentRef.getAmount());
                        payment.setBillingAddressId(paymentRef.getBillingAddress());
                        payment.setPaymentMethod(paymentRef.getPayMode());
                        payment.setCreatedOn(Utils.getCurrentTimeInCST());
                        paymentList.add(payment);
                    }
            );
        });
    }
}
