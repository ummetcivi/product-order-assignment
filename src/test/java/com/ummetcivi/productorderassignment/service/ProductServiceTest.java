package com.ummetcivi.productorderassignment.service;

import com.ummetcivi.productorderassignment.TestConstants;
import com.ummetcivi.productorderassignment.data.entity.ProductEntity;
import com.ummetcivi.productorderassignment.data.jpa.ProductRepository;
import com.ummetcivi.productorderassignment.domain.CreateProductData;
import com.ummetcivi.productorderassignment.domain.Product;
import com.ummetcivi.productorderassignment.domain.UpdateProductData;
import com.ummetcivi.productorderassignment.exception.ResourceNotFoundException;
import com.ummetcivi.productorderassignment.util.IdGenerator;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Optional;

public class ProductServiceTest {

    private ProductService underTest;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private IdGenerator idGenerator;
    @Mock
    private ConverterService converterService;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new ProductService(productRepository, idGenerator, converterService);
    }

    @Test
    public void shouldCreateProduct() {
        // Given
        final CreateProductData data = Mockito.mock(CreateProductData.class);
        final Product product = Mockito.mock(Product.class);

        Mockito.when(data.getName()).thenReturn(TestConstants.ANY_PRODUCT_NAME);
        Mockito.when(data.getPrice()).thenReturn(TestConstants.ANY_PRODUCT_PRICE);

        Mockito.when(idGenerator.generate()).thenReturn(TestConstants.ANY_PRODUCT_ID);
        Mockito.when(productRepository.save(Mockito.any(ProductEntity.class)))
                .then(invocation -> invocation.getArgument(0));
        Mockito.when(converterService.convert(Mockito.any(ProductEntity.class), Mockito.eq(Product.class)))
                .thenReturn(product);

        // When
        final Product result = underTest.create(data);

        // Then
        Assert.assertNotNull(result);
        Assert.assertEquals(result, product);

        final ArgumentCaptor<ProductEntity> savedProductEntityArgumentCaptor = ArgumentCaptor
                .forClass(ProductEntity.class);
        Mockito.verify(productRepository).save(savedProductEntityArgumentCaptor.capture());
        final ProductEntity savedProductEntity = savedProductEntityArgumentCaptor.getValue();

        Assert.assertNotNull(savedProductEntity);
        Assert.assertEquals(savedProductEntity.getPrice(), TestConstants.ANY_PRODUCT_PRICE);
        Assert.assertEquals(savedProductEntity.getName(), TestConstants.ANY_PRODUCT_NAME);
        Assert.assertEquals(savedProductEntity.getId(), TestConstants.ANY_PRODUCT_ID);
        Assert.assertNotNull(savedProductEntity.getCreatedAt());

        Mockito.verify(converterService).convert(savedProductEntity, Product.class);
        Mockito.verify(idGenerator).generate();
    }

    @Test
    public void shouldUpdateProduct() {
        // Given
        final UpdateProductData data = Mockito.mock(UpdateProductData.class);
        final ProductEntity productEntity = ProductEntity.builder()
                .id(TestConstants.ANY_PRODUCT_ID)
                .name(TestConstants.ANY_PRODUCT_NAME)
                .price(TestConstants.ANY_PRODUCT_PRICE)
                .build();
        final Product product = Mockito.mock(Product.class);

        Mockito.when(data.getName()).thenReturn(TestConstants.ANY_OTHER_PRODUCT_NAME);
        Mockito.when(data.getPrice()).thenReturn(TestConstants.ANY_OTHER_PRODUCT_PRICE);

        Mockito.when(productRepository.findById(TestConstants.ANY_PRODUCT_ID)).thenReturn(Optional.of(productEntity));
        Mockito.when(productRepository.save(Mockito.any(ProductEntity.class)))
                .then(invocation -> invocation.getArgument(0));
        Mockito.when(converterService.convert(productEntity, Product.class)).thenReturn(product);

        // When
        final Product result = underTest.update(TestConstants.ANY_PRODUCT_ID, data);

        // Then
        Assert.assertNotNull(result);
        Assert.assertEquals(result, product);

        Mockito.verify(productRepository).findById(TestConstants.ANY_PRODUCT_ID);
        Mockito.verify(converterService).convert(productEntity, Product.class);

        final ArgumentCaptor<ProductEntity> savedProductEntityArgumentCaptor = ArgumentCaptor
                .forClass(ProductEntity.class);
        Mockito.verify(productRepository).save(savedProductEntityArgumentCaptor.capture());
        final ProductEntity savedProductEntity = savedProductEntityArgumentCaptor.getValue();

        Assert.assertEquals(savedProductEntity.getPrice(), TestConstants.ANY_OTHER_PRODUCT_PRICE);
        Assert.assertEquals(savedProductEntity.getName(), TestConstants.ANY_OTHER_PRODUCT_NAME);
    }

    @Test(expectedExceptions = ResourceNotFoundException.class)
    public void shouldNotUpdateAndThrowResourceNotFoundExceptionWhenProductNotFoundWithId() {
        // Given
        final UpdateProductData data = Mockito.mock(UpdateProductData.class);

        Mockito.when(productRepository.findById(TestConstants.ANY_PRODUCT_ID)).thenReturn(Optional.empty());

        try {
            // When
            underTest.update(TestConstants.ANY_PRODUCT_ID, data);
        } finally {
            // Then
            Mockito.verify(productRepository).findById(TestConstants.ANY_PRODUCT_ID);
            Mockito.verifyNoMoreInteractions(productRepository);
            Mockito.verifyNoInteractions(converterService);
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldGetAllProducts() {
        // Given
        final Page<ProductEntity> pagedProductEntity = Mockito.mock(Page.class);
        final Page<Product> pagedProduct = Mockito.mock(Page.class);
        final Pageable pageable = Mockito.mock(Pageable.class);

        Mockito.when(productRepository.findAll(pageable)).thenReturn(pagedProductEntity);
        Mockito.when(converterService.convertPage(pagedProductEntity, Product.class)).thenReturn(pagedProduct);

        // When
        final Page<Product> result = underTest.getAll(pageable);

        // Then
        Assert.assertNotNull(result);
        Assert.assertEquals(result, pagedProduct);

        Mockito.verify(productRepository).findAll(pageable);
        Mockito.verify(converterService).convertPage(pagedProductEntity, Product.class);
    }

    @Test
    public void shouldGetProductById() {
        // Given
        final ProductEntity productEntity = Mockito.mock(ProductEntity.class);
        final Product product = Mockito.mock(Product.class);

        Mockito.when(productRepository.findById(TestConstants.ANY_PRODUCT_ID)).thenReturn(Optional.of(productEntity));
        Mockito.when(converterService.convert(productEntity, Product.class)).thenReturn(product);

        // When
        final Product result = underTest.getById(TestConstants.ANY_PRODUCT_ID);

        // Then
        Assert.assertNotNull(result);
        Assert.assertEquals(result, product);

        Mockito.verify(productRepository).findById(TestConstants.ANY_PRODUCT_ID);
        Mockito.verify(converterService).convert(productEntity, Product.class);
    }

    @Test(expectedExceptions = ResourceNotFoundException.class)
    public void shouldThrowResourceNotFoundExceptionWhenProductNotFoundById() {
        try {
            // When
            underTest.getById(TestConstants.ANY_PRODUCT_ID);
        } finally {
            // Then
            Mockito.verify(productRepository).findById(TestConstants.ANY_PRODUCT_ID);
            Mockito.verify(converterService, Mockito.never())
                    .convert(Mockito.any(ProductEntity.class), Mockito.eq(Product.class));
        }
    }
}