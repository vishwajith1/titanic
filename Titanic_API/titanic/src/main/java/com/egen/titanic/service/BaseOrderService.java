package com.egen.titanic.service;

import com.egen.titanic.entity.BaseOrder;
import com.egen.titanic.model.request.CreateOrderRequestDTO;
import com.egen.titanic.model.request.ModifyOrderRequestDTO;
import com.egen.titanic.model.response.OrderResponseDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface BaseOrderService {
    OrderResponseDTO createOrder(CreateOrderRequestDTO orderRequestDTO);

    Optional<BaseOrder> getById(Integer id);

    BaseOrder saveOrUpdate(BaseOrder baseOrder);

    void deleteById(Integer id);

    BaseOrder modifyOrder(ModifyOrderRequestDTO modifyOrderRequestDTO);

    BaseOrder cancelOrder(Integer id);
}
