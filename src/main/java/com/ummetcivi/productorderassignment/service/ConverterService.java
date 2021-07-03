package com.ummetcivi.productorderassignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ConverterService {

    @Autowired
    @Lazy
    private ConversionService conversionService;

    public <S, D> Page<D> convertPage(final Page<S> page, Class<D> destinationClass) {
        if (Objects.isNull(page)) {
            return Page.empty();
        }

        return page.map(s -> convert(s, destinationClass));
    }

    public <S, D> List<D> convertList(final List<S> list, Class<D> destinationClass) {
        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }

        return list.stream()
                .map(s -> convert(s, destinationClass))
                .collect(Collectors.toList());
    }

    public <S, D> D convert(final S object, Class<D> destinationClass) {
        return conversionService.convert(object, destinationClass);
    }

}
