package com.ummetcivi.productorderassignment.service;

import com.ummetcivi.productorderassignment.TestConstants;
import com.ummetcivi.productorderassignment.data.entity.OrderEntity;
import com.ummetcivi.productorderassignment.data.entity.OrderProductEntity;
import com.ummetcivi.productorderassignment.data.jpa.OrderRepository;
import com.ummetcivi.productorderassignment.domain.CreateOrderData;
import com.ummetcivi.productorderassignment.domain.CreateOrderProductData;
import com.ummetcivi.productorderassignment.domain.Order;
import com.ummetcivi.productorderassignment.domain.Product;
import com.ummetcivi.productorderassignment.util.IdGenerator;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.GreaterOrEqual;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Instant;
import java.util.List;

public class OrderServiceTest {

    private OrderService underTest;

    @Mock
    private ProductService productService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private IdGenerator idGenerator;
    @Mock
    private ConverterService converterService;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new OrderService(productService, orderRepository, idGenerator, converterService);
    }

    @Test
    public void shouldCreateOrder() {
        // Given
        final Order order = Mockito.mock(Order.class);

        final CreateOrderProductData firstOrderProduct = CreateOrderProductData.builder()
                .id(TestConstants.ANY_PRODUCT_ID)
                .quantity(TestConstants.ANY_QUANTITY)
                .build();
        final CreateOrderProductData secondOrderProduct = CreateOrderProductData.builder()
                .id(TestConstants.ANY_OTHER_PRODUCT_ID)
                .quantity(TestConstants.ANY_OTHER_QUANTITY)
                .build();
        final CreateOrderData data = CreateOrderData.builder()
                .buyerEmail(TestConstants.ANY_BUYER_EMAIL)
                .products(List.of(firstOrderProduct, secondOrderProduct))
                .build();

        final Product firstProduct = Product.builder()
                .name(TestConstants.ANY_PRODUCT_NAME)
                .id(TestConstants.ANY_PRODUCT_ID)
                .price(TestConstants.ANY_PRODUCT_PRICE)
                .build();
        final Product secondProduct = Product.builder()
                .id(TestConstants.ANY_OTHER_PRODUCT_ID)
                .name(TestConstants.ANY_OTHER_PRODUCT_NAME)
                .price(TestConstants.ANY_OTHER_PRODUCT_PRICE)
                .build();

        Mockito.when(idGenerator.generate()).thenReturn(TestConstants.ANY_ORDER_ID);
        Mockito.when(converterService.convert(Mockito.any(OrderEntity.class), Mockito.eq(Order.class)))
                .thenReturn(order);
        Mockito.when(productService.getById(TestConstants.ANY_PRODUCT_ID)).thenReturn(firstProduct);
        Mockito.when(productService.getById(TestConstants.ANY_OTHER_PRODUCT_ID)).thenReturn(secondProduct);
        Mockito.when(orderRepository.save(Mockito.any(OrderEntity.class)))
                .then(invocation -> invocation.getArgument(0));

        // When
        final Order result = underTest.createOrder(data);

        // Then
        Assert.assertEquals(result, order);

        ArgumentCaptor<OrderEntity> savedOrderEntityArgumentCaptor = ArgumentCaptor.forClass(OrderEntity.class);
        Mockito.verify(orderRepository).save(savedOrderEntityArgumentCaptor.capture());
        final OrderEntity savedOrder = savedOrderEntityArgumentCaptor.getValue();

        Assert.assertNotNull(savedOrder);
        Assert.assertEquals(savedOrder.getId(), TestConstants.ANY_ORDER_ID);
        Assert.assertNotNull(savedOrder.getCreatedAt());
        Assert.assertEquals(savedOrder.getBuyerEmail(), TestConstants.ANY_BUYER_EMAIL);
        Assert.assertNotNull(savedOrder.getProducts());
        Assert.assertEquals(savedOrder.getProducts().size(), 2);
        Assert.assertEquals(savedOrder.getTotalPrice(), 11);

        savedOrder.getProducts().forEach(orderProductEntity -> {
            switch (orderProductEntity.getProductId()) {
                case TestConstants.ANY_PRODUCT_ID:
                    assertOrderProductEntity(orderProductEntity, TestConstants.ANY_PRODUCT_NAME,
                            TestConstants.ANY_PRODUCT_PRICE, TestConstants.ANY_QUANTITY);
                    break;
                case TestConstants.ANY_OTHER_PRODUCT_ID:
                    assertOrderProductEntity(orderProductEntity, TestConstants.ANY_OTHER_PRODUCT_NAME,
                            TestConstants.ANY_OTHER_PRODUCT_PRICE, TestConstants.ANY_OTHER_QUANTITY);
                    break;
                default:
                    Assert.fail();
            }
        });

        Mockito.verify(idGenerator).generate();
        Mockito.verify(converterService).convert(Mockito.any(OrderEntity.class), Mockito.eq(Order.class));
        Mockito.verify(productService).getById(TestConstants.ANY_PRODUCT_ID);
        Mockito.verify(productService).getById(TestConstants.ANY_OTHER_PRODUCT_ID);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldGetAll() {
        // Given
        final Instant startDate = Instant.now();
        final Instant endDate = Instant.now().plusSeconds(5);
        final Pageable pageable = Mockito.mock(Pageable.class);
        final Page<OrderEntity> pagedOrderEntity = Mockito.mock(Page.class);
        final Page<Order> pagedOrder = Mockito.mock(Page.class);

        Mockito.when(orderRepository.findAllByCreatedAtBetween(startDate, endDate, pageable))
                .thenReturn(pagedOrderEntity);
        Mockito.when(converterService.convertPage(pagedOrderEntity, Order.class)).thenReturn(pagedOrder);

        // When
        final Page<Order> result = underTest.getAll(startDate, endDate, pageable);

        // Then
        Assert.assertEquals(result, pagedOrder);

        Mockito.verify(orderRepository).findAllByCreatedAtBetween(startDate, endDate, pageable);
        Mockito.verify(converterService).convertPage(pagedOrderEntity, Order.class);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldSetStartDateAsEpochAndGetAll() {
        // Given
        final Instant expectedStartDate = Instant.EPOCH;
        final Instant endDate = Instant.now().plusSeconds(5);
        final Pageable pageable = Mockito.mock(Pageable.class);
        final Page<OrderEntity> pagedOrderEntity = Mockito.mock(Page.class);
        final Page<Order> pagedOrder = Mockito.mock(Page.class);

        Mockito.when(orderRepository.findAllByCreatedAtBetween(expectedStartDate, endDate, pageable))
                .thenReturn(pagedOrderEntity);
        Mockito.when(converterService.convertPage(pagedOrderEntity, Order.class)).thenReturn(pagedOrder);

        // When
        final Page<Order> result = underTest.getAll(null, endDate, pageable);

        // Then
        Assert.assertEquals(result, pagedOrder);

        Mockito.verify(orderRepository).findAllByCreatedAtBetween(expectedStartDate, endDate, pageable);
        Mockito.verify(converterService).convertPage(pagedOrderEntity, Order.class);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldEndDateAsNowAndGetAll() {
        // Given
        final Instant now = Instant.now();
        final Instant startDate = Instant.now();
        final Pageable pageable = Mockito.mock(Pageable.class);
        final Page<OrderEntity> pagedOrderEntity = Mockito.mock(Page.class);
        final Page<Order> pagedOrder = Mockito.mock(Page.class);

        Mockito.when(orderRepository
                .findAllByCreatedAtBetween(Mockito.eq(startDate), Mockito.argThat(new GreaterOrEqual<>(now)),
                        Mockito.eq(pageable)))
                .thenReturn(pagedOrderEntity);
        Mockito.when(converterService.convertPage(pagedOrderEntity, Order.class)).thenReturn(pagedOrder);

        // When
        final Page<Order> result = underTest.getAll(startDate, null, pageable);

        // Then
        Assert.assertEquals(result, pagedOrder);

        Mockito.verify(orderRepository)
                .findAllByCreatedAtBetween(Mockito.eq(startDate), Mockito.argThat(new GreaterOrEqual<>(now)),
                        Mockito.eq(pageable));
        Mockito.verify(converterService).convertPage(pagedOrderEntity, Order.class);
    }

    private void assertOrderProductEntity(OrderProductEntity entity, String name, double price, int quantity) {
        Assert.assertEquals(entity.getName(), name);
        Assert.assertEquals(entity.getPrice(), price);
        Assert.assertEquals(entity.getQuantity(), quantity);
    }
}