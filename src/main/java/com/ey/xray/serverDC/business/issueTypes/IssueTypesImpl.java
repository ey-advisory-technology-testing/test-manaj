package com.ey.xray.serverDC.business.issueTypes;

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

public class IssueTypesImpl implements IssueTypes {

    public void getXrayIssueTypes(File authDataFile, String baseUrl) throws IOException {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/xrayIssueTypes";

            makeAPICallDC.makeGetCall(url,authData.get("username").toString(),authData.get("password").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void installXrayIssueTypeSchemeForProjects(File authDataFile, String baseUrl, File jsonBody) throws IOException {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            JSONObject body = (JSONObject) jsonParser.parse(new FileReader(jsonBody));

            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl + "/xrayIssueTypes/issueTypeScreenSchemes";

            makeAPICallDC.makePostCall(url, authData.get("username").toString(), authData.get("password").toString(), body.get("body").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
