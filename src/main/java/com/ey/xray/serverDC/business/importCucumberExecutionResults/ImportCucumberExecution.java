package com.ey.xray.serverDC.business.importCucumberExecutionResults;

import java.io.File;
import java.io.IOException;

public interface ImportCucumberExecution {

    public void importExecution(File authDataFile, String baseUrl, File requestBody) throws IOException;

    public void importExecutionMultipart(File authDataFile, String baseUrl, File infoJson, File result) throws IOException;


    }
