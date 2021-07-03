package com.ummetcivi.productorderassignment.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CreateOrderData {

    private final String buyerEmail;
    private final List<CreateOrderProductData> products;
}
