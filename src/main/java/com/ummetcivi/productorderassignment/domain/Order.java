package com.ummetcivi.productorderassignment.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Builder
@Getter
public class Order {

    private final String id;
    private final String buyerEmail;
    private final List<OrderProduct> products;
    private final Instant createdAt;
    private final double totalPrice;
}
