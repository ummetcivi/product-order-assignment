package com.ummetcivi.productorderassignment.controller.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Builder
@Getter
public class CreateOrderDto {

    @NotBlank
    @Email
    private final String buyerEmail;
    @NotEmpty
    @Valid
    private final List<CreateOrderProductDto> products;
}
