package com.ummetcivi.productorderassignment.converter;

import com.ummetcivi.productorderassignment.data.entity.OrderProductEntity;
import com.ummetcivi.productorderassignment.domain.OrderProduct;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderProductEntityToOrderProductConverter implements Converter<OrderProductEntity, OrderProduct> {

    @Override
    public OrderProduct convert(OrderProductEntity source) {
        return OrderProduct.builder()
                .quantity(source.getQuantity())
                .price(source.getPrice())
                .name(source.getName())
                .productId(source.getProductId())
                .build();
    }
}
