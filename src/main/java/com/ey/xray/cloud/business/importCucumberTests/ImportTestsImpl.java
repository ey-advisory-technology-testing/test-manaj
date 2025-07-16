package com.ey.xray.cloud.business.importCucumberTests;

import java.io.*;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.HttpsURLConnection;

import com.ey.xray.cloud.infrastructure.buildAPIRequests.MakeAPICall;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



/**
 * The xray.infrastructure package is internal to the project and should not be exposed outside. Any configurations, parameters and indirect method calls
 * should flow from the xray.application layer via the Initializer. In short, consuming xray.application should not know how or what is being done to initialize this project
 */
public class ImportTestsImpl implements ImportTests {

    public void importTestScenarios(File authDataFile, File multipartDataFile, File featureFile) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject authData = (JSONObject) jsonParser.parse(new FileReader(authDataFile));
            JSONObject multipartData = (JSONObject) jsonParser.parse(new FileReader(multipartDataFile));
            MakeAPICall makeAPICall = new MakeAPICall();

            //Generate auth token
            URL authUrl = new URL(authData.get("Url").toString());
            HttpsURLConnection connection = makeAPICall.getAuthToken(true, authUrl, authData.get("Body"));
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            if (connection.getResponseCode() == connection.HTTP_OK) { // success
                while ((inputLine = in .readLine()) != null) {
                    response.append(inputLine);
                } in .close();
            } else {
                System.out.println("POST request did not work");
            }

            String url = multipartData.get("BaseUrl").toString() + multipartData.get("ImportTestsPath").toString() + multipartData.get("ProjectKey").toString();
            URL multipartUrl = new URL(url);

            makeAPICall.importCucumberTestsToJIRA(featureFile, multipartUrl, response.toString());
            System.out.println("Test scenarios successfully uploaded to JIRA");

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


//    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyManagementException {
//        if(args.length > 0) {
//            File file = new File(args[0]);
//            importTestScenarios(file);
//
//            // Work with your 'file' object here
//        }
//    }
}

