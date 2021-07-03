package com.ummetcivi.productorderassignment.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderProduct {

    private final String productId;
    private final String name;
    private final double price;
    private final int quantity;
}
