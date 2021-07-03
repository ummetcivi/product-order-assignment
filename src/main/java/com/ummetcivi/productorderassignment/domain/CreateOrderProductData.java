package com.ummetcivi.productorderassignment.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateOrderProductData {

    private final String id;
    private final int quantity;
}
