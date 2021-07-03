package com.ummetcivi.productorderassignment.controller.resource;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderProductResource {

    private final String id;
    private final String name;
    private final int quantity;
    private final double price;
}
