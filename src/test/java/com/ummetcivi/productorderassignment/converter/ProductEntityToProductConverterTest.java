package com.ummetcivi.productorderassignment.converter;

import com.ummetcivi.productorderassignment.TestConstants;
import com.ummetcivi.productorderassignment.data.entity.ProductEntity;
import com.ummetcivi.productorderassignment.domain.Product;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Instant;

public class ProductEntityToProductConverterTest {

    private ProductEntityToProductConverter underTest;

    @BeforeMethod
    public void setUp() {
        underTest = new ProductEntityToProductConverter();
    }

    @Test
    public void shouldConvert() {
        // Given
        final Instant now = Instant.now();
        final ProductEntity productEntity = ProductEntity.builder()
                .name(TestConstants.ANY_PRODUCT_NAME)
                .price(TestConstants.ANY_PRODUCT_PRICE)
                .id(TestConstants.ANY_PRODUCT_ID)
                .createdAt(now)
                .build();

        // When
        final Product result = underTest.convert(productEntity);

        // Then
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getId(), TestConstants.ANY_PRODUCT_ID);
        Assert.assertEquals(result.getName(), TestConstants.ANY_PRODUCT_NAME);
        Assert.assertEquals(result.getPrice(), TestConstants.ANY_PRODUCT_PRICE);
        Assert.assertEquals(result.getCreatedAt(), now);
    }
}