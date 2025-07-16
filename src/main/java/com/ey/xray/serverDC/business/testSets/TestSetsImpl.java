package com.ey.xray.serverDC.business.testSets;

import com.ey.xray.serverDC.infrastructure.buildAPIRequests.MakeAPICallDC;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;

public class TestSetsImpl implements TestSets {

    public void getTestsFromTestSet(File authDataFile, String baseUrl, File testSetKey) throws IOException {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/testset/"+testSetKey+"/test";

            makeAPICallDC.makeGetCall(url,authData.get("username").toString(),authData.get("password").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    public void addTestsToTestSet(File authDataFile, String baseUrl, String testSetKey, File testFile) throws IOException {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            JSONObject tests = (JSONObject) jsonParser.parse(new FileReader(testFile));

            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/testset/"+testSetKey+"/test";

            makeAPICallDC.makePostCall(url,authData.get("username").toString(),authData.get("password").toString(), tests.get("body").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    public void removeTestsFromTestSet(File authDataFile, String baseUrl, String testSetKey, String testKey) throws IOException {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));

            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl + "/testset/" + testSetKey + "/test/" + testKey;

            makeAPICallDC.makeDeleteCall(url, authData.get("username").toString(), authData.get("password").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
