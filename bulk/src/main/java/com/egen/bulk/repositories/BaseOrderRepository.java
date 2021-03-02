package com.egen.bulk.repositories;


import com.egen.bulk.entity.BaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseOrderRepository extends JpaRepository<BaseOrder, Integer> {

    @Modifying
    @Query(value = "UPDATE base_order SET status = :status WHERE id in :ids", nativeQuery = true)
    void cancelOrdersWithIds(@Param("ids") int[] ids, @Param("status") String status);
}
