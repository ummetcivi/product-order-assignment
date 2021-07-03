package com.ummetcivi.productorderassignment.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateProductData {

    private final String name;
    private final double price;
}
