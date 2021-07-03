package com.ummetcivi.productorderassignment.controller;

import com.ummetcivi.productorderassignment.controller.dto.CreateOrderDto;
import com.ummetcivi.productorderassignment.controller.resource.OrderResource;
import com.ummetcivi.productorderassignment.domain.CreateOrderData;
import com.ummetcivi.productorderassignment.domain.Order;
import com.ummetcivi.productorderassignment.service.ConverterService;
import com.ummetcivi.productorderassignment.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Instant;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final ConverterService converterService;

    @PostMapping
    public ResponseEntity<OrderResource> create(@RequestBody @Valid final CreateOrderDto dto) {
        final CreateOrderData data = converterService.convert(dto, CreateOrderData.class);
        final Order order = orderService.createOrder(data);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(converterService.convert(order, OrderResource.class));
    }

    @GetMapping
    public ResponseEntity<Page<OrderResource>> getAll(
            @RequestParam(required = false) final Instant startDate,
            @RequestParam(required = false) final Instant endDate, @ParameterObject final Pageable pageable) {
        final Page<Order> pagedOrders = orderService.getAll(startDate, endDate, pageable);

        return ResponseEntity.ok(converterService.convertPage(pagedOrders, OrderResource.class));
    }
}
