package com.ummetcivi.productorderassignment.converter;

import com.ummetcivi.productorderassignment.TestConstants;
import com.ummetcivi.productorderassignment.controller.dto.CreateOrderProductDto;
import com.ummetcivi.productorderassignment.domain.CreateOrderProductData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CreateOrderProductDtoToCreateOrderProductDataConverterTest {

    private CreateOrderProductDtoToCreateOrderProductDataConverter underTest;

    @BeforeMethod
    public void setUp() {
        underTest = new CreateOrderProductDtoToCreateOrderProductDataConverter();
    }

    @Test
    public void shouldConvert() {
        // Given
        final CreateOrderProductDto dto = CreateOrderProductDto.builder()
                .id(TestConstants.ANY_PRODUCT_ID)
                .quantity(TestConstants.ANY_QUANTITY)
                .build();

        // When
        final CreateOrderProductData result = underTest.convert(dto);

        // Then
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getId(), TestConstants.ANY_PRODUCT_ID);
        Assert.assertEquals(result.getQuantity(), TestConstants.ANY_QUANTITY);
    }
}