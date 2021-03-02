package com.egen.bulk.utils;

public enum OrderStatus {

    INITIATED("Initiated"),
    PROCESSING("Processing"),
    SHIPPED("Shipped"),
    PAYMENT_FAILED("PaymentFailed"),
    CANCELED("Canceled"),
    DELIVERED("Delivered");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

}
