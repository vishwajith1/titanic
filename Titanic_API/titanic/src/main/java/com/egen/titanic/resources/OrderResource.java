package com.egen.titanic.resources;

import com.egen.titanic.entity.BaseOrder;
import com.egen.titanic.model.request.CreateOrderRequestDTO;
import com.egen.titanic.model.request.ModifyOrderRequestDTO;
import com.egen.titanic.model.response.OrderResponseDTO;
import com.egen.titanic.service.BaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/order")
public class OrderResource {

    @Autowired
    private BaseOrderService baseOrderService;

    @PostMapping("/create")
    public OrderResponseDTO createOrder(@RequestBody CreateOrderRequestDTO orderRequestDTO) {
        return baseOrderService.createOrder(orderRequestDTO);
    }

    @GetMapping("/{id}")
    public BaseOrder getById(@PathVariable("id") Integer id) {
        return baseOrderService.getById(id).orElse(null);
    }

    @PostMapping("/modify")
    public BaseOrder modifyOrder(@RequestBody ModifyOrderRequestDTO modifyOrderRequestDTO) {
        return baseOrderService.modifyOrder(modifyOrderRequestDTO);
    }

    @GetMapping("/cancel/{id}")
    public BaseOrder cancelOrder(@PathVariable("id") Integer id) {
        return baseOrderService.cancelOrder(id);
    }

}
