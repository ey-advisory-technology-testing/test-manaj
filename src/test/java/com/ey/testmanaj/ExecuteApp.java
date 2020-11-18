package com.ey.testmanaj;

import java.util.LinkedHashMap;

import org.apache.commons.lang.SystemUtils;

public class ExecuteApp {

	public static void main(String[] args) throws Exception {

	    String testCaseName = "TC_19  Create a New Submission for a Full Application";

        LinkedHashMap<String, String> runProperties = new LinkedHashMap<>();
        runProperties.put("attachment", "C:\\Users\\Manjunath.Purad\\OneDrive - EY\\Desktop\\EY I and A\\Image.JPG;C:\\Users\\Manjunath.Purad\\OneDrive - EY\\Desktop\\EY I and A\\Image - Copy.JPG");
        runProperties.put("comments", "This is a comment");

        LinkedHashMap<String, LinkedHashMap<String, String>> steps = new LinkedHashMap<>();

        LinkedHashMap<String, String> stepProperties = new LinkedHashMap<>();
        stepProperties.put("status", "Passed");
        stepProperties.put("description", "Given I navigate to www.google.com");
        stepProperties.put("execution-time","14:38:14");
        steps.put("Step 1", stepProperties);

        stepProperties = new LinkedHashMap<>();
        stepProperties.put("status", "Passed");
        stepProperties.put("description", "Given I enter text search into search.bar");
        stepProperties.put("expected", "Google Home Page Should Load");
        steps.put("Step 2", stepProperties);

        stepProperties = new LinkedHashMap<>();
        stepProperties.put("status", "Failed");
        stepProperties.put("description", "Then google.results are displayed");
        stepProperties.put("actual", "Exception in thread \"main\" java.util.NoSuchElementException\n" +
                "at java.util.Scanner.throwFor(Scanner.java:838)\n" +
                "at java.util.Scanner.next(Scanner.java:1461)\n" +
                "at java.util.Scanner.nextInt(Scanner.java:2091)\n" +
                "at java.util.Scanner.nextInt(Scanner.java:2050)\n" +
                "at Addition.main(Addition.java:16)");
        stepProperties.put("attachment", "C:\\Users\\Manjunath.Purad\\OneDrive - EY\\Desktop\\EY I and A\\Image.JPG");
        steps.put("Step 3", stepProperties);

        stepProperties = new LinkedHashMap<>();
        stepProperties.put("status", "No Run");
        stepProperties.put("description", "Then google.logo is displayed");
        stepProperties.put("execution-date","2009-01-27");
        steps.put("Step 4", stepProperties);

        stepProperties = new LinkedHashMap<>();
        stepProperties.put("status", "No Run");
        stepProperties.put("description", "Then I click google.home");
        steps.put("Step 5", stepProperties);

        String resoucesPath = SystemUtils.getUserDir().toString();

        updateTestCase(testCaseName, steps, runProperties, resoucesPath);
	}

	public static void updateTestCase(String testCaseName, LinkedHashMap<String, LinkedHashMap<String, String>> steps, LinkedHashMap<String, String> runProperties, String resourcesPath) throws Exception {
		App executeApp = new App(resourcesPath);
		executeApp.updateTestResult(testCaseName, steps, runProperties);
	}

}
