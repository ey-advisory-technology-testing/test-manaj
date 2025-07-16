package com.ey.xray.serverDC.business.dataset;

import com.ey.xray.serverDC.infrastructure.buildAPIRequests.MakeAPICallDC;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DatasetImpl implements Dataset {

    public void getDatasetCSV(File authDataFile, String baseUrl) throws IOException {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/dataset/export";

            makeAPICallDC.makeGetCall(url,authData.get("username").toString(),authData.get("password").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void importDataset(File authDataFile, String baseUrl, String testIssueId, File csvFile) throws IOException {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/dataset/import?testIssueId="+testIssueId;

            makeAPICallDC.makePostCallWithFile(url,authData.get("username").toString(),authData.get("password").toString(), csvFile);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
