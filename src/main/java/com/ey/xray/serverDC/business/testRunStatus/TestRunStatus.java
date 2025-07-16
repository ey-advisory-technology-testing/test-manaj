package com.ey.xray.serverDC.business.testRunStatus;

import java.io.File;
import java.io.IOException;

public interface TestRunStatus {

    public void resetTestRunStatus(File authDataFile, String baseUrl, String issueKey, File testFile) throws IOException;

}
