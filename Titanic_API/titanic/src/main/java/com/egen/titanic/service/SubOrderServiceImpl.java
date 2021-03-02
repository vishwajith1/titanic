package com.egen.titanic.service;

import com.egen.titanic.entity.SubOrder;
import com.egen.titanic.service.SubOrderService;
import com.egen.titanic.repository.SubOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubOrderServiceImpl implements SubOrderService {
    @Autowired
    SubOrderRepository subOrderRepository;

    @Override
    public Optional<SubOrder> getById(Integer id) {
        return subOrderRepository.findById(id);
    }

    @Override
    public SubOrder saveOrUpdate(SubOrder subOrder){
        return subOrderRepository.saveAndFlush(subOrder);
    }

    @Override
    public void deleteById(Integer id) {
        subOrderRepository.deleteById(id);
    }

    @Override
    public SubOrder getByBaseOrderIdAndItemId(Integer baseOrderId, Integer itemId) {
        return subOrderRepository.getSubOrderByBaseOrderIdAndItemId(baseOrderId, itemId);
    }
}
