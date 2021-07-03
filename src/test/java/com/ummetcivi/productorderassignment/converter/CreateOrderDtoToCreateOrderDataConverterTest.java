package com.ummetcivi.productorderassignment.converter;

import com.ummetcivi.productorderassignment.TestConstants;
import com.ummetcivi.productorderassignment.controller.dto.CreateOrderDto;
import com.ummetcivi.productorderassignment.controller.dto.CreateOrderProductDto;
import com.ummetcivi.productorderassignment.domain.CreateOrderData;
import com.ummetcivi.productorderassignment.domain.CreateOrderProductData;
import com.ummetcivi.productorderassignment.service.ConverterService;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class CreateOrderDtoToCreateOrderDataConverterTest {

    private CreateOrderDtoToCreateOrderDataConverter underTest;

    @Mock
    private ConverterService converterService;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new CreateOrderDtoToCreateOrderDataConverter(converterService);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldConvert() {
        // Given
        final List<CreateOrderProductDto> products = Mockito.mock(List.class);
        final List<CreateOrderProductData> productDataList = Mockito.mock(List.class);
        final CreateOrderDto dto = CreateOrderDto.builder()
                .buyerEmail(TestConstants.ANY_BUYER_EMAIL)
                .products(products)
                .build();

        Mockito.when(converterService.convertList(products, CreateOrderProductData.class)).thenReturn(productDataList);

        // When
        final CreateOrderData result = underTest.convert(dto);

        // Then
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getBuyerEmail(), TestConstants.ANY_BUYER_EMAIL);
        Assert.assertEquals(result.getProducts(), productDataList);

        Mockito.verify(converterService).convertList(products, CreateOrderProductData.class);
    }
}