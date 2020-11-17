package com.ey.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;

import org.junit.Test;

public class EntityTest {
    @Test
    public void testSetType() {
        Entity entity = new Entity();
        entity.setType("Type");
        assertEquals("Type", entity.getType());
    }

    @Test
    public void testSetFields() {
        Entity entity = new Entity();
        ArrayList<Field> fieldList = new ArrayList<Field>();
        entity.setFields(fieldList);
        assertSame(fieldList, entity.getFields());
    }
}

