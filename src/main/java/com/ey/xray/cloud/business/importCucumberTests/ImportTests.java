package com.ey.xray.cloud.business.importCucumberTests;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public interface ImportTests {

    public void importTestScenarios(File authDataFile, File multipartDataFile, File featureFile) throws IOException, NoSuchAlgorithmException, KeyManagementException;

}
