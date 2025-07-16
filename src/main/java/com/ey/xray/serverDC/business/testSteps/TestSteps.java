package com.ey.xray.serverDC.business.testSteps;

import java.io.File;
import java.io.IOException;

public interface TestSteps {

    public void getStepsFromTest(File authDataFile, String baseUrl, String testSetKey) throws IOException;

    public void createNewTestStep(File authDataFile, String baseUrl, String testSetKey, File jsonBody) throws IOException;

    public void getSingleStepFromTest(File authDataFile, String baseUrl, String testSetKey, String stepId) throws IOException;

    public void updateExistingTestStep(File authDataFile, String baseUrl, String testSetKey, String stepId, File jsonBody) throws IOException;

    public void deleteTestStep(File authDataFile, String baseUrl, String testSetKey, String stepId) throws IOException;

    public void getAttachmentsFromTestStep(File authDataFile, String baseUrl, String testSetKey, String stepId) throws IOException;

    public void deleteAttachmentFromTestStep(File authDataFile, String baseUrl, String testSetKey, String stepId, String attachmentId) throws IOException;

    }
