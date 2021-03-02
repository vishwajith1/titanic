package com.egen.bulk.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class ModifyOrderDTO {
    private Integer baseOrderId;
    private Map<Integer, ModifySubOrders> subOrdersMap;
}
