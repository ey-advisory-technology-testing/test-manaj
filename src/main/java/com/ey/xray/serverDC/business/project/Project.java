package com.ey.xray.serverDC.business.project;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public interface Project {

    public void getProjectTestRunCustomFields(File authDataFile, String baseUrl, String projectId) throws IOException;

    public void getProjectTestStepsCustomFields(File authDataFile, String baseUrl, String projectId) throws IOException;

    }
