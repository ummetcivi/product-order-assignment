package com.ummetcivi.productorderassignment.converter;

import com.ummetcivi.productorderassignment.TestConstants;
import com.ummetcivi.productorderassignment.controller.dto.CreateProductDto;
import com.ummetcivi.productorderassignment.domain.CreateProductData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CreateProductDtoToCreateProductDataConverterTest {

    private CreateProductDtoToCreateProductDataConverter underTest;

    @BeforeMethod
    public void setUp() {
        underTest = new CreateProductDtoToCreateProductDataConverter();
    }

    @Test
    public void shouldConvert() {
        // Given
        final CreateProductDto dto = CreateProductDto.builder()
                .name(TestConstants.ANY_PRODUCT_NAME)
                .price(TestConstants.ANY_PRODUCT_PRICE)
                .build();

        // When
        final CreateProductData result = underTest.convert(dto);

        // Then
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getName(), dto.getName());
        Assert.assertEquals(result.getPrice(), dto.getPrice());
    }
}