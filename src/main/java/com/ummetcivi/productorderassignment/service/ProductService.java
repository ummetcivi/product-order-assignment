package com.ummetcivi.productorderassignment.service;

import com.ummetcivi.productorderassignment.data.entity.ProductEntity;
import com.ummetcivi.productorderassignment.data.jpa.ProductRepository;
import com.ummetcivi.productorderassignment.domain.CreateProductData;
import com.ummetcivi.productorderassignment.domain.Product;
import com.ummetcivi.productorderassignment.domain.UpdateProductData;
import com.ummetcivi.productorderassignment.exception.ResourceNotFoundException;
import com.ummetcivi.productorderassignment.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final IdGenerator idGenerator;
    private final ConverterService converterService;

    public Product create(final CreateProductData data) {
        final ProductEntity savedProductEntity = productRepository.save(ProductEntity.builder()
                .id(idGenerator.generate())
                .createdAt(Instant.now())
                .name(data.getName())
                .price(data.getPrice())
                .createdAt(Instant.now())
                .build());
        return converterService.convert(savedProductEntity, Product.class);
    }

    public Product update(final String id, final UpdateProductData data) {
        final ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found."));

        productEntity.setPrice(data.getPrice());
        productEntity.setName(data.getName());

        final ProductEntity savedProductEntity = productRepository.save(productEntity);

        return converterService.convert(savedProductEntity, Product.class);
    }

    public Product getById(final String id) {
        return productRepository.findById(id)
                .map(productEntity -> converterService.convert(productEntity, Product.class))
                .orElseThrow(() -> new ResourceNotFoundException("Product not found."));
    }

    public Page<Product> getAll(Pageable pageable) {
        return converterService.convertPage(productRepository.findAll(pageable), Product.class);
    }
}
