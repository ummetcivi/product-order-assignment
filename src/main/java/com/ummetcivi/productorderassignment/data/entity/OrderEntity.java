package com.ummetcivi.productorderassignment.data.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document
@Builder
@Getter
public class OrderEntity {

    @Id
    private final String id;
    private final String buyerEmail;
    private final List<OrderProductEntity> products;
    private final double totalPrice;
    private final Instant createdAt;
}
