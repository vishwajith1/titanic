package com.egen.titanic.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemDetails {
    private Integer quantity;
    private Integer shippingAddressId;
}