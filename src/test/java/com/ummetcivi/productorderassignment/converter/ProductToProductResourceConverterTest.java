package com.ummetcivi.productorderassignment.converter;

import com.ummetcivi.productorderassignment.TestConstants;
import com.ummetcivi.productorderassignment.controller.resource.ProductResource;
import com.ummetcivi.productorderassignment.domain.Product;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Instant;

public class ProductToProductResourceConverterTest {

    private ProductToProductResourceConverter underTest;

    @BeforeMethod
    public void setUp() {
        underTest = new ProductToProductResourceConverter();
    }

    @Test
    public void shouldConvert() {
        // Given
        final Instant now = Instant.now();
        final Product product = Product.builder()
                .id(TestConstants.ANY_PRODUCT_ID)
                .createdAt(now)
                .price(TestConstants.ANY_PRODUCT_PRICE)
                .name(TestConstants.ANY_PRODUCT_NAME)
                .build();

        // When
        final ProductResource result = underTest.convert(product);

        // Then
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getName(), TestConstants.ANY_PRODUCT_NAME);
        Assert.assertEquals(result.getPrice(), TestConstants.ANY_PRODUCT_PRICE);
        Assert.assertEquals(result.getId(), TestConstants.ANY_PRODUCT_ID);
        Assert.assertEquals(result.getCreatedAt(), now);
    }
}