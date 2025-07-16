package com.ey.xray.serverDC.business.testRuns;

import com.ey.xray.serverDC.infrastructure.buildAPIRequests.MakeAPICallDC;
import okhttp3.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class TestRunsImpl implements TestRuns {

    public void getTestRunWithTestExecAndTestKey(File authDataFile, String baseUrl, String testExecIssueKey, String testIssueKey) throws IOException {

        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/testrun?testExecIssueKey="+testExecIssueKey+"&testIssueKey="+testIssueKey;

            makeAPICallDC.makeGetCall(url,authData.get("username").toString(),authData.get("password").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void getTestRunWithTestId(File authDataFile, String baseUrl, String testId) throws IOException {

        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/testrun/"+testId;

            makeAPICallDC.makeGetCall(url,authData.get("username").toString(),authData.get("password").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void updateTestRun(File authDataFile, String baseUrl, String testRunId, File jsonBody) throws IOException {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            JSONObject body = (JSONObject) jsonParser.parse(new FileReader(jsonBody));

            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/testrun/"+testRunId;

            makeAPICallDC.makePutCall(url,authData.get("username").toString(),authData.get("password").toString(), body.get("body").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void getTestRunCustomField(File authDataFile, String baseUrl, String testRunId, String customFieldId) throws IOException {

        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/testrun/"+testRunId+"/customfield/"+customFieldId;

            makeAPICallDC.makeGetCall(url,authData.get("username").toString(),authData.get("password").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void updateTestRunCustomField(File authDataFile, String baseUrl, String testRunId, String customFieldId, File jsonBody) throws IOException {

        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            JSONObject body = (JSONObject) jsonParser.parse(new FileReader(jsonBody));

            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/testrun/"+testRunId+"/customfield/"+customFieldId;

            makeAPICallDC.makePutCall(url,authData.get("username").toString(),authData.get("password").toString(), body.get("body").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void getTestRunIteration(File authDataFile, String baseUrl, String testRunId, String iterationId) throws IOException {

        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/testrun/"+testRunId+"/iteration/"+iterationId;

            makeAPICallDC.makeGetCall(url,authData.get("username").toString(),authData.get("password").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void updateTestRunIteration(File authDataFile, String baseUrl, String testRunId, String iterationId, File jsonBody) throws IOException {

        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            JSONObject body = (JSONObject) jsonParser.parse(new FileReader(jsonBody));

            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/testrun/"+testRunId+"/iteration/"+iterationId;

            makeAPICallDC.makePutCall(url,authData.get("username").toString(),authData.get("password").toString(), body.get("body").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void getTestRunIterationStep(File authDataFile, String baseUrl, String testRunId, String iterationId) throws IOException {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/testrun/"+testRunId+"/iteration/"+iterationId+"/step";

            makeAPICallDC.makeGetCall(url,authData.get("username").toString(),authData.get("password").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void getTestRunIterationStepResult(File authDataFile, String baseUrl, String testRunId, String iterationId, String stepResultId) throws IOException {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/testrun/"+testRunId+"/iteration/"+iterationId+"/step"+stepResultId;

            makeAPICallDC.makeGetCall(url,authData.get("username").toString(),authData.get("password").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void updateTestRunIterationTestStepResult(File authDataFile, String baseUrl, String testRunId, String iterationId, String stepResultId, File jsonBody) throws IOException {

        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            JSONObject body = (JSONObject) jsonParser.parse(new FileReader(jsonBody));

            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/testrun/"+testRunId+"/iteration/"+iterationId+"/step"+stepResultId;

            makeAPICallDC.makePutCall(url,authData.get("username").toString(),authData.get("password").toString(), body.get("body").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void getTestRunIterationStepResultStatus(File authDataFile, String baseUrl, String testRunId, String iterationId, String stepResultId) throws IOException {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/testrun/"+testRunId+"/iteration/"+iterationId+"/step"+stepResultId+"/status";

            makeAPICallDC.makeGetCall(url,authData.get("username").toString(),authData.get("password").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void updateTestRunIterationTestStepResultStatus(File authDataFile, String baseUrl, String testRunId, String iterationId, String stepResultId, String status, File jsonBody) throws IOException {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            JSONObject body = (JSONObject) jsonParser.parse(new FileReader(jsonBody));

            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/testrun/"+testRunId+"/iteration/"+iterationId+"/step"+stepResultId+"/status?status="+status;

            makeAPICallDC.makePutCall(url,authData.get("username").toString(),authData.get("password").toString(), body.get("body").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void getTestRunIterationStepResultAttachment(File authDataFile, String baseUrl, String testRunId, String iterationId, String stepResultId) throws IOException {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/testrun/"+testRunId+"/iteration/"+iterationId+"/step"+stepResultId+"/attachment";

            makeAPICallDC.makeGetCall(url,authData.get("username").toString(),authData.get("password").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void addTestRunIterationStepResultAttachment(File authDataFile, String baseUrl, String testRunId, String iterationId, String stepResultId, File jsonBody) throws IOException {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            JSONObject body = (JSONObject) jsonParser.parse(new FileReader(jsonBody));

            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/testrun/"+testRunId+"/iteration/"+iterationId+"/step"+stepResultId+"/attachment";

            makeAPICallDC.makePutCall(url,authData.get("username").toString(),authData.get("password").toString(), body.get("body").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void testRunDeleteAllAttachments(File authDataFile, String baseUrl, String testRunId, String iterationId, String stepResultId) throws IOException {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/testrun/"+testRunId+"/iteration/"+iterationId+"/step"+stepResultId+"/attachment?filename=";

            makeAPICallDC.makeDeleteCall(url,authData.get("username").toString(),authData.get("password").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void testRunDeleteSpecificAttachment(File authDataFile, String baseUrl, String testRunId, String iterationId, String stepResultId, String attachmentId) throws IOException {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/testrun/"+testRunId+"/iteration/"+iterationId+"/step"+stepResultId+"/attachment/"+attachmentId;

            makeAPICallDC.makeDeleteCall(url,authData.get("username").toString(),authData.get("password").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


}
