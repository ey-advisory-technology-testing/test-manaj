package com.ey.xray.cloud.business.importCucumberExecutionResults;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface ImportExecution {

    /**
     * Import results.
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
    public void importResults(File authDataFile, File multipartDataFile, File reportDir, File infoJson, List<File> filesList) throws IOException, NoSuchAlgorithmException, KeyManagementException;


}
