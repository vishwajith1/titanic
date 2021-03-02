package com.egen.titanic.service;

import com.egen.titanic.entity.SubOrder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface SubOrderService {

    Optional<SubOrder> getById(Integer id);

    SubOrder saveOrUpdate(SubOrder subOrder);

    void deleteById(Integer id);

    SubOrder getByBaseOrderIdAndItemId(Integer baseOrderId, Integer itemId);
}
