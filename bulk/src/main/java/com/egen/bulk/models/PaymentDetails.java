package com.egen.bulk.models;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentDetails {
    private String payMode;
    private Double amount;
    private Integer billingAddress;
}

