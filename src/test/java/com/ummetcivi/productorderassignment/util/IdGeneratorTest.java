package com.ummetcivi.productorderassignment.util;

import org.springframework.util.StringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class IdGeneratorTest {

    private IdGenerator underTest;

    @BeforeMethod
    public void setUp() {
        underTest = new IdGenerator();
    }

    @Test
    public void shouldGenerateId() {
        // When
        final String result = underTest.generate();

        // Then
        Assert.assertNotNull(result);
        Assert.assertTrue(StringUtils.hasText(result));
    }
}