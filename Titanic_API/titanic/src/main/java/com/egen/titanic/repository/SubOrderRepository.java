package com.egen.titanic.repository;

import com.egen.titanic.entity.SubOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubOrderRepository extends JpaRepository<SubOrder, Integer> {
    SubOrder getSubOrderByBaseOrderIdAndItemId(Integer baseOrderId, Integer itemId);
}
