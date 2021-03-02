package com.egen.titanic.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "sub_order")
@Data
@NoArgsConstructor
public class SubOrder implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "base_order_id")
    private Integer baseOrderId;

    @Column(name = "item_id")
    private Integer itemId;

    @Column(name = "qty")
    private Integer quantity;

    @Column(name = "shipping_address_id")
    private Integer shippingAddressId;

    @Column(name = "order_status")
    private String status;

    @Column(name = "created_on")
    private Timestamp createdOn;

    @Column(name = "modified_on")
    private Timestamp modifiedOn;
}
