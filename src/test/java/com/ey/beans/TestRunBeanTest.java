package com.ey.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.LinkedHashMap;

import org.junit.Test;

public class TestRunBeanTest {
    @Test
    public void testConstructor() {
        assertEquals("run", (new TestRunBean()).getType());
    }

    @Test
    public void testUpdateTestRunXML() {
        TestRunBean testRunBean = new TestRunBean();
        assertSame(testRunBean, testRunBean.updateTestRunXML("Status"));
    }

    @Test
    public void testUpdateAllFieldsTestRunXML() {
        TestRunBean testRunBean = new TestRunBean();
        assertSame(testRunBean, testRunBean.updateAllFieldsTestRunXML(new LinkedHashMap<String, String>()));
    }

    @Test
    public void testSetType() {
        TestRunBean testRunBean = new TestRunBean();
        testRunBean.setType("Type");
        assertEquals("Type", testRunBean.getType());
    }
}

