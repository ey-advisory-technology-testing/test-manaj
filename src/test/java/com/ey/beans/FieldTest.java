package com.ey.beans;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FieldTest {
    @Test
    public void testSetName() {
        Field field = new Field();
        field.setName("Name");
        assertEquals("Name", field.getName());
    }

    @Test
    public void testSetValue() {
        Field field = new Field();
        field.setValue("value");
        assertEquals("value", field.getValue());
    }
}

