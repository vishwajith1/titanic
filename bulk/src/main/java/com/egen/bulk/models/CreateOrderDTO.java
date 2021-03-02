package com.egen.bulk.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class CreateOrderDTO {
    private Integer customerId;
    private Map<Integer, ItemDetails> items;
    private List<PaymentDetails> payments;
}