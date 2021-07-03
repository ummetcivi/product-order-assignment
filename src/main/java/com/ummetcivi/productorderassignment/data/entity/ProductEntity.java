package com.ummetcivi.productorderassignment.data.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document
@Builder
@Getter
public class ProductEntity {

    @Id
    private final String id;
    private final Instant createdAt;
    @Setter
    private String name;
    @Setter
    private double price;
}
