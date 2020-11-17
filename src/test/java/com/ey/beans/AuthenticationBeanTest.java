package com.ey.beans;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AuthenticationBeanTest {
    @Test
    public void testSetUser() {
        AuthenticationBean authenticationBean = new AuthenticationBean();
        authenticationBean.setUser("User");
        assertEquals("User", authenticationBean.getUser());
    }

    @Test
    public void testSetPassword() {
        AuthenticationBean authenticationBean = new AuthenticationBean();
        authenticationBean.setPassword("iloveyou");
        assertEquals("iloveyou", authenticationBean.getPassword());
    }
}

