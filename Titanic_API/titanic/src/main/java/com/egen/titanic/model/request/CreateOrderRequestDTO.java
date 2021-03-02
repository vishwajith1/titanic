package com.egen.titanic.model.request;

import com.egen.titanic.model.ItemDetails;
import com.egen.titanic.model.PaymentDetails;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class CreateOrderRequestDTO {
    private Integer customerId;
    private Map<Integer, ItemDetails> items;
    private List<PaymentDetails> payments;
}