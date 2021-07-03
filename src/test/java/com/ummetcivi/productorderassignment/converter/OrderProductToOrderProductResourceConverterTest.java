package com.ummetcivi.productorderassignment.converter;

import com.ummetcivi.productorderassignment.TestConstants;
import com.ummetcivi.productorderassignment.controller.resource.OrderProductResource;
import com.ummetcivi.productorderassignment.domain.OrderProduct;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class OrderProductToOrderProductResourceConverterTest {

    private OrderProductToOrderProductResourceConverter underTest;

    @BeforeMethod
    public void setUp() {
        underTest = new OrderProductToOrderProductResourceConverter();
    }

    @Test
    public void shouldConvert() {
        // Given
        final OrderProduct orderProduct = OrderProduct.builder()
                .productId(TestConstants.ANY_PRODUCT_ID)
                .name(TestConstants.ANY_PRODUCT_NAME)
                .price(TestConstants.ANY_PRODUCT_PRICE)
                .quantity(TestConstants.ANY_QUANTITY)
                .build();

        // When
        final OrderProductResource result = underTest.convert(orderProduct);

        // Then
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getId(), TestConstants.ANY_PRODUCT_ID);
        Assert.assertEquals(result.getName(), TestConstants.ANY_PRODUCT_NAME);
        Assert.assertEquals(result.getPrice(), TestConstants.ANY_PRODUCT_PRICE);
        Assert.assertEquals(result.getQuantity(), TestConstants.ANY_QUANTITY);
    }
}