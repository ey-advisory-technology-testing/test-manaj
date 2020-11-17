package com.ey.testManaj.apirequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;

import java.io.IOException;

import org.junit.Test;

public class BuildRequestsTest {
    @Test
    public void testCreatePutRequest() throws IOException {
        BuildRequests buildRequests = new BuildRequests();
        HttpRequest actualCreatePutRequestResult = buildRequests.createPutRequest(new GenericUrl(), "Pay Load");
        assertTrue(actualCreatePutRequestResult.getThrowExceptionOnExecuteError());
        assertEquals(20000, actualCreatePutRequestResult.getConnectTimeout());
        assertEquals(0, actualCreatePutRequestResult.getWriteTimeout());
        assertEquals(20000, actualCreatePutRequestResult.getReadTimeout());
        assertFalse(actualCreatePutRequestResult.getRetryOnExecuteIOException());
        assertFalse(actualCreatePutRequestResult.getResponseReturnRawInputStream());
        assertTrue(actualCreatePutRequestResult.getFollowRedirects());
        assertTrue(actualCreatePutRequestResult.isLoggingEnabled());
        assertEquals(10, actualCreatePutRequestResult.getNumberOfRetries());
        assertTrue(
                actualCreatePutRequestResult.getTransport() instanceof com.google.api.client.http.javanet.NetHttpTransport);
        assertEquals(16384, actualCreatePutRequestResult.getContentLoggingLimit());
        assertEquals(0, actualCreatePutRequestResult.getUrl().size());
        HttpContent content = actualCreatePutRequestResult.getContent();
        assertTrue(content instanceof ByteArrayContent);
        assertEquals("PUT", actualCreatePutRequestResult.getRequestMethod());
        assertEquals(1, actualCreatePutRequestResult.getResponseHeaders().size());
        assertTrue(actualCreatePutRequestResult.isCurlLoggingEnabled());
        assertEquals(1, actualCreatePutRequestResult.getHeaders().size());
        assertEquals("application/xml", content.getType());
        assertTrue(((ByteArrayContent) content).getCloseInputStream());
        assertTrue(((ByteArrayContent) content).getInputStream() instanceof java.io.ByteArrayInputStream);
        assertEquals(8L, content.getLength());
    }

    @Test
    public void testCreatePutRequest2() throws IOException {
        GenericUrl url = new GenericUrl("https://example.org/example");
        HttpRequest actualCreatePutRequestResult = (new BuildRequests()).createPutRequest(url, "Pay Load");
        assertTrue(actualCreatePutRequestResult.getThrowExceptionOnExecuteError());
        assertEquals(20000, actualCreatePutRequestResult.getConnectTimeout());
        assertEquals(0, actualCreatePutRequestResult.getWriteTimeout());
        assertEquals(20000, actualCreatePutRequestResult.getReadTimeout());
        assertFalse(actualCreatePutRequestResult.getRetryOnExecuteIOException());
        assertFalse(actualCreatePutRequestResult.getResponseReturnRawInputStream());
        assertTrue(actualCreatePutRequestResult.getFollowRedirects());
        assertTrue(actualCreatePutRequestResult.isLoggingEnabled());
        assertEquals(10, actualCreatePutRequestResult.getNumberOfRetries());
        assertTrue(
                actualCreatePutRequestResult.getTransport() instanceof com.google.api.client.http.javanet.NetHttpTransport);
        assertEquals(16384, actualCreatePutRequestResult.getContentLoggingLimit());
        assertEquals(0, actualCreatePutRequestResult.getUrl().size());
        HttpContent content = actualCreatePutRequestResult.getContent();
        assertTrue(content instanceof ByteArrayContent);
        assertEquals("PUT", actualCreatePutRequestResult.getRequestMethod());
        assertEquals(1, actualCreatePutRequestResult.getResponseHeaders().size());
        assertTrue(actualCreatePutRequestResult.isCurlLoggingEnabled());
        assertEquals(1, actualCreatePutRequestResult.getHeaders().size());
        assertEquals("application/xml", content.getType());
        assertTrue(((ByteArrayContent) content).getCloseInputStream());
        assertTrue(((ByteArrayContent) content).getInputStream() instanceof java.io.ByteArrayInputStream);
        assertEquals(8L, content.getLength());
    }

    @Test
    public void testCreateGetRequest() throws IOException {
        BuildRequests buildRequests = new BuildRequests();
        HttpRequest actualCreateGetRequestResult = buildRequests.createGetRequest(new GenericUrl());
        assertTrue(actualCreateGetRequestResult.getThrowExceptionOnExecuteError());
        assertEquals(20000, actualCreateGetRequestResult.getConnectTimeout());
        assertEquals(0, actualCreateGetRequestResult.getWriteTimeout());
        assertEquals(20000, actualCreateGetRequestResult.getReadTimeout());
        assertFalse(actualCreateGetRequestResult.getRetryOnExecuteIOException());
        assertFalse(actualCreateGetRequestResult.getResponseReturnRawInputStream());
        assertTrue(actualCreateGetRequestResult.getFollowRedirects());
        assertTrue(actualCreateGetRequestResult.isLoggingEnabled());
        assertEquals(10, actualCreateGetRequestResult.getNumberOfRetries());
        assertTrue(
                actualCreateGetRequestResult.getTransport() instanceof com.google.api.client.http.javanet.NetHttpTransport);
        assertEquals(16384, actualCreateGetRequestResult.getContentLoggingLimit());
        assertEquals(0, actualCreateGetRequestResult.getUrl().size());
        assertEquals("GET", actualCreateGetRequestResult.getRequestMethod());
        assertEquals(1, actualCreateGetRequestResult.getResponseHeaders().size());
        assertEquals(1, actualCreateGetRequestResult.getHeaders().size());
        assertTrue(actualCreateGetRequestResult.isCurlLoggingEnabled());
    }

