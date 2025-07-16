package com.ey.xray.serverDC.business.externalApps;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public interface ExternalApps {

    public void getXrayLicense(File authDataFile, String baseUrl) throws IOException;

    }
