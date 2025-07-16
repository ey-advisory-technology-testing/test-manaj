package com.ey.xray.serverDC.business.testRuns;

import java.io.File;
import java.io.IOException;

public interface TestRuns {

    public void getTestRunWithTestExecAndTestKey(File authDataFile, String baseUrl, String testExecIssueKey, String testIssueKey) throws IOException;

    public void getTestRunWithTestId(File authDataFile, String baseUrl, String testId) throws IOException;

    public void updateTestRun(File authDataFile, String baseUrl, String testRunId, File jsonBody) throws IOException;

    public void getTestRunCustomField(File authDataFile, String baseUrl, String testRunId, String customFieldId) throws IOException;

    public void updateTestRunCustomField(File authDataFile, String baseUrl, String testRunId, String customFieldId, File jsonBody) throws IOException;

    public void getTestRunIteration(File authDataFile, String baseUrl, String testRunId, String iterationId) throws IOException;

    public void updateTestRunIteration(File authDataFile, String baseUrl, String testRunId, String iterationId, File jsonBody) throws IOException ;

    public void getTestRunIterationStep(File authDataFile, String baseUrl, String testRunId, String iterationId) throws IOException;

    public void getTestRunIterationStepResult(File authDataFile, String baseUrl, String testRunId, String iterationId, String stepResultId) throws IOException;

    public void updateTestRunIterationTestStepResult(File authDataFile, String baseUrl, String testRunId, String iterationId, String stepResultId, File jsonBody) throws IOException;

    public void getTestRunIterationStepResultStatus(File authDataFile, String baseUrl, String testRunId, String iterationId, String stepResultId) throws IOException;

    public void updateTestRunIterationTestStepResultStatus(File authDataFile, String baseUrl, String testRunId, String iterationId, String stepResultId, String status, File jsonBody) throws IOException;

    public void getTestRunIterationStepResultAttachment(File authDataFile, String baseUrl, String testRunId, String iterationId, String stepResultId) throws IOException;

    public void addTestRunIterationStepResultAttachment(File authDataFile, String baseUrl, String testRunId, String iterationId, String stepResultId, File jsonBody) throws IOException;

    public void testRunDeleteAllAttachments(File authDataFile, String baseUrl, String testRunId, String iterationId, String stepResultId) throws IOException;

    public void testRunDeleteSpecificAttachment(File authDataFile, String baseUrl, String testRunId, String iterationId, String stepResultId, String attachmentId) throws IOException;

    }
