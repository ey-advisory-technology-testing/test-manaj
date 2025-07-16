package com.ey.xray.serverDC.business.settings;

import java.io.File;
import java.io.IOException;

public interface Settings {

    public void getRequirementProjects(File authDataFile, String baseUrl) throws IOException;

    public void activateProjectsForXray(File authDataFile, String baseUrl, File jsonBody) throws IOException;

    }
