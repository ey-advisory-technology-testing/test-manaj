package com.ey.xray.cloud.business.importCucumberExecutionResults;

import java.io.*;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;

import com.ey.xray.cloud.infrastructure.buildAPIRequests.MakeAPICall;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * The xray.infrastructure package is internal to the project and should not be exposed outside. Any configurations, parameters and indirect method calls
 * should flow from the xray.application layer via the Initializer. In short, consuming xray.application should not know how or what is being done to initialize this project
 */
public class ImportExecutionImpl implements ImportExecution {

    public void importResults(File authDataFile, File multipartDataFile, File reportDir, File infoJson, List<File> filesList) throws IOException, NoSuchAlgorithmException, KeyManagementException {
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

            //Make API call to import execution results to JIRA
            String url = multipartData.get("BaseUrl").toString() + multipartData.get("ImportExecutionPath").toString();
            String jiraUrl = multipartData.get("JiraBaseUrl").toString() + multipartData.get("JiraPath").toString();
            String xAtlassianToken = authData.get("xAtlassianToken").toString();

            URL multipartUrl = new URL(url);
            URL jiraUrlEndpoint = new URL(jiraUrl);
            makeAPICall.importRes(multipartUrl, response.toString(), reportDir, infoJson, jiraUrlEndpoint, filesList, xAtlassianToken);
            System.out.println("Test execution results successfully uploaded to JIRA");

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


//    public void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyManagementException {
//        File authDataFile = new File(args[0]);
//        File multipartDataFile = new File(args[1]);
//        File reportDir = new File(args[2]);
//        File infoJson = new File(args[3]);
//
//        importResults(authDataFile, multipartDataFile, reportDir, infoJson);
//    }
}

