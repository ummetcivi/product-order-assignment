package com.ummetcivi.productorderassignment.converter;

import com.ummetcivi.productorderassignment.TestConstants;
import com.ummetcivi.productorderassignment.controller.resource.OrderProductResource;
import com.ummetcivi.productorderassignment.controller.resource.OrderResource;
import com.ummetcivi.productorderassignment.domain.Order;
import com.ummetcivi.productorderassignment.domain.OrderProduct;
import com.ummetcivi.productorderassignment.service.ConverterService;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Instant;
import java.util.List;

public class OrderToOrderResourceConverterTest {

    private OrderToOrderResourceConverter underTest;

    @Mock
    private ConverterService converterService;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new OrderToOrderResourceConverter(converterService);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldConvert() {
        // Given
        final Instant now = Instant.now();
        final List<OrderProduct> orderProductList = Mockito.mock(List.class);
        final List<OrderProductResource> orderProductResources = Mockito.mock(List.class);

        final Order order = Order.builder()
                .id(TestConstants.ANY_ORDER_ID)
                .buyerEmail(TestConstants.ANY_BUYER_EMAIL)
                .createdAt(now)
                .products(orderProductList)
                .totalPrice(TestConstants.ANY_TOTAL_PRICE)
                .build();

        Mockito.when(converterService.convertList(orderProductList, OrderProductResource.class))
                .thenReturn(orderProductResources);

        // When
        final OrderResource result = underTest.convert(order);

        // Then
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getBuyerEmail(), TestConstants.ANY_BUYER_EMAIL);
        Assert.assertEquals(result.getCreatedAt(), now);
        Assert.assertEquals(result.getId(), TestConstants.ANY_ORDER_ID);
        Assert.assertEquals(result.getTotalPrice(), TestConstants.ANY_TOTAL_PRICE);
        Assert.assertEquals(result.getProducts(), orderProductResources);
    }
}