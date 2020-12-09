package com.ey.testmanaj.urls;

import com.ey.testmanaj.App;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang.SystemUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.FileSystems;

import static org.junit.Assert.*;

public class BuildURLTest {
    @Test
    public void testGetAuthenticationURL() {
        assertEquals("nullauthentication-point/authenticate", BuildURL.getAuthenticationURL());
    }

    @Test
    public void testGetCheckAuthentication() {
        assertEquals("null?login-form-required=y/rest/is-authenticated", BuildURL.getCheckAuthentication());
    }

    @Test
    public void testGetURL() {
        assertEquals("", BuildURL.getURL("https://example.org/example"));
    }

    @Test
    public void testGetTestSetFolderURL() {
        assertEquals("null?login-form-required=y/rest/domains/null/projects/null/test-set-folders",
                BuildURL.getTestSetFolderURL());
    }

    @Test
    public void testGetTestRunURL() {
        assertEquals("null?login-form-required=y/rest/domains/null/projects/null/runs", BuildURL.getTestRunURL());
    }

    @Test
    public void testGetProjectURL() {
        assertEquals("null?login-form-required=y/rest/domains/null/projects/null", BuildURL.getProjectURL());
    }

    @Test
    public void testGetTestInstanceURL() {
        assertEquals("null?login-form-required=y/rest/domains/null/projects/null/test-instances",
                BuildURL.getTestInstanceURL());
    }

    @Test
    public void testCreateTestSet() {
        assertEquals("null?login-form-required=y/rest/domains/null/projects/null/test-sets", BuildURL.createTestSet());
    }

    @Test
    public void testGetCreateTestRunURL() {
        assertEquals("null?login-form-required=y/rest/domains/null/projects/null/runs", BuildURL.getCreateTestRunURL());
    }

    @Test
    public void testGetSessionURL() {
        assertEquals("nullrest/site-session", BuildURL.getSessionURL());
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
        assertEquals("null?login-form-required=y/rest/domains/null/projects/null/tests", BuildURL.getTestsURL());
    }
}

