package com.ey.xray.serverDC.business.testRunStatus;

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

public class TestRunStatusImpl implements TestRunStatus {

    public void resetTestRunStatus(File authDataFile, String baseUrl, String issueKey, File testFile) throws IOException {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            JSONObject tests = (JSONObject) jsonParser.parse(new FileReader(testFile));

            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/testrunstatus/reset/keys?keys="+issueKey;

            makeAPICallDC.makePostCall(url,authData.get("username").toString(),authData.get("password").toString(), tests.get("body").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


}
