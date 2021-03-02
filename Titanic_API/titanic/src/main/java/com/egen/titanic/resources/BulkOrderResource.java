package com.egen.titanic.resources;

import com.egen.titanic.apputils.KafkaOrderTopics;
import com.egen.titanic.model.request.CreateOrderRequestDTO;
import com.egen.titanic.model.request.ModifyOrderRequestDTO;
import com.egen.titanic.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/bulk/order")
public class BulkOrderResource {

    @Autowired
    private KafkaService kafkaService;

    @PostMapping("/create")
    public void createOrder(@RequestBody CreateOrderRequestDTO orderRequestDTO) {
        kafkaService.publishMessage(KafkaOrderTopics.CREATE.getTopic(), orderRequestDTO);
    }

    @PostMapping("/modify")
    public void modifyOrder(@RequestBody ModifyOrderRequestDTO modifyOrderRequestDTO) {
        kafkaService.publishMessage(KafkaOrderTopics.MODIFY.getTopic(), modifyOrderRequestDTO);
    }

    @GetMapping("/cancel/{id}")
    public void cancelOrder(@PathVariable("id") Integer id) {
        kafkaService.publishMessage(KafkaOrderTopics.CANCEL.getTopic(), id);
    }

}
