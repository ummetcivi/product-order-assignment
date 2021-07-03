package com.ummetcivi.productorderassignment.converter;

import com.ummetcivi.productorderassignment.controller.resource.ProductResource;
import com.ummetcivi.productorderassignment.domain.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductToProductResourceConverter implements Converter<Product, ProductResource> {

    @Override
    public ProductResource convert(Product source) {
        return ProductResource.builder()
                .id(source.getId())
                .name(source.getName())
                .price(source.getPrice())
                .createdAt(source.getCreatedAt())
                .build();
    }
}
