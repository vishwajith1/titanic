package com.egen.titanic.model.request;

import com.egen.titanic.model.ModifySubOrders;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class ModifyOrderRequestDTO {
    private Integer baseOrderId;
    private Map<Integer, ModifySubOrders> subOrdersMap;
}
