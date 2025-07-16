package com.ey.xray.cloud.application;
import com.ey.xray.cloud.business.importCucumberExecutionResults.ImportExecutionImpl;
import com.ey.xray.cloud.business.importCucumberExecutionResults.ImportExecution;
import com.ey.xray.cloud.business.importCucumberTests.ImportTestsImpl;
import com.ey.xray.cloud.business.importCucumberTests.ImportTests;
import com.ey.xray.cloud.business.uploadAttachment.UploadAttachment;
import com.ey.xray.cloud.business.uploadAttachment.UploadAttachmentImpl;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


/**
 * This is the class that initializes all the xray.business interfaces/classes to the consuming project. This will be called from consuming project which
 * can then start calling the obfuscated methods of the xray.business layer using their corresponding interfaces
 */
public class XrayInitializer {

    /**
     * Import test execution results.
     *
     * @param authDataFile      the auth data file
     * @param multipartDataFile the multipart data file
     * @param reportDir         the report dir
     * @param infoJson          the info json
     * @param filesList         the files list
     * @throws IOException              the io exception
     * @throws NoSuchAlgorithmException the no such algorithm exception
     * @throws KeyManagementException   the key management exception
     */
    public void importTestExecutionResults(File authDataFile, File multipartDataFile, File reportDir, File infoJson, List<File> filesList) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        ImportExecution obj = new ImportExecutionImpl();
        obj.importResults(authDataFile, multipartDataFile, reportDir, infoJson, filesList);

    }

    /**
     * Import cucumber tests to jira.
     *
     * @param authDataFile      the auth data file
     * @param multipartDataFile the multipart data file
     * @param featureFile       the feature file
     * @throws IOException              the io exception
     * @throws NoSuchAlgorithmException the no such algorithm exception
     * @throws KeyManagementException   the key management exception
     */
    public void importCucumberTestsToJIRA(File authDataFile, File multipartDataFile, File featureFile) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        ImportTests obj = new ImportTestsImpl();
        obj.importTestScenarios(authDataFile, multipartDataFile, featureFile);

    }


    /**
     * Upload attachments.
     *
     * @param jiraUrl         the jira url
     * @param filesList       the files list
     * @param jiraKey         the jira key
     * @param xAtlassianToken the x atlassian token
     * @throws IOException the io exception
     */
    public void uploadAttachments(URL jiraUrl, List<File> filesList, String jiraKey, String xAtlassianToken) throws IOException {
        UploadAttachment obj = new UploadAttachmentImpl();
        obj.uploadAttachmentToJIRAIssue(jiraUrl, filesList, jiraKey, xAtlassianToken);

    }

        public static void main(String[] args) {

    }
}
