package com.ey.beans;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestSetBeanTest {
    @Test
    public void testConstructor() {
        assertEquals("test-set", (new TestSetBean()).getType());
    }

    @Test
    public void testSetType() {
        TestSetBean testSetBean = new TestSetBean();
        testSetBean.setType("Type");
        assertEquals("Type", testSetBean.getType());
    }
}

