package com.ummetcivi.productorderassignment.controller;

import com.ummetcivi.productorderassignment.controller.dto.CreateProductDto;
import com.ummetcivi.productorderassignment.controller.dto.UpdateProductDto;
import com.ummetcivi.productorderassignment.controller.resource.ProductResource;
import com.ummetcivi.productorderassignment.domain.CreateProductData;
import com.ummetcivi.productorderassignment.domain.Product;
import com.ummetcivi.productorderassignment.domain.UpdateProductData;
import com.ummetcivi.productorderassignment.service.ConverterService;
import com.ummetcivi.productorderassignment.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final ConverterService converterService;

    @PostMapping
    public ResponseEntity<ProductResource> create(@RequestBody @Valid CreateProductDto dto) {
        final CreateProductData data = converterService.convert(dto, CreateProductData.class);
        final Product product = productService.create(data);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(converterService.convert(product, ProductResource.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResource> update(@PathVariable final String id,
            @RequestBody @Valid final UpdateProductDto dto) {
        final UpdateProductData data = converterService.convert(dto, UpdateProductData.class);
        final Product product = productService.update(id, data);

        return ResponseEntity.ok(converterService.convert(product, ProductResource.class));
    }

    @GetMapping
    public ResponseEntity<Page<ProductResource>> getAll(@ParameterObject final Pageable pageable) {
        final Page<Product> pagedProducts = productService.getAll(pageable);

        return ResponseEntity.ok(converterService.convertPage(pagedProducts, ProductResource.class));
    }
}
