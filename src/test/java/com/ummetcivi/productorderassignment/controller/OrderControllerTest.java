package com.ummetcivi.productorderassignment.controller;

import com.ummetcivi.productorderassignment.controller.dto.CreateOrderDto;
import com.ummetcivi.productorderassignment.controller.resource.OrderResource;
import com.ummetcivi.productorderassignment.domain.CreateOrderData;
import com.ummetcivi.productorderassignment.domain.Order;
import com.ummetcivi.productorderassignment.service.ConverterService;
import com.ummetcivi.productorderassignment.service.OrderService;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Instant;

public class OrderControllerTest {

    private OrderController underTest;

    @Mock
    private OrderService orderService;
    @Mock
    private ConverterService converterService;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new OrderController(orderService, converterService);
    }

    @Test
    public void shouldCreateOrder() {
        // Given
        final CreateOrderDto dto = Mockito.mock(CreateOrderDto.class);
        final CreateOrderData data = Mockito.mock(CreateOrderData.class);
        final Order order = Mockito.mock(Order.class);
        final OrderResource resource = Mockito.mock(OrderResource.class);

        Mockito.when(converterService.convert(dto, CreateOrderData.class)).thenReturn(data);
        Mockito.when(orderService.createOrder(data)).thenReturn(order);
        Mockito.when(converterService.convert(order, OrderResource.class)).thenReturn(resource);

        // When
        final ResponseEntity<OrderResource> response = underTest.create(dto);

        // Then
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        Assert.assertEquals(response.getBody(), resource);

        Mockito.verify(converterService).convert(dto, CreateOrderData.class);
        Mockito.verify(orderService).createOrder(data);
        Mockito.verify(converterService).convert(order, OrderResource.class);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldGetAll() {
        // Given
        final Instant startDate = Instant.now();
        final Instant endDate = Instant.now().plusSeconds(5);
        final Pageable pageable = Mockito.mock(Pageable.class);
        final Page<Order> pagedOrders = Mockito.mock(Page.class);
        final Page<OrderResource> pagedResource = Mockito.mock(Page.class);

        Mockito.when(orderService.getAll(startDate, endDate, pageable)).thenReturn(pagedOrders);
        Mockito.when(converterService.convertPage(pagedOrders, OrderResource.class)).thenReturn(pagedResource);

        // When
        final ResponseEntity<Page<OrderResource>> response = underTest.getAll(startDate, endDate, pageable);

        // Then
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody(), pagedResource);

        Mockito.verify(orderService).getAll(startDate, endDate, pageable);
        Mockito.verify(converterService).convertPage(pagedOrders, OrderResource.class);
    }
}