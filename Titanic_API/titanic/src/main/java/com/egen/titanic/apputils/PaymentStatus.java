package com.egen.titanic.apputils;

public enum PaymentStatus {
    TO_BE_PROCESSED("TO_BE_PROCESSED"),
    CANCELED("Canceled"),
    COMPLETED("Completed"),
    REVERTED("Reverted");

    private final String status;

    PaymentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }


}
