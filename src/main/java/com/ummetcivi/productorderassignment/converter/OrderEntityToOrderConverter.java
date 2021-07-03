package com.ummetcivi.productorderassignment.converter;

import com.ummetcivi.productorderassignment.data.entity.OrderEntity;
import com.ummetcivi.productorderassignment.domain.Order;
import com.ummetcivi.productorderassignment.domain.OrderProduct;
import com.ummetcivi.productorderassignment.service.ConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEntityToOrderConverter implements Converter<OrderEntity, Order> {

    private final ConverterService converterService;

    @Override
    public Order convert(OrderEntity source) {
        return Order.builder()
                .totalPrice(source.getTotalPrice())
                .id(source.getId())
                .createdAt(source.getCreatedAt())
                .buyerEmail(source.getBuyerEmail())
                .products(converterService.convertList(source.getProducts(), OrderProduct.class))
                .build();
    }
}
