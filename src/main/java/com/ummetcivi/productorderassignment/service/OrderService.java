package com.ummetcivi.productorderassignment.service;

import com.ummetcivi.productorderassignment.data.entity.OrderEntity;
import com.ummetcivi.productorderassignment.data.entity.OrderProductEntity;
import com.ummetcivi.productorderassignment.data.jpa.OrderRepository;
import com.ummetcivi.productorderassignment.domain.CreateOrderData;
import com.ummetcivi.productorderassignment.domain.CreateOrderProductData;
import com.ummetcivi.productorderassignment.domain.Order;
import com.ummetcivi.productorderassignment.domain.Product;
import com.ummetcivi.productorderassignment.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final IdGenerator idGenerator;
    private final ConverterService converterService;

    public Order createOrder(final CreateOrderData data) {
        final List<OrderProductEntity> products = prepareProducts(data.getProducts());

        final OrderEntity orderEntity = orderRepository.save(OrderEntity.builder()
                .buyerEmail(data.getBuyerEmail())
                .id(idGenerator.generate())
                .totalPrice(calculateTotalPrice(products))
                .products(products)
                .createdAt(Instant.now().truncatedTo(ChronoUnit.SECONDS))
                .build());

        return converterService.convert(orderEntity, Order.class);
    }

    public Page<Order> getAll(Instant startDate, Instant endDate, final Pageable pageable) {
        if (Objects.isNull(startDate)) {
            startDate = Instant.EPOCH;
        }

        if (Objects.isNull(endDate)) {
            endDate = Instant.now();
        }

        final Page<OrderEntity> pagedOrder = orderRepository.findAllByCreatedAtBetween(startDate, endDate, pageable);
        return converterService.convertPage(pagedOrder, Order.class);
    }

    private List<OrderProductEntity> prepareProducts(List<CreateOrderProductData> products) {
        return products.stream()
                .map(createOrderProductData -> {
                    final Product product = productService.getById(createOrderProductData.getId());

                    return OrderProductEntity.builder()
                            .name(product.getName())
                            .price(product.getPrice())
                            .productId(product.getId())
                            .quantity(createOrderProductData.getQuantity())
                            .build();
                })
                .collect(Collectors.toList());
    }

    private static double calculateTotalPrice(final List<OrderProductEntity> productEntities) {
        return productEntities.stream()
                .mapToDouble(value -> value.getPrice() * value.getQuantity())
                .sum();
    }
}
