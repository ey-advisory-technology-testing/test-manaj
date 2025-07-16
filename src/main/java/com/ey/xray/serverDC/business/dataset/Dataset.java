package com.ey.xray.serverDC.business.dataset;

import java.io.File;
import java.io.IOException;

public interface Dataset {

    public void getDatasetCSV(File authDataFile, String baseUrl) throws IOException;

    public void importDataset(File authDataFile, String baseUrl, String testIssueId, File csvFile) throws IOException;

    }
