package com.ummetcivi.productorderassignment.converter;

import com.ummetcivi.productorderassignment.TestConstants;
import com.ummetcivi.productorderassignment.controller.dto.UpdateProductDto;
import com.ummetcivi.productorderassignment.domain.UpdateProductData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UpdateProductDtoToUpdateProductDataConverterTest {

    private UpdateProductDtoToUpdateProductDataConverter underTest;

    @BeforeMethod
    public void setUp() {
        underTest = new UpdateProductDtoToUpdateProductDataConverter();
    }

    @Test
    public void shouldConvert() {
        // Given
        final UpdateProductDto dto = UpdateProductDto.builder()
                .name(TestConstants.ANY_PRODUCT_NAME)
                .price(TestConstants.ANY_PRODUCT_PRICE)
                .build();

        // When
        final UpdateProductData result = underTest.convert(dto);

        // Then
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getName(), TestConstants.ANY_PRODUCT_NAME);
        Assert.assertEquals(result.getPrice(), TestConstants.ANY_PRODUCT_PRICE);
    }
}