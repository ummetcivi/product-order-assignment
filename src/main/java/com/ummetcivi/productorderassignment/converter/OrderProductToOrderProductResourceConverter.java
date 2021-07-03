package com.ummetcivi.productorderassignment.converter;

import com.ummetcivi.productorderassignment.controller.resource.OrderProductResource;
import com.ummetcivi.productorderassignment.domain.OrderProduct;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderProductToOrderProductResourceConverter implements Converter<OrderProduct, OrderProductResource> {

    @Override
    public OrderProductResource convert(OrderProduct source) {
        return OrderProductResource.builder()
                .id(source.getProductId())
                .name(source.getName())
                .price(source.getPrice())
                .quantity(source.getQuantity())
                .build();
    }
}
