package com.ey.testmanaj.connection;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.google.api.client.http.HttpRequestFactory;
import org.junit.Test;

public class AuthenticatorTest {
    @Test
    public void testGetRequestFactory() {
        HttpRequestFactory actualRequestFactory = Authenticator.getRequestFactory();
        assertNull(actualRequestFactory.getInitializer());
        assertTrue(actualRequestFactory.getTransport() instanceof com.google.api.client.http.javanet.NetHttpTransport);
    }
}

