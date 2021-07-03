package com.ummetcivi.productorderassignment.service;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class ConverterServiceTest {

    private ConverterService underTest;

    @Mock
    private ConversionService conversionService;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new ConverterService();
        ReflectionTestUtils.setField(underTest, "conversionService", conversionService);
    }

    @Test
    public void shouldConvertPage() {
        // Given
        final Source sourceObject = Mockito.mock(Source.class);
        final Destination destinationObject = Mockito.mock(Destination.class);

        final Page<Source> sourcePage = Mockito.spy(new PageImpl<>(List.of(sourceObject)));
        final Page<Destination> destinationPage = new PageImpl<>(List.of(destinationObject));

        Mockito.when(conversionService.convert(sourceObject, Destination.class)).thenReturn(destinationObject);

        // When
        final Page<Destination> result = underTest.convertPage(sourcePage, Destination.class);

        // Then
        Assert.assertNotNull(result);
        Assert.assertEquals(result, destinationPage);

        Mockito.verify(sourcePage).map(Mockito.any());
        Mockito.verify(conversionService).convert(sourceObject, Destination.class);
    }

    @Test
    public void shouldReturnEmptyPageWhenSourcePageIsNull() {
        // When
        final Page<Destination> result = underTest.convertPage(null, Destination.class);

        // Then
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void shouldConvertList() {
        // Given
        final Source sourceObject = Mockito.mock(Source.class);
        final Destination destinationObject = Mockito.mock(Destination.class);
        final List<Source> sourceList = List.of(sourceObject);

        Mockito.when(conversionService.convert(sourceObject, Destination.class)).thenReturn(destinationObject);

        // When
        final List<Destination> result = underTest.convertList(sourceList, Destination.class);

        // Then
        Assert.assertNotNull(result);
        Assert.assertEquals(result, List.of(destinationObject));

        Mockito.verify(conversionService).convert(sourceObject, Destination.class);
    }

    @Test
    public void shouldReturnEmptyListWhenSourceListIsNull() {
        // When
        final List<Destination> result = underTest.convertList(null, Destination.class);

        // Then
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void shouldConvert() {
        // Given
        final Source sourceObject = Mockito.mock(Source.class);
        final Destination destinationObject = Mockito.mock(Destination.class);

        Mockito.when(conversionService.convert(sourceObject, Destination.class)).thenReturn(destinationObject);

        // When
        final Destination result = underTest.convert(sourceObject, Destination.class);

        // Then
        Assert.assertNotNull(result);
        Assert.assertEquals(result, destinationObject);

        Mockito.verify(conversionService).convert(sourceObject, Destination.class);
    }

    private static class Source {

    }

    private static class Destination {

    }
}