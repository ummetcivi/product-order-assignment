package com.ummetcivi.productorderassignment.converter;

import com.ummetcivi.productorderassignment.controller.dto.CreateOrderDto;
import com.ummetcivi.productorderassignment.domain.CreateOrderData;
import com.ummetcivi.productorderassignment.domain.CreateOrderProductData;
import com.ummetcivi.productorderassignment.service.ConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateOrderDtoToCreateOrderDataConverter implements Converter<CreateOrderDto, CreateOrderData> {

    private final ConverterService converterService;

    @Override
    public CreateOrderData convert(CreateOrderDto source) {
        return CreateOrderData.builder()
                .buyerEmail(source.getBuyerEmail())
                .products(converterService.convertList(source.getProducts(), CreateOrderProductData.class))
                .build();
    }
}
