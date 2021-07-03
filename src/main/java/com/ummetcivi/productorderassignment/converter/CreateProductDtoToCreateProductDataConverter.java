package com.ummetcivi.productorderassignment.converter;

import com.ummetcivi.productorderassignment.controller.dto.CreateProductDto;
import com.ummetcivi.productorderassignment.domain.CreateProductData;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CreateProductDtoToCreateProductDataConverter implements Converter<CreateProductDto, CreateProductData> {

    @Override
    public CreateProductData convert(CreateProductDto source) {
        return CreateProductData.builder()
                .name(source.getName())
                .price(source.getPrice())
                .build();
    }
}
