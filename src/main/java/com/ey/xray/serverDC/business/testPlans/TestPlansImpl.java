package com.ey.xray.serverDC.business.testPlans;

import com.ey.xray.serverDC.infrastructure.buildAPIRequests.MakeAPICallDC;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class TestPlansImpl implements TestPlans {

    public void getTestsFromTestPlan(File authDataFile, String baseUrl, String testPlanKey) throws IOException {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/testplan/"+testPlanKey+"/test";

            makeAPICallDC.makeGetCall(url,authData.get("username").toString(),authData.get("password").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public void addTestsToTestPlan(File authDataFile, String baseUrl, String testPlanKey, File testFile) throws IOException {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            JSONObject tests = (JSONObject) jsonParser.parse(new FileReader(testFile));

            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/testplan/"+testPlanKey+"/test";

            makeAPICallDC.makePostCall(url,authData.get("username").toString(),authData.get("password").toString(), tests.get("body").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void removeTestsFromTestPlan(File authDataFile, String baseUrl, String testPlanKey, String testKey) throws IOException {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));

            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl + "/testplan/" + testPlanKey + "/test/" + testKey;

            makeAPICallDC.makeDeleteCall(url, authData.get("username").toString(), authData.get("password").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    public void getTestExecutionsFromTestPlan(File authDataFile, String baseUrl, String testPlanKey) throws IOException {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/testplan/"+testPlanKey+"/testexecution";

            makeAPICallDC.makeGetCall(url,authData.get("username").toString(),authData.get("password").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

//    public void addTestExecutionsToTestPlan(File authDataFile, String baseUrl, String testPlanKey, File testFile) throws IOException, NoSuchAlgorithmException, KeyManagementException {
//        //Creating a JSONParser object
//        JSONParser jsonParser = new JSONParser();
//        try {
//            //Parsing the contents of the JSON file
//            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
//            JSONObject multipartData = (JSONObject) jsonParser.parse(new FileReader(multipartDataFile));
//            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();
//
////            //Generate auth token
////            URL authUrl = new URL(authData.get("Url").toString());
////            HttpsURLConnection connection = makeAPICall.getAuthToken(true, authUrl, authData.get("Body"));
////            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
////            String inputLine;
////            StringBuffer response = new StringBuffer();
////            if (connection.getResponseCode() == connection.HTTP_OK) { // success
////                while ((inputLine = in .readLine()) != null) {
////                    response.append(inputLine);
////                } in .close();
////            } else {
////                System.out.println("POST request did not work");
////            }
//
//            //Make API call to import execution results to JIRA
//            String url = multipartData.get("BaseUrl").toString() + multipartData.get("ImportExecutionPath").toString();
//            String jiraUrl = multipartData.get("JiraBaseUrl").toString() + multipartData.get("JiraPath").toString();
//            String xAtlassianToken = authData.get("xAtlassianToken").toString();
//
//            URL multipartUrl = new URL(url);
//            URL jiraUrlEndpoint = new URL(jiraUrl);
//            makeAPICallDC.getTestsFromTestSet(testSetKey);
//            System.out.println("Test execution results successfully uploaded to JIRA");
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void removeTestExecutionsFromTestPlan(File authDataFile, String testSetKey, File multipartDataFile, File reportDir, File infoJson, List<File> filesList) throws IOException, NoSuchAlgorithmException, KeyManagementException {
//        //Creating a JSONParser object
//        JSONParser jsonParser = new JSONParser();
//        try {
//            //Parsing the contents of the JSON file
//            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
//            JSONObject multipartData = (JSONObject) jsonParser.parse(new FileReader(multipartDataFile));
//            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();
//
////            //Generate auth token
////            URL authUrl = new URL(authData.get("Url").toString());
////            HttpsURLConnection connection = makeAPICall.getAuthToken(true, authUrl, authData.get("Body"));
////            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
////            String inputLine;
////            StringBuffer response = new StringBuffer();
////            if (connection.getResponseCode() == connection.HTTP_OK) { // success
////                while ((inputLine = in .readLine()) != null) {
////                    response.append(inputLine);
////                } in .close();
////            } else {
////                System.out.println("POST request did not work");
////            }
//
//            //Make API call to import execution results to JIRA
//            String url = multipartData.get("BaseUrl").toString() + multipartData.get("ImportExecutionPath").toString();
//            String jiraUrl = multipartData.get("JiraBaseUrl").toString() + multipartData.get("JiraPath").toString();
//            String xAtlassianToken = authData.get("xAtlassianToken").toString();
//
//            URL multipartUrl = new URL(url);
//            URL jiraUrlEndpoint = new URL(jiraUrl);
//            makeAPICallDC.getTestsFromTestSet(testSetKey);
//            System.out.println("Test execution results successfully uploaded to JIRA");
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//    }

}
