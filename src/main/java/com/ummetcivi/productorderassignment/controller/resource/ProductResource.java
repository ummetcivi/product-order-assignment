package com.ummetcivi.productorderassignment.controller.resource;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Builder
@Getter
public class ProductResource {

    private final String id;
    private final String name;
    private final double price;
    private final Instant createdAt;
}
