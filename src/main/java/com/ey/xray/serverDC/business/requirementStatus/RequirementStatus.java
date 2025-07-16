package com.ey.xray.serverDC.business.requirementStatus;

import java.io.File;
import java.io.IOException;

public interface RequirementStatus {

    public void resetRequirementStatus(File authDataFile, String baseUrl, String issueKey, File jsonBody) throws IOException;

}
