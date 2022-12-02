package com.edev.support.utils;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class NameUtilsTest {
    @Test
    public void testConvertToCamelCase() {
        String name = NameUtils.convertToCamelCase("hello_world_to_you");
        assertThat(name, equalTo("helloWorldToYou"));
    }
    @Test
    public void testConvertToUnderline() {
        String name = NameUtils.convertToUnderline("helloWorldToYou");
        assertThat(name, equalTo("hello_world_to_you"));
    }
    @Test
    public void testConvertToFirstUpperCase() {
        String name = NameUtils.convertToFirstUpperCase("helloWorldToYou");
        assertThat(name, equalTo("HelloWorldToYou"));
    }
}