    @Test
    public void testCreateGetRequest2() throws IOException {
        GenericUrl url = new GenericUrl("https://example.org/example");
        HttpRequest actualCreateGetRequestResult = (new BuildRequests()).createGetRequest(url);
        assertTrue(actualCreateGetRequestResult.getThrowExceptionOnExecuteError());
        assertEquals(20000, actualCreateGetRequestResult.getConnectTimeout());
        assertEquals(0, actualCreateGetRequestResult.getWriteTimeout());
        assertEquals(20000, actualCreateGetRequestResult.getReadTimeout());
        assertFalse(actualCreateGetRequestResult.getRetryOnExecuteIOException());
        assertFalse(actualCreateGetRequestResult.getResponseReturnRawInputStream());
        assertTrue(actualCreateGetRequestResult.getFollowRedirects());
        assertTrue(actualCreateGetRequestResult.isLoggingEnabled());
        assertEquals(10, actualCreateGetRequestResult.getNumberOfRetries());
        assertTrue(
                actualCreateGetRequestResult.getTransport() instanceof com.google.api.client.http.javanet.NetHttpTransport);
        assertEquals(16384, actualCreateGetRequestResult.getContentLoggingLimit());
        assertEquals(0, actualCreateGetRequestResult.getUrl().size());
        assertEquals("GET", actualCreateGetRequestResult.getRequestMethod());
        assertEquals(1, actualCreateGetRequestResult.getResponseHeaders().size());
        assertEquals(1, actualCreateGetRequestResult.getHeaders().size());
        assertTrue(actualCreateGetRequestResult.isCurlLoggingEnabled());
    }

    @Test
    public void testCreatePostRequest() throws IOException {
        BuildRequests buildRequests = new BuildRequests();
        HttpRequest actualCreatePostRequestResult = buildRequests.createPostRequest(new GenericUrl(), "Pay Load");
        assertTrue(actualCreatePostRequestResult.getThrowExceptionOnExecuteError());
        assertEquals(20000, actualCreatePostRequestResult.getConnectTimeout());
        assertEquals(0, actualCreatePostRequestResult.getWriteTimeout());
        assertEquals(20000, actualCreatePostRequestResult.getReadTimeout());
        assertFalse(actualCreatePostRequestResult.getRetryOnExecuteIOException());
        assertFalse(actualCreatePostRequestResult.getResponseReturnRawInputStream());
        assertTrue(actualCreatePostRequestResult.getFollowRedirects());
        assertTrue(actualCreatePostRequestResult.isLoggingEnabled());
        assertEquals(10, actualCreatePostRequestResult.getNumberOfRetries());
        assertTrue(
                actualCreatePostRequestResult.getTransport() instanceof com.google.api.client.http.javanet.NetHttpTransport);
        assertEquals(16384, actualCreatePostRequestResult.getContentLoggingLimit());
        assertEquals(0, actualCreatePostRequestResult.getUrl().size());
        HttpContent content = actualCreatePostRequestResult.getContent();
        assertTrue(content instanceof ByteArrayContent);
        assertEquals("POST", actualCreatePostRequestResult.getRequestMethod());
        assertEquals(1, actualCreatePostRequestResult.getResponseHeaders().size());
        assertTrue(actualCreatePostRequestResult.isCurlLoggingEnabled());
        assertEquals(1, actualCreatePostRequestResult.getHeaders().size());
        assertEquals("application/xml", content.getType());
        assertTrue(((ByteArrayContent) content).getCloseInputStream());
        assertTrue(((ByteArrayContent) content).getInputStream() instanceof java.io.ByteArrayInputStream);
        assertEquals(8L, content.getLength());
    }

    @Test
    public void testCreateDeleteRequest() throws IOException {
        BuildRequests buildRequests = new BuildRequests();
        HttpRequest actualCreateDeleteRequestResult = buildRequests.createDeleteRequest(new GenericUrl());
        assertTrue(actualCreateDeleteRequestResult.getThrowExceptionOnExecuteError());
        assertEquals(20000, actualCreateDeleteRequestResult.getConnectTimeout());
        assertEquals(0, actualCreateDeleteRequestResult.getWriteTimeout());
        assertEquals(20000, actualCreateDeleteRequestResult.getReadTimeout());
        assertFalse(actualCreateDeleteRequestResult.getRetryOnExecuteIOException());
        assertFalse(actualCreateDeleteRequestResult.getResponseReturnRawInputStream());
        assertTrue(actualCreateDeleteRequestResult.getFollowRedirects());
        assertTrue(actualCreateDeleteRequestResult.isLoggingEnabled());
        assertEquals(10, actualCreateDeleteRequestResult.getNumberOfRetries());
        assertTrue(
                actualCreateDeleteRequestResult.getTransport() instanceof com.google.api.client.http.javanet.NetHttpTransport);
        assertEquals(16384, actualCreateDeleteRequestResult.getContentLoggingLimit());
        assertEquals(0, actualCreateDeleteRequestResult.getUrl().size());
        assertEquals("DELETE", actualCreateDeleteRequestResult.getRequestMethod());
        assertEquals(1, actualCreateDeleteRequestResult.getResponseHeaders().size());
        assertEquals(1, actualCreateDeleteRequestResult.getHeaders().size());
        assertTrue(actualCreateDeleteRequestResult.isCurlLoggingEnabled());
    }
}

