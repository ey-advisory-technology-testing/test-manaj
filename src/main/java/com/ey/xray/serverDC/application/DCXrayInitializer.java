package com.ey.xray.serverDC.application;

import com.ey.xray.serverDC.business.dataset.Dataset;
import com.ey.xray.serverDC.business.dataset.DatasetImpl;
import com.ey.xray.serverDC.business.externalApps.ExternalApps;
import com.ey.xray.serverDC.business.externalApps.ExternalAppsImpl;
import com.ey.xray.serverDC.business.importCucumberExecutionResults.ImportCucumberExectutionImpl;
import com.ey.xray.serverDC.business.importCucumberExecutionResults.ImportCucumberExecution;
import com.ey.xray.serverDC.business.issueTypes.IssueTypes;
import com.ey.xray.serverDC.business.issueTypes.IssueTypesImpl;
import com.ey.xray.serverDC.business.project.Project;
import com.ey.xray.serverDC.business.project.ProjectImpl;
import com.ey.xray.serverDC.business.requirementStatus.RequirementStatus;
import com.ey.xray.serverDC.business.requirementStatus.RequirementStatusImpl;
import com.ey.xray.serverDC.business.settings.Settings;
import com.ey.xray.serverDC.business.settings.SettingsImpl;
import com.ey.xray.serverDC.business.testPlans.TestPlans;
import com.ey.xray.serverDC.business.testPlans.TestPlansImpl;
import com.ey.xray.serverDC.business.testRunStatus.TestRunStatus;
import com.ey.xray.serverDC.business.testRunStatus.TestRunStatusImpl;
import com.ey.xray.serverDC.business.testRuns.TestRuns;
import com.ey.xray.serverDC.business.testRuns.TestRunsImpl;
import com.ey.xray.serverDC.business.testSets.TestSets;
import com.ey.xray.serverDC.business.testSets.TestSetsImpl;
import com.ey.xray.serverDC.business.testSteps.TestSteps;
import com.ey.xray.serverDC.business.testSteps.TestStepsImpl;
import java.io.File;
import java.io.IOException;

/**
 * This is the class that initializes all the xray.business interfaces/classes to the consuming project. This will be called from consuming project which
 * can then start calling the obfuscated methods of the xray.business layer using their corresponding interfaces
 */
public class DCXrayInitializer {

    TestRuns testRuns = new TestRunsImpl();
    TestPlans testPlans = new TestPlansImpl();
    TestSteps testSteps = new TestStepsImpl();
    TestRunStatus testRunStatus = new TestRunStatusImpl();
    Settings settings = new SettingsImpl();
    RequirementStatus requirementStatus = new RequirementStatusImpl();
    Project project = new ProjectImpl();
    IssueTypes issueTypes = new IssueTypesImpl();
    ExternalApps externalApps = new ExternalAppsImpl();
    Dataset dataset = new DatasetImpl();
    TestSets testSets = new TestSetsImpl();
    ImportCucumberExecution importCucumberExecution = new ImportCucumberExectutionImpl();




    /**
     * Get Test Run
     *
     * @param authDataFile      the auth data file
     * @throws IOException              the io exception
     */
    public void getTestRunWithTestExecAndTestKey(File authDataFile, String baseUrl, String testExecIssueKey, String testIssueKey) throws IOException {
        testRuns.getTestRunWithTestExecAndTestKey(authDataFile,baseUrl,testExecIssueKey,testIssueKey);
    }

    /**
     *
     * @param authDataFile
     * @param baseUrl
     * @param testId
     * @throws IOException
     */
    public void getTestRunWithTestId(File authDataFile, String baseUrl, String testId) throws IOException {
        testRuns.getTestRunWithTestId(authDataFile,baseUrl,testId);
    }


    public void updateTestRun(File authDataFile, String baseUrl, String testRunId, File jsonBody) throws IOException {
        testRuns.updateTestRun(authDataFile, baseUrl, testRunId, jsonBody);
    }

    public void getTestRunCustomField(File authDataFile, String baseUrl, String testRunId, String customFieldId) throws IOException {
        testRuns.getTestRunCustomField(authDataFile,baseUrl,testRunId,customFieldId);
    }

    public void updateTestRunCustomField(File authDataFile, String baseUrl, String testRunId, String customFieldId, File jsonBody) throws IOException {
        testRuns.updateTestRunCustomField(authDataFile, baseUrl, testRunId, customFieldId, jsonBody);
    }

    public void getTestRunIteration(File authDataFile, String baseUrl, String testRunId, String iterationId) throws IOException{
        testRuns.getTestRunIteration(authDataFile,baseUrl,testRunId,iterationId);
    }

