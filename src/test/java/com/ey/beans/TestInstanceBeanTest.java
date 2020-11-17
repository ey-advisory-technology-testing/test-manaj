package com.ey.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class TestInstanceBeanTest {
    @Test
    public void testConstructor() {
        assertEquals("test-instance", (new TestInstanceBean()).getType());
    }

    @Test
    public void testSetType() {
        TestInstanceBean testInstanceBean = new TestInstanceBean();
        testInstanceBean.setType("Type");
        assertEquals("Type", testInstanceBean.getType());
    }

    @Test
    public void testGetchildrenCount() {
        assertNull((new TestInstanceBean()).getchildrenCount());
    }
}

