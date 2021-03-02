package com.egen.titanic.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModifySubOrders {
    private Integer quantity;
    private Integer shippingAddressId;
}