    public void updateTestRunIteration(File authDataFile, String baseUrl, String testRunId, String iterationId, File jsonBody) throws IOException{
        testRuns.updateTestRunIteration(authDataFile,baseUrl,testRunId,iterationId,jsonBody);
    }

    public void getTestRunIterationStep(File authDataFile, String baseUrl, String testRunId, String iterationId) throws IOException{
        testRuns.getTestRunIterationStep(authDataFile, baseUrl, testRunId, iterationId);
    }

    public void getTestRunIterationStepResult(File authDataFile, String baseUrl, String testRunId, String iterationId, String stepResultId) throws IOException{
        testRuns.getTestRunIterationStepResult(authDataFile, baseUrl, testRunId, iterationId, stepResultId);
    }

    public void updateTestRunIterationTestStepResult(File authDataFile, String baseUrl, String testRunId, String iterationId, String stepResultId, File jsonBody) throws IOException{
        testRuns.updateTestRunIterationTestStepResult(authDataFile, baseUrl, testRunId, iterationId, stepResultId, jsonBody);
    }

    public void getTestRunIterationStepResultStatus(File authDataFile, String baseUrl, String testRunId, String iterationId, String stepResultId) throws IOException{
        testRuns.getTestRunIterationStepResultStatus(authDataFile, baseUrl, testRunId, iterationId, stepResultId);
    }

    public void updateTestRunIterationTestStepResultStatus(File authDataFile, String baseUrl, String testRunId, String iterationId, String stepResultId, String status, File jsonBody) throws IOException{
        testRuns.updateTestRunIterationTestStepResultStatus(authDataFile, baseUrl, testRunId, iterationId, stepResultId, status, jsonBody);
    }

    public void getTestRunIterationStepResultAttachment(File authDataFile, String baseUrl, String testRunId, String iterationId, String stepResultId) throws IOException{
        testRuns.getTestRunIterationStepResultAttachment(authDataFile, baseUrl, testRunId, iterationId, stepResultId);
    }

    public void addTestRunIterationStepResultAttachment(File authDataFile, String baseUrl, String testRunId, String iterationId, String stepResultId, File jsonBody) throws IOException{
        testRuns.addTestRunIterationStepResultAttachment(authDataFile, baseUrl, testRunId, iterationId, stepResultId, jsonBody);
    }

    public void testRunDeleteAllAttachments(File authDataFile, String baseUrl, String testRunId, String iterationId, String stepResultId) throws IOException{
        testRuns.testRunDeleteAllAttachments(authDataFile, baseUrl, testRunId, iterationId, stepResultId);
    }

    public void testRunDeleteSpecificAttachment(File authDataFile, String baseUrl, String testRunId, String iterationId, String stepResultId, String attachmentId) throws IOException{
        testRuns.testRunDeleteSpecificAttachment(authDataFile, baseUrl, testRunId, iterationId, stepResultId, attachmentId);
    }

    //---------------- Test Plans -----------------------------------------

    public void getTestsFromTestPlan(File authDataFile, String baseUrl, String testPlanKey) throws IOException{
        testPlans.getTestsFromTestPlan(authDataFile, baseUrl, testPlanKey);
    }

    public void addTestsToTestPlan(File authDataFile, String baseUrl, String testPlanKey, File testFile) throws IOException{
        testPlans.addTestsToTestPlan(authDataFile, baseUrl, testPlanKey, testFile);
    }

    public void removeTestsFromTestPlan(File authDataFile, String baseUrl, String testPlanKey, String testKey) throws IOException{
        testPlans.removeTestsFromTestPlan(authDataFile, baseUrl, testPlanKey, testKey);
    }

    public void getTestExecutionsFromTestPlan(File authDataFile, String baseUrl, String testPlanKey) throws IOException{
        testPlans.getTestExecutionsFromTestPlan(authDataFile, baseUrl, testPlanKey);
    }



    //------------------ Test Steps ---------------------------------------

    public void getStepsFromTest(File authDataFile, String baseUrl, String testSetKey) throws IOException{
        testSteps.getStepsFromTest(authDataFile, baseUrl, testSetKey);
    }

    public void createNewTestStep(File authDataFile, String baseUrl, String testSetKey, File jsonBody) throws IOException{
        testSteps.createNewTestStep(authDataFile, baseUrl, testSetKey, jsonBody);
    }

    public void getSingleStepFromTest(File authDataFile, String baseUrl, String testSetKey, String stepId) throws IOException{
        testSteps.getSingleStepFromTest(authDataFile, baseUrl, testSetKey, stepId);
    }

