package com.egen.titanic.repository;

import com.egen.titanic.entity.BaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseOrderRepository extends JpaRepository<BaseOrder, Integer> {
}
