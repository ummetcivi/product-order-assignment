package com.ummetcivi.productorderassignment.converter;

import com.ummetcivi.productorderassignment.controller.resource.OrderProductResource;
import com.ummetcivi.productorderassignment.controller.resource.OrderResource;
import com.ummetcivi.productorderassignment.domain.Order;
import com.ummetcivi.productorderassignment.service.ConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderToOrderResourceConverter implements Converter<Order, OrderResource> {

    private final ConverterService converterService;

    @Override
    public OrderResource convert(Order source) {
        return OrderResource.builder()
                .id(source.getId())
                .createdAt(source.getCreatedAt())
                .buyerEmail(source.getBuyerEmail())
                .totalPrice(source.getTotalPrice())
                .products(converterService.convertList(source.getProducts(), OrderProductResource.class))
                .build();
    }
}
