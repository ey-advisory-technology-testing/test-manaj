package com.ey.xray.serverDC.business.testSets;

import java.io.File;
import java.io.IOException;

public interface TestSets {

    public void getTestsFromTestSet(File authDataFile, String baseUrl, File testSetKey) throws IOException;

    public void addTestsToTestSet(File authDataFile, String baseUrl, String testSetKey, File testFile) throws IOException;

    public void removeTestsFromTestSet(File authDataFile, String baseUrl, String testSetKey, String testKey) throws IOException;

    }
