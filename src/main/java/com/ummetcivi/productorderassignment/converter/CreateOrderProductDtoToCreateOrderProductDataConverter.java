package com.ummetcivi.productorderassignment.converter;

import com.ummetcivi.productorderassignment.controller.dto.CreateOrderProductDto;
import com.ummetcivi.productorderassignment.domain.CreateOrderProductData;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CreateOrderProductDtoToCreateOrderProductDataConverter implements
        Converter<CreateOrderProductDto, CreateOrderProductData> {

    @Override
    public CreateOrderProductData convert(CreateOrderProductDto source) {
        return CreateOrderProductData.builder()
                .id(source.getId())
                .quantity(source.getQuantity())
                .build();
    }
}
