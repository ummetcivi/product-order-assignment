package com.ummetcivi.productorderassignment.data.entity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderProductEntity {

    private final String productId;
    private final String name;
    private final double price;
    private final int quantity;
}
