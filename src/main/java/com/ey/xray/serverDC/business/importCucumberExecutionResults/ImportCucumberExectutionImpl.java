package com.ey.xray.serverDC.business.importCucumberExecutionResults;

import java.io.*;
import com.ey.xray.serverDC.infrastructure.buildAPIRequests.MakeAPICallDC;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ImportCucumberExectutionImpl implements ImportCucumberExecution {

    public void importExecution(File authDataFile, String baseUrl, File requestBody) throws IOException {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            JSONObject reqBody = (JSONObject) jsonParser.parse(new FileReader(requestBody));

            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/import/execution";

            makeAPICallDC.makePostCall(url,authData.get("username").toString(),authData.get("password").toString(), reqBody.get("body").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void importExecutionMultipart(File authDataFile, String baseUrl, File infoJson, File result) throws IOException {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));

            MakeAPICallDC makeAPICallDC = new MakeAPICallDC();

            String url = baseUrl+ "/import/execution/multipart";

            makeAPICallDC.importExecutionMultipart(url,authData.get("username").toString(),authData.get("password").toString(),infoJson,result);

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}
