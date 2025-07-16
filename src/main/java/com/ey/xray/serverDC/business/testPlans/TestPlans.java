package com.ey.xray.serverDC.business.testPlans;

import java.io.File;
import java.io.IOException;

public interface TestPlans {

    public void getTestsFromTestPlan(File authDataFile, String baseUrl, String testPlanKey) throws IOException;

    public void addTestsToTestPlan(File authDataFile, String baseUrl, String testPlanKey, File testFile) throws IOException;

    public void removeTestsFromTestPlan(File authDataFile, String baseUrl, String testPlanKey, String testKey) throws IOException;

    public void getTestExecutionsFromTestPlan(File authDataFile, String baseUrl, String testPlanKey) throws IOException;


}
