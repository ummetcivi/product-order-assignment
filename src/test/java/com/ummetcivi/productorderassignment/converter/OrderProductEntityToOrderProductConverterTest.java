package com.ummetcivi.productorderassignment.converter;

import com.ummetcivi.productorderassignment.TestConstants;
import com.ummetcivi.productorderassignment.data.entity.OrderProductEntity;
import com.ummetcivi.productorderassignment.domain.OrderProduct;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class OrderProductEntityToOrderProductConverterTest {

    private OrderProductEntityToOrderProductConverter underTest;

    @BeforeMethod
    public void setUp() {
        underTest = new OrderProductEntityToOrderProductConverter();
    }

    @Test
    public void shouldConvert() {
        // Given
        final OrderProductEntity entity = OrderProductEntity.builder()
                .quantity(TestConstants.ANY_QUANTITY)
                .productId(TestConstants.ANY_PRODUCT_ID)
                .price(TestConstants.ANY_PRODUCT_PRICE)
                .name(TestConstants.ANY_PRODUCT_NAME)
                .build();

        // When
        final OrderProduct result = underTest.convert(entity);

        // Then
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getName(), TestConstants.ANY_PRODUCT_NAME);
        Assert.assertEquals(result.getProductId(), TestConstants.ANY_PRODUCT_ID);
        Assert.assertEquals(result.getPrice(), TestConstants.ANY_PRODUCT_PRICE);
        Assert.assertEquals(result.getQuantity(), TestConstants.ANY_QUANTITY);
    }
}