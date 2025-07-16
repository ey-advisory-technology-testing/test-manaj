package com.ey.xray.serverDC.business.testSteps;

import java.io.*;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;

import com.ey.xray.cloud.infrastructure.buildAPIRequests.MakeAPICall;
import com.ey.xray.serverDC.infrastructure.buildAPIRequests.MakeAPICallDC;
import okhttp3.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TestStepsImpl implements TestSteps {

    public void getStepsFromTest(File authDataFile, String baseUrl, String testSetKey) throws IOException {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/test/"+testSetKey+"/steps";

            makeAPICallDC.makeGetCall(url,authData.get("username").toString(),authData.get("password").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void createNewTestStep(File authDataFile, String baseUrl, String testSetKey, File jsonBody) throws IOException {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            JSONObject body = (JSONObject) jsonParser.parse(new FileReader(jsonBody));

            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/test/"+testSetKey+"/steps";

            makeAPICallDC.makePostCall(url,authData.get("username").toString(),authData.get("password").toString(), body.get("body").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void getSingleStepFromTest(File authDataFile, String baseUrl, String testSetKey, String stepId) throws IOException {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/test/"+testSetKey+"/steps/"+stepId;

            makeAPICallDC.makeGetCall(url,authData.get("username").toString(),authData.get("password").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void updateExistingTestStep(File authDataFile, String baseUrl, String testSetKey, String stepId, File jsonBody) throws IOException {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            JSONObject body = (JSONObject) jsonParser.parse(new FileReader(jsonBody));

            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/test/"+testSetKey+"/steps/"+stepId;

            makeAPICallDC.makePutCall(url,authData.get("username").toString(),authData.get("password").toString(), body.get("body").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void deleteTestStep(File authDataFile, String baseUrl, String testSetKey, String stepId) throws IOException {

        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/test/"+testSetKey+"/steps/"+stepId;

            makeAPICallDC.makeDeleteCall(url,authData.get("username").toString(),authData.get("password").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void getAttachmentsFromTestStep(File authDataFile, String baseUrl, String testSetKey, String stepId) throws IOException {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/test/"+testSetKey+"/steps/"+stepId+"/attachments";

            makeAPICallDC.makeGetCall(url,authData.get("username").toString(),authData.get("password").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void deleteAttachmentFromTestStep(File authDataFile, String baseUrl, String testSetKey, String stepId, String attachmentId) throws IOException {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/test/"+testSetKey+"/steps/"+stepId+"/attachments/"+attachmentId;

            makeAPICallDC.makeDeleteCall(url,authData.get("username").toString(),authData.get("password").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
