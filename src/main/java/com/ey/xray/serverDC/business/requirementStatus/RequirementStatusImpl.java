package com.ey.xray.serverDC.business.requirementStatus;

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

public class RequirementStatusImpl implements RequirementStatus {

    public void resetRequirementStatus(File authDataFile, String baseUrl, String issueKey, File jsonBody) throws IOException {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            JSONObject body = (JSONObject) jsonParser.parse(new FileReader(jsonBody));

            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl + "/requirementstatus/reset?keys="+issueKey;

            makeAPICallDC.makePutCall(url, authData.get("username").toString(), authData.get("password").toString(), body.get("body").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
