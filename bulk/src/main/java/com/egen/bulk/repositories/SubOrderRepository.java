package com.egen.bulk.repositories;

import com.egen.bulk.entity.SubOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubOrderRepository extends JpaRepository<SubOrder, Integer> {
}
