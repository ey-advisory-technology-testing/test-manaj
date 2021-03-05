package com.ey.utilities;

import com.ey.testmanaj.App;
import com.ey.testmanaj.urls.BuildURL;
import org.apache.commons.lang.SystemUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class PropertyReaderTest {
    PropertyReader reader = new PropertyReader();
    private Set<String> setA = new HashSet<>();

    private App app;

    @Before
    public void setup() throws IOException {
        app = new App(SystemUtils.getUserDir().toString(),
                FileSystems.getDefault().getSeparator() +
                        "src" + FileSystems.getDefault().getSeparator() +
                        "test" + FileSystems.getDefault().getSeparator() +
                        "resources" + FileSystems.getDefault().getSeparator() +
                        "testResources" + FileSystems.getDefault().getSeparator());

        BuildURL.setEnv();
        setA.add("project");
        setA.add("baseURL");
        setA.add("password");
        setA.add("domain");
        setA.add("username");
        setA.add("testSetFolderPath");
        setA.add("always_create_new_tset_instance");
        setA.add("login_form_required");
    }

    @Test
    public void getAllKeys() throws IOException {
        assertEquals(setA, reader.getAllKeys());
    }
}