    public void updateExistingTestStep(File authDataFile, String baseUrl, String testSetKey, String stepId, File jsonBody) throws IOException{
        testSteps.updateExistingTestStep(authDataFile, baseUrl, testSetKey, stepId, jsonBody);
    }

    public void deleteTestStep(File authDataFile, String baseUrl, String testSetKey, String stepId) throws IOException{
        testSteps.deleteTestStep(authDataFile, baseUrl, testSetKey, stepId);
    }

    public void getAttachmentsFromTestStep(File authDataFile, String baseUrl, String testSetKey, String stepId) throws IOException{
        testSteps.getAttachmentsFromTestStep(authDataFile, baseUrl, testSetKey, stepId);
    }

    public void deleteAttachmentFromTestStep(File authDataFile, String baseUrl, String testSetKey, String stepId, String attachmentId) throws IOException{
        testSteps.deleteAttachmentFromTestStep(authDataFile, baseUrl, testSetKey, stepId, attachmentId);
    }

    //----------------- Test Run Status ------------------------------------

    public void resetTestRunStatus(File authDataFile, String baseUrl, String issueKey, File testFile) throws IOException{
        testRunStatus.resetTestRunStatus(authDataFile, baseUrl, issueKey, testFile);
    }

//------------------ Settings ----------------------------------------------

    public void getRequirementProjects(File authDataFile, String baseUrl) throws IOException{
        settings.getRequirementProjects(authDataFile, baseUrl);
    }

    public void activateProjectsForXray(File authDataFile, String baseUrl, File jsonBody) throws IOException{
        settings.activateProjectsForXray(authDataFile, baseUrl, jsonBody);
    }

    //------------ Requirement Status ----------------------------------------

    public void resetRequirementStatus(File authDataFile, String baseUrl, String issueKey, File jsonBody) throws IOException{
        requirementStatus.resetRequirementStatus(authDataFile, baseUrl, issueKey, jsonBody);
    }

    //---------------- Project -------------------------------------------

    public void getProjectTestRunCustomFields(File authDataFile, String baseUrl, String projectId) throws IOException{
        project.getProjectTestRunCustomFields(authDataFile, baseUrl, projectId);
    }

    public void getProjectTestStepsCustomFields(File authDataFile, String baseUrl, String projectId) throws IOException{
        project.getProjectTestStepsCustomFields(authDataFile, baseUrl, projectId);
    }

    //------------- Issue Types ----------------------------------------

    public void getXrayIssueTypes(File authDataFile, String baseUrl) throws IOException{
        issueTypes.getXrayIssueTypes(authDataFile, baseUrl);
    }

    public void installXrayIssueTypeSchemeForProjects(File authDataFile, String baseUrl, File jsonBody) throws IOException{
        issueTypes.installXrayIssueTypeSchemeForProjects(authDataFile, baseUrl, jsonBody);
    }



    //------------------ External Apps ------------------------------------

    public void getXrayLicense(File authDataFile, String baseUrl) throws IOException{
        externalApps.getXrayLicense(authDataFile, baseUrl);
    }



    //---------- Dataset -----------------------------------------------

    public void getDatasetCSV(File authDataFile, String baseUrl) throws IOException{
        dataset.getDatasetCSV(authDataFile, baseUrl);
    }

    public void importDataset(File authDataFile, String baseUrl, String testIssueId, File csvFile) throws IOException{
        dataset.importDataset(authDataFile, baseUrl, testIssueId, csvFile);
    }



    //------------------ Test Sets ------------------------------------

    public void getTestsFromTestSet(File authDataFile, String baseUrl, File testSetKey) throws IOException{
        testSets.getTestsFromTestSet(authDataFile, baseUrl, testSetKey);
    }

    public void addTestsToTestSet(File authDataFile, String baseUrl, String testSetKey, File testFile) throws IOException{
        testSets.addTestsToTestSet(authDataFile, baseUrl, testSetKey, testFile);
    }

    public void removeTestsFromTestSet(File authDataFile, String baseUrl, String testSetKey, String testKey) throws IOException{
        testSets.removeTestsFromTestSet(authDataFile, baseUrl, testSetKey, testKey);
    }


    //----------------------- Import Execution Results -------------------------

    public void importExecution(File authDataFile, String baseUrl, File requestBody) throws IOException{
        importCucumberExecution.importExecution(authDataFile, baseUrl, requestBody);
    }

    public void importExecutionMultipart(File authDataFile, String baseUrl, File infoJson, File result) throws IOException{
        importCucumberExecution.importExecutionMultipart(authDataFile, baseUrl, infoJson, result);
    }

    public static void main(String[] args) {

    }

}
