package com.ummetcivi.productorderassignment.controller.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class CreateOrderProductDto {

    @NotBlank
    private final String id;
    @Min(1)
    private final int quantity;
}
