package com.ummetcivi.productorderassignment.converter;

import com.ummetcivi.productorderassignment.controller.dto.UpdateProductDto;
import com.ummetcivi.productorderassignment.domain.UpdateProductData;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UpdateProductDtoToUpdateProductDataConverter implements Converter<UpdateProductDto, UpdateProductData> {

    @Override
    public UpdateProductData convert(UpdateProductDto source) {
        return UpdateProductData.builder()
                .name(source.getName())
                .price(source.getPrice())
                .build();
    }
}
