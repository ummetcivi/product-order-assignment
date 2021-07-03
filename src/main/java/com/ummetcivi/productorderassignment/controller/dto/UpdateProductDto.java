package com.ummetcivi.productorderassignment.controller.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Builder
@Getter
public class UpdateProductDto {

    @NotBlank
    private final String name;
    @Positive
    private final double price;
}
