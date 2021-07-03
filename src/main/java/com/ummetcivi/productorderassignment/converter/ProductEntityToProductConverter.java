package com.ummetcivi.productorderassignment.converter;

import com.ummetcivi.productorderassignment.data.entity.ProductEntity;
import com.ummetcivi.productorderassignment.domain.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductEntityToProductConverter implements Converter<ProductEntity, Product> {

    @Override
    public Product convert(ProductEntity source) {
        return Product.builder()
                .id(source.getId())
                .name(source.getName())
                .price(source.getPrice())
                .createdAt(source.getCreatedAt())
                .build();
    }
}
