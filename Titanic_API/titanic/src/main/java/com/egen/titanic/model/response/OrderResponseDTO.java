package com.egen.titanic.model.response;

import com.egen.titanic.entity.BaseOrder;
import com.egen.titanic.entity.Payment;
import com.egen.titanic.entity.SubOrder;
import lombok.Data;

import java.util.List;

@Data
public class OrderResponseDTO {
    BaseOrder baseOrder;
    List<SubOrder> subOrders;
    List<Payment> payments;
}
