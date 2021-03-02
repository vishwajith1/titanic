package com.egen.titanic.service;

import com.egen.titanic.apputils.OrderStatus;
import com.egen.titanic.apputils.PaymentStatus;
import com.egen.titanic.apputils.Utils;
import com.egen.titanic.entity.BaseOrder;
import com.egen.titanic.entity.Payment;
import com.egen.titanic.entity.SubOrder;
import com.egen.titanic.model.request.CreateOrderRequestDTO;
import com.egen.titanic.model.request.ModifyOrderRequestDTO;
import com.egen.titanic.model.response.OrderResponseDTO;
import com.egen.titanic.service.BaseOrderService;
import com.egen.titanic.service.PaymentService;
import com.egen.titanic.service.SubOrderService;
import com.egen.titanic.repository.BaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BaseOrderServiceImpl implements BaseOrderService {

    @Autowired
    BaseOrderRepository baseOrderRepository;

    @Autowired
    SubOrderService subOrderService;

    @Autowired
    PaymentService paymentService;

    @Override
    public OrderResponseDTO createOrder(CreateOrderRequestDTO orderRequestDTO) {
        BaseOrder baseOrder = new BaseOrder();
        baseOrder.setStatus(OrderStatus.INITIATED.getStatus());
        baseOrder.setCustomerId(orderRequestDTO.getCustomerId());
        baseOrder.setCreatedOn(Utils.getCurrentTimeInCST());
        baseOrder = saveOrUpdate(baseOrder);
        BaseOrder finalBaseOrder = baseOrder;

        List<SubOrder> createdSubOrders = new ArrayList<>();
        orderRequestDTO.getItems().entrySet().stream().forEach(
                entity -> {
                    SubOrder subOrder = new SubOrder();
                    subOrder.setBaseOrderId(finalBaseOrder.getId());
                    subOrder.setItemId(entity.getKey());
                    subOrder.setShippingAddressId(entity.getValue().getShippingAddressId());
                    subOrder.setQuantity(entity.getValue().getQuantity());
                    subOrder.setStatus(OrderStatus.INITIATED.getStatus());
                    subOrder.setCreatedOn(Utils.getCurrentTimeInCST());
                    subOrder = subOrderService.saveOrUpdate(subOrder);
                    createdSubOrders.add(subOrder);
                });

        List<Payment> createdPayments = new ArrayList<>();
        orderRequestDTO.getPayments().stream().forEach(
                paymentRef -> {
                    Payment payment = new Payment();
                    payment.setBaseOrderId(finalBaseOrder.getId());
                    payment.setAmount(paymentRef.getAmount());
                    payment.setBillingAddressId(paymentRef.getBillingAddress());
                    payment.setPaymentMethod(paymentRef.getPayMode());
                    payment.setCreatedOn(Utils.getCurrentTimeInCST());
                    payment = paymentService.saveOrUpdate(payment);
                    createdPayments.add(payment);
                }
        );
        OrderResponseDTO responseDTO = new OrderResponseDTO();
        responseDTO.setBaseOrder(finalBaseOrder);
        responseDTO.setSubOrders(createdSubOrders);
        responseDTO.setPayments(createdPayments);
        return responseDTO;
    };

    @Override
    public Optional<BaseOrder> getById(Integer id) {
        return baseOrderRepository.findById(id);
    }

    @Override
    public BaseOrder saveOrUpdate(BaseOrder baseOrder) {
        return baseOrderRepository.saveAndFlush(baseOrder);
    }

    @Override
    public void deleteById(Integer id) {
        baseOrderRepository.deleteById(id);
    }

    @Override
    public BaseOrder modifyOrder(ModifyOrderRequestDTO modifyOrderRequestDTO) {
        modifyOrderRequestDTO.getSubOrdersMap().entrySet().stream().forEach(entity -> {
            Integer subOrderId = entity.getKey();
            if (entity.getValue().getQuantity() > 0) {
               Optional<SubOrder> subOrder = subOrderService.getById(subOrderId);
               subOrder.ifPresent(subOrderObj -> {
                   subOrderObj.setQuantity(entity.getValue().getQuantity());
                   subOrderObj.setShippingAddressId(entity.getValue().getShippingAddressId());
                   subOrderObj.setModifiedOn(Utils.getCurrentTimeInCST());
                   subOrderService.saveOrUpdate(subOrderObj);
               });
            } else {
                subOrderService.deleteById(subOrderId);
            }
        });
        //TODO:
        //Need to Handle the Payments ( as Quantity of orders have changed)
        return baseOrderRepository.findById(modifyOrderRequestDTO.getBaseOrderId()).orElse(null);
    }

    @Override
    public BaseOrder cancelOrder(Integer id) {
        Optional<BaseOrder> baseOrder = baseOrderRepository.findById(id);
        baseOrder.ifPresent(baseOrderObj -> {
            baseOrderObj.setStatus(OrderStatus.CANCELED.getStatus());
            baseOrderObj.setModifiedOn(Utils.getCurrentTimeInCST());
            baseOrderRepository.saveAndFlush(baseOrderObj);
            baseOrderObj.getSubOrderList().forEach(subOrder -> {
                subOrder.setStatus(OrderStatus.CANCELED.getStatus());
                subOrder.setModifiedOn(Utils.getCurrentTimeInCST());
                subOrderService.saveOrUpdate(subOrder);
            });
            baseOrderObj.getPaymentList().forEach(payment -> {
                payment.setStatus(PaymentStatus.CANCELED.getStatus());
                payment.setModifiedOn(Utils.getCurrentTimeInCST());
                paymentService.saveOrUpdate(payment);
            });
        } );
        return baseOrderRepository.findById(id).orElse(null);
    }

}
