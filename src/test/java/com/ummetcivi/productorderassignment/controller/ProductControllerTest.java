package com.ummetcivi.productorderassignment.controller;

import com.ummetcivi.productorderassignment.TestConstants;
import com.ummetcivi.productorderassignment.controller.dto.CreateProductDto;
import com.ummetcivi.productorderassignment.controller.dto.UpdateProductDto;
import com.ummetcivi.productorderassignment.controller.resource.ProductResource;
import com.ummetcivi.productorderassignment.domain.CreateProductData;
import com.ummetcivi.productorderassignment.domain.Product;
import com.ummetcivi.productorderassignment.domain.UpdateProductData;
import com.ummetcivi.productorderassignment.service.ConverterService;
import com.ummetcivi.productorderassignment.service.ProductService;
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

public class ProductControllerTest {

    @Mock
    private ProductService productService;
    @Mock
    private ConverterService converterService;

    private ProductController underTest;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new ProductController(productService, converterService);
    }

    @Test
    public void shouldCreateProduct() {
        // Given
        final CreateProductDto dto = Mockito.mock(CreateProductDto.class);
        final CreateProductData data = Mockito.mock(CreateProductData.class);
        final Product product = Mockito.mock(Product.class);
        final ProductResource resource = Mockito.mock(ProductResource.class);

        Mockito.when(converterService.convert(dto, CreateProductData.class)).thenReturn(data);
        Mockito.when(productService.create(data)).thenReturn(product);
        Mockito.when(converterService.convert(product, ProductResource.class)).thenReturn(resource);

        // When
        final ResponseEntity<ProductResource> response = underTest.create(dto);

        // Then
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        Assert.assertEquals(response.getBody(), resource);

        Mockito.verify(converterService).convert(dto, CreateProductData.class);
        Mockito.verify(productService).create(data);
        Mockito.verify(converterService).convert(product, ProductResource.class);
    }

    @Test
    public void shouldUpdateProduct() {
        // Given
        final UpdateProductDto dto = Mockito.mock(UpdateProductDto.class);
        final UpdateProductData data = Mockito.mock(UpdateProductData.class);
        final Product product = Mockito.mock(Product.class);
        final ProductResource resource = Mockito.mock(ProductResource.class);

        Mockito.when(converterService.convert(dto, UpdateProductData.class)).thenReturn(data);
        Mockito.when(converterService.convert(product, ProductResource.class)).thenReturn(resource);
        Mockito.when(productService.update(TestConstants.ANY_PRODUCT_ID, data)).thenReturn(product);

        // When
        final ResponseEntity<ProductResource> response = underTest.update(TestConstants.ANY_PRODUCT_ID, dto);

        // Then
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody(), resource);

        Mockito.verify(converterService).convert(dto, UpdateProductData.class);
        Mockito.verify(converterService).convert(product, ProductResource.class);
        Mockito.verify(productService).update(TestConstants.ANY_PRODUCT_ID, data);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldGetAllProducts() {
        // Given
        final Pageable pageable = Mockito.mock(Pageable.class);
        final Page<Product> pagedProducts = Mockito.mock(Page.class);
        final Page<ProductResource> pagedProductResources = Mockito.mock(Page.class);

        Mockito.when(productService.getAll(pageable)).thenReturn(pagedProducts);
        Mockito.when(converterService.convertPage(pagedProducts, ProductResource.class))
                .thenReturn(pagedProductResources);

        // When
        final ResponseEntity<Page<ProductResource>> response = underTest.getAll(pageable);

        // Then
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody(), pagedProductResources);

        Mockito.verify(productService).getAll(pageable);
        Mockito.verify(converterService).convertPage(pagedProducts, ProductResource.class);
    }
}