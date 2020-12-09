package com.ey.testmanaj.urls;

import com.ey.testmanaj.App;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang.SystemUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.FileSystems;

import static org.junit.Assert.*;

public class BuildURLTest {

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
    }

    @Test
    public void testGetAuthenticationURL() {
        assertEquals("http://localhost:8888/qcbin/authentication-point/authenticate", BuildURL.getURL("authentication"));
    }

    @Test
    public void testGetCheckAuthentication() {
        assertEquals("http://localhost:8888/qcbin/?login-form-required=y/rest/is-authenticated", BuildURL.getURL("isAuthenticated"));
    }

    @Test
    public void testGetTestSetFolderURL() {
        assertEquals("http://localhost:8888/qcbin/?login-form-required=y/rest/domains/TRAINING/projects/ALM_Sample/test-set-folders",
                BuildURL.getURL("testSetFolder"));
    }

    @Test
    public void testGetTestRunURL() {
        assertEquals("http://localhost:8888/qcbin/?login-form-required=y/rest/domains/TRAINING/projects/ALM_Sample/runs", BuildURL.getURL("testRun"));
    }

    @Test
    public void testGetProjectURL() {
        assertEquals("http://localhost:8888/qcbin/?login-form-required=y/rest/domains/TRAINING/projects/ALM_Sample", BuildURL.getURL("baseProject"));
    }

    @Test
    public void testGetTestInstanceURL() {
        assertEquals("http://localhost:8888/qcbin/?login-form-required=y/rest/domains/TRAINING/projects/ALM_Sample/test-instances",
                BuildURL.getURL("testInstance"));
    }

    @Test
    public void testCreateTestSet() {
        assertEquals("http://localhost:8888/qcbin/?login-form-required=y/rest/domains/TRAINING/projects/ALM_Sample/test-sets", BuildURL.getURL("createTestSet"));
    }

    @Test
    public void testGetCreateTestRunURL() {
        assertEquals("http://localhost:8888/qcbin/?login-form-required=y/rest/domains/TRAINING/projects/ALM_Sample/runs", BuildURL.getURL("createTestRun"));
    }

    @Test
    public void testGetSessionURL() {
        assertEquals("http://localhost:8888/qcbin/rest/site-session", BuildURL.getURL("getSession"));
    }

    @Test
    public void testGetGenericURL() throws IOException {
        new App(SystemUtils.getUserDir().toString(),
                "src" + FileSystems.getDefault().getSeparator() +
                "test" + FileSystems.getDefault().getSeparator() +
                "resources" + FileSystems.getDefault().getSeparator() +
                "testResources" + FileSystems.getDefault().getSeparator());
        BuildURL.setEnv();
        assertNotNull(BuildURL.getGenericURL("authentication"));
        assertNull(BuildURL.getGenericURL("https://example.org/example"));
    }

    @Test
    public void testBuildSessionXML() throws JsonProcessingException {
        assertEquals("<session-parameters><client-type>REST Client</client-type></session-parameters>",
                BuildURL.buildSessionXML());
    }

    @Test
    public void testGetTestsURL() {
        assertEquals("http://localhost:8888/qcbin/?login-form-required=y/rest/domains/TRAINING/projects/ALM_Sample/tests", BuildURL.getURL("testPlan"));
    }
}

