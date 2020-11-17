package com.ey.beans;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SessionBeanTest {
    @Test
    public void testSetClientType() {
        SessionBean sessionBean = new SessionBean();
        sessionBean.setClientType("Client Type");
        assertEquals("Client Type", sessionBean.getClientType());
    }
}

