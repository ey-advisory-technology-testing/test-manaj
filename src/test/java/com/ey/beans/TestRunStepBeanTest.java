package com.ey.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.LinkedHashMap;

import org.junit.Test;

public class TestRunStepBeanTest {
    @Test
    public void testConstructor() {
        assertEquals("run-step", (new TestRunStepBean()).getType());
    }

    @Test
    public void testUpdateTestRunStepXML() {
        TestRunStepBean testRunStepBean = new TestRunStepBean();
        assertSame(testRunStepBean, testRunStepBean.updateTestRunStepXML("Status"));
    }

    @Test
    public void testCreateTestRunStepXML() {
        TestRunStepBean testRunStepBean = new TestRunStepBean();
        assertSame(testRunStepBean, testRunStepBean.createTestRunStepXML(new LinkedHashMap<String, String>(), "Step Name"));
    }

    @Test
    public void testSetType() {
        TestRunStepBean testRunStepBean = new TestRunStepBean();
        testRunStepBean.setType("Type");
        assertEquals("Type", testRunStepBean.getType());
    }
}

