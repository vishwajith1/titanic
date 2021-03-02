package com.egen.bulk.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemDetails {
    private Integer quantity;
    private Integer shippingAddressId;
}