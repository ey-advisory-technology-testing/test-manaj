package com.ey.xray.serverDC.business.issueTypes;

import java.io.File;
import java.io.IOException;

public interface IssueTypes {

    public void getXrayIssueTypes(File authDataFile, String baseUrl) throws IOException;

    public void installXrayIssueTypeSchemeForProjects(File authDataFile, String baseUrl, File jsonBody) throws IOException;


    }
