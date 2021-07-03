package com.ummetcivi.productorderassignment.converter;

import com.ummetcivi.productorderassignment.TestConstants;
import com.ummetcivi.productorderassignment.data.entity.OrderEntity;
import com.ummetcivi.productorderassignment.data.entity.OrderProductEntity;
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

public class OrderEntityToOrderConverterTest {

    private OrderEntityToOrderConverter underTest;

    @Mock
    private ConverterService converterService;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new OrderEntityToOrderConverter(converterService);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldConvert() {
        // Given
        final List<OrderProductEntity> orderProductEntityList = Mockito.mock(List.class);
        final List<OrderProduct> orderProducts = Mockito.mock(List.class);
        final Instant now = Instant.now();
        final OrderEntity entity = OrderEntity.builder()
                .id(TestConstants.ANY_ORDER_ID)
                .buyerEmail(TestConstants.ANY_BUYER_EMAIL)
                .totalPrice(TestConstants.ANY_TOTAL_PRICE)
                .createdAt(now)
                .products(orderProductEntityList)
                .build();

        Mockito.when(converterService.convertList(orderProductEntityList, OrderProduct.class))
                .thenReturn(orderProducts);

        // When
        final Order result = underTest.convert(entity);

        // Then
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getBuyerEmail(), TestConstants.ANY_BUYER_EMAIL);
        Assert.assertEquals(result.getId(), TestConstants.ANY_ORDER_ID);
        Assert.assertEquals(result.getCreatedAt(), now);
        Assert.assertEquals(result.getTotalPrice(), TestConstants.ANY_TOTAL_PRICE);
        Assert.assertEquals(result.getProducts(), orderProducts);

        Mockito.verify(converterService).convertList(orderProductEntityList, OrderProduct.class);
    }
}