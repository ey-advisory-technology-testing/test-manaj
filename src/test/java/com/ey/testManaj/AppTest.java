package com.ey.testManaj;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AppTest {
    @Test
    public void testConstructor() {
        new App("Path");
        assertEquals("Path", App.resourcesFilePath);
    }
}

