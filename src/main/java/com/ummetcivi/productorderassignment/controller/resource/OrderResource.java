package com.ummetcivi.productorderassignment.controller.resource;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Builder
@Getter
public class OrderResource {

    private final String id;
    private final String buyerEmail;
    private final List<OrderProductResource> products;
    private final Instant createdAt;
    private final double totalPrice;
}
