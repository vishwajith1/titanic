package com.egen.bulk.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "payment")
@Data
@NoArgsConstructor
public class Payment implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "pay_mode")
    private String paymentMethod;

    @Column(name = "confirmation_number")
    private Integer confirmationNumber;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "base_order_id")
    private Integer baseOrderId;

    @Column(name = "created_on")
    private Timestamp createdOn;

    @Column(name = "billing_address_id")
    private Integer billingAddressId;

    @Column(name = "status")
    private String status;

    @Column(name = "modified_on")
    private Timestamp modifiedOn;

}
