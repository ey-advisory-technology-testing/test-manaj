package com.ey.testmanaj;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.http.HttpStatus;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ey.testmanaj.apirequest.BuildRequests;
import com.ey.testmanaj.apirequest.Headers;
import com.ey.testmanaj.connection.Authenticator;
import com.ey.testmanaj.urls.BuildURL;
import com.ey.beans.TestInstanceBean;
import com.ey.beans.TestRunBean;
import com.ey.beans.TestRunStepBean;
import com.ey.beans.TestSetBean;
import com.ey.utilities.PropertyReader;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.util.Base64;

import com.google.common.io.CharStreams;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * The type App.
 */
public class App {

    private static BuildRequests buildRequest;
    private static Headers header;
    private static PropertyReader objConfig;
    private static PropertyReader objTestSet;
    private static PropertyReader objTestInstance;
    private static PropertyReader objTestRun;
    private static PropertyReader objTestRunStep;

    /**
     * Gets alm resources file path.
     *
     * @return the alm resources file path
     */
    public static String getResourcesFilePath() {
        return resourcesFilePath;
    }

    /**
     * Sets resources file path.
     *
     * @param resourcesFilePath the resources file path
     */
    public static void setResourcesFilePath(String resourcesFilePath) {
        App.resourcesFilePath = resourcesFilePath;
    }

    /**
     * The constant resourcesFilePath.
     */
    public static String resourcesFilePath;

    /**
     * Sets path for all the alm sources.
     *
     * @param path the alm resurces path
     */
    public App(String path){
        setResourcesFilePath(path);
        buildRequest = new BuildRequests();
        header = new Headers();
        objConfig = new PropertyReader(getResourcesFilePath() + "\\resources\\configuration.properties");
        objTestSet = new PropertyReader(getResourcesFilePath() + "\\resources\\test-set.properties");
        objTestInstance = new PropertyReader(getResourcesFilePath() + "\\resources\\test-instance.properties");
        objTestRun = new PropertyReader(getResourcesFilePath() + "\\resources\\test-run.properties");
        objTestRunStep = new PropertyReader(getResourcesFilePath() + "\\resources\\test-run-step.properties");
    }

    /**
     * Creates and updates test run with test steps and attachments.
     *
     * @param testCaseName  the test case name
     * @param stepDeatils   the step details
     * @param runProperties the test run properties
     * @throws Exception the exception
     */
    public void updateTestResult(String testCaseName, LinkedHashMap<String, LinkedHashMap<String, String>> stepDeatils, LinkedHashMap<String, String> runProperties) throws Exception {

        String statusString = "status";
        String attachmentString = "attachment";
        try {
            createTestSet();

            createTestInstance(testCaseName);

            String[] arrStepID = createTestRun();
            deleteAllTestSteps(arrStepID);
            String finalStatus = "No Run";
            for ( String key : stepDeatils.keySet() ) {
                createTestStepAndStatus(stepDeatils.get(key), key);
                String status = stepDeatils.get(key).get(statusString);
                if(status.equalsIgnoreCase("passed") || status.equalsIgnoreCase("failed")){
                    finalStatus = status;
                }
            }
            if(!runProperties.containsKey(statusString)){
                runProperties.put(statusString, finalStatus);
            }

            updateTestRun(runProperties);

            if(runProperties.containsKey(attachmentString)){
                String[] attachmentPaths = runProperties.get(attachmentString).split(";");
                for(int i = 0; i < attachmentPaths.length; i++) {
                    Path p = Paths.get(attachmentPaths[i]);
                    String file = p.getFileName().toString();
                    uploadAttachment(attachmentPaths[i].replace("\\" + file, ""), file);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets header for http request.
     *
     * @param cookie the cookies which needs to be set for the http request
     * @return the http headers
     * @throws IOException the io exception
     */
    @SuppressWarnings({ "unchecked"})
    public String getHeader(String cookie) throws IOException {
        String sessionXML = BuildURL.buildSessionXML();
        GenericUrl sessionn = BuildURL.getGenericURL("getSession");
        ArrayList<String> headers = new ArrayList<String>();
        Object a = getSession(sessionn, sessionXML, cookie);
        headers.addAll((ArrayList<String>) a);
        String c = "";
        for (String h : headers) {
            if (!h.contains("JSESSIONID")) {
                c = c + h + ";";
            }
        }

        return c;
    }

    /**
     * Sets credentials and encodes them.
     *
     * @return the encoded user credentials
     * @throws ConfigurationException the Configuration exception
     */
    public String setLoginCredentials() throws ConfigurationException  {
        String auth;
        try {
            String username = objConfig.readValue("username");
            String password = objConfig.readValue("password");

            objTestRun.writeValue("owner", username);

            String authorization = username + ":" + password;

            auth = new String(Base64.encodeBase64(authorization.getBytes()));
        }catch(IOException e) {
            throw new RuntimeException("Run time exception at setLoginCredentials method" + e.getLocalizedMessage());
        }
        return auth;

    }

    /**
     * Create test set.
     *
     * @throws IOException                  the io exception
     * @throws ConfigurationException       the configuration exception
     */
    public void createTestSet() throws IOException, ConfigurationException{
        XmlMapper xmlMapper = new XmlMapper();

        findTestSetFolderID();

        String headers = isAuthenticated();

        GenericUrl testSetURL = BuildURL.getGenericURL("createTestSet");

        TestSetBean testSetXml = new TestSetBean().createTestSetXML();

        String testSetXmlString = xmlMapper.writeValueAsString(testSetXml);

        try {
            HttpRequest req = buildRequest.createPostRequest(testSetURL, testSetXmlString);

            req.getHeaders().setContentType("application/xml");

            header.setHeader(headers, req);

            HttpResponse res = req.execute();

            InputStream iStream = res.getContent();
            String temp = CharStreams.toString(new InputStreamReader(iStream));

            objTestInstance.writeValue("cycle-id", getAttributeFromXMLResponse(temp, "id"));
            objTestRun.writeValue("cycle-id", getAttributeFromXMLResponse(temp, "id"));
        } catch (Exception e) {
            e.getLocalizedMessage();

            getTestSet(headers);
        }

    }

    /**
     * Gets test set.
     *
     * @param headers the request headers
     * @throws IOException the io exception
     */
    public void getTestSet(String headers) throws IOException {

        GenericUrl testSetURL = BuildURL.getGenericURL("createTestSet");

        String strTestSetName = objTestSet.readValue("name");
        String strParentID = objTestSet.readValue("parent-id");

        String strTemp = testSetURL.toString();

        testSetURL = new GenericUrl(strTemp + "/?query={name['" + strTestSetName + "'];parent-id[" + strParentID + "]}");

        try {
            HttpRequest req = buildRequest.createGetRequest(testSetURL);

            req.getHeaders().setContentType("application/xml");

            header.setHeader(headers, req);

            HttpResponse res = req.execute();

            InputStream iStream = res.getContent();
            String temp = CharStreams.toString(new InputStreamReader(iStream));

            objTestInstance.writeValue("cycle-id", getAttributeFromXMLResponse(temp, "id"));
            objTestRun.writeValue("cycle-id", getAttributeFromXMLResponse(temp, "id"));
        } catch (Exception e) {
            e.getLocalizedMessage();
        }

    }

    /**
     * Create test instance for the given test.
     *
     * @param testCaseName the test case name
     * @throws IOException                  the io exception
     * @throws ConfigurationException       the configuration exception
     * @throws ParserConfigurationException the parser configuration exception
     * @throws SAXException                 the sax exception
     */
    public void createTestInstance(String testCaseName) throws IOException, ConfigurationException, ParserConfigurationException, SAXException {

        String headers = isAuthenticated();

        boolean numeric;
        numeric = testCaseName.matches("-?\\d+(\\.\\d+)?");
        if(numeric) {
            getTestsById(testCaseName.trim());
        }
        else {
            getTestsByName(testCaseName.trim());
        }

        XmlMapper xmlMapper = new XmlMapper();
		/*
		Code to check for created test instance
		 */

        GenericUrl testInstanceURL = BuildURL.getGenericURL("testInstance");
        TestInstanceBean testInstanceXml = new TestInstanceBean().createTestInstanceXML();
        String testInstanceXmlString = xmlMapper.writeValueAsString(testInstanceXml);

        boolean existingTestInstance = false;
        if(objConfig.readValue("always_create_new_tset_instance").equals("false")) {
            String cycleID = objTestInstance.readValue("cycle-id");
            String tcID = objTestInstance.readValue("test-id");
            GenericUrl testSetURL = new GenericUrl(testInstanceURL.toString() + "/?query={cycle-id[" + cycleID + "];test-id[" + tcID + "]}");

            HttpRequest req0 = buildRequest.createGetRequest(testSetURL);
            req0.getHeaders().setContentType("application/xml");

            header.setHeader(headers, req0);

            HttpResponse res0 = req0.execute();

            InputStream iStream0 = res0.getContent();
            String temp0 = CharStreams.toString(new InputStreamReader(iStream0));
            existingTestInstance = !temp0.contains("TotalResults=\"0\"");

            if(existingTestInstance){
                objTestRun.writeValue("testcycl-id", getAttributeFromXMLResponse(temp0, "id"));
            }
        }
        if(!existingTestInstance) {

            try {
                HttpRequest req = buildRequest.createPostRequest(testInstanceURL, testInstanceXmlString);
                req.getHeaders().setContentType("application/xml");

                header.setHeader(headers, req);

                HttpResponse res = req.execute();

                InputStream iStream = res.getContent();
                String temp = CharStreams.toString(new InputStreamReader(iStream));

                objTestRun.writeValue("testcycl-id", getAttributeFromXMLResponse(temp, "id"));
            } catch (Exception e) {
                e.getLocalizedMessage();
                throw e;
            }
        }
    }

    /**
     * Gets session details.
     *
     * @param session the session end point url
     * @param sessionXml the get session http request body
     * @param cookie the request cookie
     * @return the http request headers
     * @throws IOException the io exception
     */
    public Object getSession(GenericUrl session, String sessionXml, String cookie) throws IOException {

        HttpRequest req = buildRequest.createPostRequest(session, sessionXml);

        req.getHeaders().setCookie(cookie);
        HttpResponse res = req.execute();

        HttpHeaders headers = res.getHeaders();
        Object head;
        head = headers.get("set-cookie");
        return head;
    }

    /**
     * Authenticate alm and return cookie.
     *
     * @return the http request header/cookie
     * @throws ConfigurationException the Configuration Exception
     */
    public String authenticate() throws ConfigurationException {

        String cookie = "";
        try {
            GenericUrl gURL = BuildURL.getGenericURL("authentication");

            HttpRequest req = buildRequest.createPostRequest(gURL, "");

            String auth = setLoginCredentials();
            req.setHeaders(new HttpHeaders().set("Authorization", "Basic " + auth));

            HttpResponse res = req.execute();
            HttpHeaders headers = res.getHeaders();

            cookie = header.getCookies(headers);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return cookie;
    }

    /**
     * Check whether authentication is successful and return cookie.
     *
     * @return the http request header/cookie
     * @throws IOException the io exception
     * @throws ConfigurationException the Configuration Exception
     */
    public String isAuthenticated() throws IOException, ConfigurationException {

        String cookie = "";

        BuildURL.setEnv();

        cookie = authenticate();

        String c;
        c = getHeader(cookie);

        return c + ";" + cookie;
    }

    /**
     * Create a new test run.
     *
     * @return the test steps array
     * @throws IOException                  the io exception
     * @throws ParserConfigurationException the parser configuration exception
     * @throws SAXException                 the sax exception
     * @throws ConfigurationException       the configuration exception
     */
    public String[] createTestRun() throws IOException, ParserConfigurationException, SAXException, ConfigurationException {

        String headers = isAuthenticated();

        XmlMapper xmlMapper = new XmlMapper();
        GenericUrl testRunURL = BuildURL.getGenericURL("createTestRun");
        TestRunBean testRunXml = new TestRunBean().createTestRunXML();
        String testRunXmlString = xmlMapper.writeValueAsString(testRunXml);

        String[] arrTemp = null;

        try {
            HttpRequest req = buildRequest.createPostRequest(testRunURL, testRunXmlString);

            req.getHeaders().setContentType("application/xml");

            header.setHeader(headers, req);

            HttpResponse res = req.execute();

            InputStream iStream = res.getContent();
            String temp = CharStreams.toString(new InputStreamReader(iStream));

            objTestRunStep.writeValue("test-run-id", getAttributeFromXMLResponse(temp, "id"));

            arrTemp = getTestSteps().split(";");
        } catch (HttpResponseException e) {

            e.getLocalizedMessage();
        }
        return arrTemp;
    }

    /**
     * Gets test steps.
     *
     * @return the test steps
     * @throws IOException                  the io exception
     * @throws ConfigurationException       the configuration exception
     * @throws SAXException                 the sax exception
     * @throws ParserConfigurationException the parser configuration exception
     */
    public String getTestSteps() throws IOException, ConfigurationException, SAXException, ParserConfigurationException {

        String headers = isAuthenticated();

        GenericUrl testRunURL = BuildURL.getGenericURL("testRun");

        String strTestRunID = objTestRunStep.readValue("test-run-id");

        String strTemp = testRunURL.toString();

        GenericUrl runStepUrl = new GenericUrl(strTemp + "/" + strTestRunID + "/run-steps/");

        String testStepIDs = "";
        try {
            HttpRequest req = buildRequest.createGetRequest(runStepUrl);

            req.getHeaders().setContentType("application/xml");

            header.setHeader(headers, req);

            HttpResponse res = req.execute();

            InputStream iStream = res.getContent();
            String temp = CharStreams.toString(new InputStreamReader(iStream));
            testStepIDs = getRunStepID(temp);

            objTestRunStep.writeValue("TestRunSteps", testStepIDs);
        } catch (HttpResponseException e) {
            e.getLocalizedMessage();
        }

        return testStepIDs;
    }

    /**
     * Update test run with test steps.
     *
     * @param runProperties the test run details
     * @throws IOException                  the io exception
     * @throws ConfigurationException       the configuration exception
     */
    public void updateTestRun(LinkedHashMap<String, String> runProperties) throws IOException, ConfigurationException {

        String headers = isAuthenticated();

        XmlMapper xmlMapper = new XmlMapper();
        GenericUrl testRunURL = BuildURL.getGenericURL("testRun");

        TestRunBean updateTestRunXml = new TestRunBean().updateAllFieldsTestRunXML(runProperties);
        String payLoad = xmlMapper.writeValueAsString(updateTestRunXml);

        String strTestRunID = objTestRunStep.readValue("test-run-id");

        String strTemp = testRunURL.toString();

        GenericUrl testRunUrl = new GenericUrl(strTemp + "/" + strTestRunID);

        try {
            HttpRequest req = buildRequest.createPutRequest(testRunUrl, payLoad);

            req.getHeaders().setContentType("application/xml");

            header.setHeader(headers, req);

            req.execute();
        } catch (HttpResponseException e) {
            e.getLocalizedMessage();
        }

    }

    /**
     * Gets test run step id from http xml response.
     *
     * @param responseXml the http xml response
     * @return the test run step id
     * @throws SAXException                 the sax exception
     * @throws IOException                  the io exception
     * @throws ParserConfigurationException the parser configuration exception
     */
    public String getRunStepID(String responseXml) throws SAXException, IOException, ParserConfigurationException {
        String temp;
        String value = "";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        InputSource is = new InputSource(new StringReader(responseXml));

        Document document = builder.parse(is);

        NodeList entities = document.getElementsByTagName("Entity");

        for(int j=0;j<entities.getLength();j++) {
            Element entity = (Element)entities.item(j);
            NodeList nodes = entity.getElementsByTagName("Field");

            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);

                String name = element.getAttribute("Name");

                NodeList valueElement = element.getElementsByTagName("Value");

                if (name.equals("id")) {
                    try {
                        temp = valueElement.item(0).getTextContent();
                        if(value.equals("")) {
                            value = temp;
                        }
                        else {
                            value=value+";"+temp;
                        }
                    } catch (Exception e) {
                        System.out.println("Tag not available for " + name);
                    }
                    break;
                }
            }
        }

        return value;
    }


    /**
     * Create new test step and update status.
     *
     * @param stepProperties the test step details
     * @param stepName       the test step name
     * @throws IOException                  the io exception
     * @throws ConfigurationException       the configuration exception
     * @throws ParserConfigurationException the parser configuration exception
     * @throws SAXException                 the sax exception
     */
    public void createTestStepAndStatus(LinkedHashMap<String, String> stepProperties, String stepName) throws IOException, ConfigurationException, ParserConfigurationException, SAXException {

        String attachmentString = "attachment";

        String headers = isAuthenticated();

        GenericUrl testRunURL = BuildURL.getGenericURL("testRun");

        try {
            XmlMapper xmlMapper = new XmlMapper();
            TestRunStepBean updateTestRunStepXml = new TestRunStepBean().createTestRunStepXML(stepProperties, stepName);
            String payLoad = xmlMapper.writeValueAsString(updateTestRunStepXml);

            String strTestRunID = objTestRunStep.readValue("test-run-id");
            String strTemp = testRunURL.toString();

            GenericUrl runStepUrl = new GenericUrl(strTemp + "/" + strTestRunID + "/run-steps");
            HttpRequest req = buildRequest.createPostRequest(runStepUrl, payLoad);

            req.getHeaders().setContentType("application/xml");

            header.setHeader(headers, req);

            HttpResponse res = req.execute();

            if(stepProperties.containsKey(attachmentString)){
                InputStream iStream = res.getContent();
                String temp = CharStreams.toString(new InputStreamReader(iStream));
                String stepID = getRunStepID(temp);
                String[] attachmentPaths = stepProperties.get(attachmentString).split(";");
                for(int i = 0; i < attachmentPaths.length; i++) {
                    Path p = Paths.get(attachmentPaths[i]);
                    String file = p.getFileName().toString();
                    uploadAttachmentToStep(attachmentPaths[i].replace("\\" + file, ""), file, stepID);
                }
            }
        } catch (HttpResponseException e) {
            e.getLocalizedMessage();
        }
    }

    /**
     * Gets attribute value for http xml response.
     *
     * @param responseXml http xml response
     * @param attribute the attribute whose value to be fetched
     * @return the attribute value
     * @throws ParserConfigurationException the Parser Configuration Exception
     * @throws SAXException the sax exception
     * @throws IOException the io exception
     */
    public String getAttributeFromXMLResponse(String responseXml, String attribute)
            throws ParserConfigurationException, SAXException, IOException {

        String value = "";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        InputSource is = new InputSource(new StringReader(responseXml));

        Document document = builder.parse(is);

        NodeList nodes = document.getElementsByTagName("Field");

        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);

            String name = element.getAttribute("Name");

            if (name.equals(attribute)) {
                NodeList valueElement = element.getElementsByTagName("Value");

                try {
                    value = valueElement.item(0).getTextContent();
                    return value;
                } catch (Exception e) {
                    System.out.println("Tag not available for " + name);
                }
                break;
            }
        }

        return value;
    }

    /**
     * Gets tests by test case id.
     *
     * @param strTestID the test case id
     * @throws IOException            the io exception
     * @throws ConfigurationException the configuration exception
     */
    public void getTestsById(String strTestID) throws IOException, ConfigurationException {

        String headers = isAuthenticated();

        GenericUrl url = BuildURL.getGenericURL("testPlan");

        objTestInstance.writeValue("test-id", strTestID);
        objTestRun.writeValue("test-id", strTestID);

        try {
            String strTemp = url.toString();

            GenericUrl testRunUrl = new GenericUrl(strTemp + "/" + strTestID);

            HttpRequest req = buildRequest.createGetRequest(testRunUrl);

            header.setHeader(headers, req);

            req.execute();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    /**
     * Gets tests by test case name.
     *
     * @param strTestCaseName the test case name
     * @throws IOException                  the io exception
     * @throws ConfigurationException       the configuration exception
     * @throws ParserConfigurationException the parser configuration exception
     * @throws SAXException                 the sax exception
     */
    public void getTestsByName(String strTestCaseName) throws IOException, ConfigurationException, ParserConfigurationException, SAXException {

        String headers = isAuthenticated();

        GenericUrl url = BuildURL.getGenericURL("testPlan");

        objTestRun.writeValue("name", strTestCaseName);

        try {
            String strTemp = url.toString();

            GenericUrl testRunUrl = new GenericUrl(strTemp + "/?query={name['" + strTestCaseName + "']}");

            HttpRequest req = buildRequest.createGetRequest(testRunUrl);

            header.setHeader(headers, req);

            HttpResponse res = req.execute();

            InputStream iStream = res.getContent();
            String temp = CharStreams.toString(new InputStreamReader(iStream));

            objTestInstance.writeValue("test-id", getAttributeFromXMLResponse(temp, "id"));
            objTestRun.writeValue("test-id", getAttributeFromXMLResponse(temp, "id"));
        }
        catch (Exception e) {
            e.getLocalizedMessage();
            if(((HttpResponseException) e).getStatusCode() == 404){
                throw e;
            }
        }
    }

    /**
     * Upload attachment to the test run.
     *
     * @param filePath the attachment file path
     * @param fileName the attachment file name
     * @throws IOException            the io exception
     * @throws ConfigurationException the configuration exception
     */
    public void uploadAttachment(String filePath, String fileName) throws IOException, ConfigurationException {

        GenericUrl testRunURL = BuildURL.getGenericURL("testRun");

        String strTestRunID = objTestRunStep.readValue("test-run-id");

        String strTemp = testRunURL.toString();
        GenericUrl attachmentURL = new GenericUrl(strTemp + "/" + strTestRunID + "/attachments");
        HttpRequest req = buildRequest.createGetRequest(attachmentURL);
        String headers1 = isAuthenticated();
        req.getHeaders().setContentType("application/xml");

        header.setHeader(headers1, req);

        req.execute();

        String cookies = isAuthenticated();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookies);
        String boundary = "" + System.currentTimeMillis() ;
        final String LINE_FEED = "\r\n";
        filePath = filePath + "\\" + fileName;
        String attachmentsRunStepsURL = attachmentURL.toString();

        try
        {

            URL urlUpdateRunStepsURL = new URL(attachmentsRunStepsURL);
            HttpURLConnection httpConn = (HttpURLConnection) urlUpdateRunStepsURL.openConnection();

            httpConn.setUseCaches(false);
            httpConn.setDoOutput(true); // indicates POST method
            httpConn.setDoInput(true);
            httpConn.setRequestProperty("Cookie", cookies);
            httpConn.setRequestProperty("Content-Type","multipart/form-data; boundary=" + boundary);
            File uploadFile=new File(filePath);
            OutputStream outputStream = httpConn.getOutputStream();

            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream),true);
            fileName = uploadFile.getName();

            writer.append("--"+boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"filename\"").append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.append(fileName).append(LINE_FEED);
            writer.append("--"+boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"").append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.flush();
            FileInputStream inputStream = new FileInputStream(uploadFile);
            byte[] byteSteam=new byte[inputStream.available()];
            inputStream.read(byteSteam);
            outputStream.write(byteSteam);
            outputStream.flush();
            inputStream.close();
            writer.append(LINE_FEED);
            writer.append("--" + boundary + "--");
            writer.flush();
            writer.close();
            int status = httpConn.getResponseCode();

            if (status == HttpURLConnection.HTTP_CREATED) {
                httpConn.disconnect();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Upload attachment to test step.
     *
     * @param filePath the attachment file path
     * @param fileName the attachment file name
     * @param stepID   the test step id
     * @throws IOException            the io exception
     * @throws ConfigurationException the configuration exception
     */
    public void uploadAttachmentToStep(String filePath, String fileName, String stepID) throws IOException, ConfigurationException {

        String cookies = isAuthenticated();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookies);

        GenericUrl url = BuildURL.getGenericURL("baseProject");

        String strTemp = url.toString();

        GenericUrl attachmentURL = new GenericUrl(strTemp + "/run-steps/" + stepID + "/attachments");
        String boundary = "" + System.currentTimeMillis() ;
        final String LINE_FEED = "\r\n";
        filePath = filePath + "//" + fileName;
        String attachmentsRunStepsURL = attachmentURL.toString();

        try
        {
            URL urlUpdateRunStepsURL = new URL(attachmentsRunStepsURL);
            HttpURLConnection httpConn = (HttpURLConnection) urlUpdateRunStepsURL.openConnection();

            httpConn.setUseCaches(false);
            httpConn.setDoOutput(true); // indicates POST method
            httpConn.setDoInput(true);
            httpConn.setRequestProperty("Cookie", cookies);
            httpConn.setRequestProperty("Content-Type","multipart/form-data; boundary=" + boundary);
            File uploadFile=new File(filePath);
            OutputStream outputStream = httpConn.getOutputStream();

            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream),true);
            fileName = uploadFile.getName();

            writer.append("--"+boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"filename\"").append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.append(fileName).append(LINE_FEED);
            writer.append("--"+boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"").append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.flush();
            FileInputStream inputStream = new FileInputStream(uploadFile);
            byte[] byteSteam=new byte[inputStream.available()];
            inputStream.read(byteSteam);
            outputStream.write(byteSteam);
            outputStream.flush();
            inputStream.close();
            writer.append(LINE_FEED);
            writer.append("--" + boundary + "--");
            writer.flush();
            writer.close();

            int status = httpConn.getResponseCode();

            if (status == HttpURLConnection.HTTP_CREATED) {
                httpConn.disconnect();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Upload attachment with Octet stream.
     *
     * @param filePath the attachment file path
     * @param fileName the attachment file name
     * @throws Exception the exception
     */
    @SuppressWarnings("unused")
    public void attachWithOctetStream(String filePath, String fileName) throws Exception {

        String cookies = isAuthenticated();

        new PropertyReader();

        String strTestRunID = objTestRunStep.readValue("test-run-id");

        GenericUrl url = BuildURL.getGenericURL("testRun");
        String strTemp = url.toString();
        GenericUrl attachmentURL = new GenericUrl(strTemp + "/" + strTestRunID + "/attachments");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookies);
        headers.setContentType("application/octet-stream");
        headers.set("Slug",fileName);

        File f = new File(filePath + "\\" + fileName);

        FileInputStream fis = new FileInputStream(f);
        byte[] fileData = new byte[(int) f.length()];
        fis.read(fileData);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        bytes.write(fileData);

        bytes.close();

        fis.close();

        HttpRequestFactory requestFactory = Authenticator.getRequestFactory();

        try {

            HttpRequest req = requestFactory.buildPostRequest(attachmentURL,
                    ByteArrayContent.fromString("application/octet-stream", bytes.toString()));
            req.setHeaders(headers);
            req.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Find test set folder id.
     *
     * @throws ConfigurationException       the configuration exception
     * @throws IOException                  the io exception
     */
    public void findTestSetFolderID() throws ConfigurationException, IOException{

        String testSetPath = objConfig.readValue("testSetFolderPath");

        String[] arrPath = testSetPath.split("/");

        String headers = isAuthenticated();

        GenericUrl url = BuildURL.getGenericURL("testSetFolder");

        String parentID = "-1";
        try {
            for(int i=0;i<arrPath.length;i++) {
                GenericUrl testSetFolderUrl = new GenericUrl(url.toString() + "/?query={name['" + arrPath[i] + "'];parent-id['" + parentID + "']}");

                HttpRequest req = buildRequest.createGetRequest(testSetFolderUrl);

                header.setHeader(headers, req);

                HttpResponse res = req.execute();

                InputStream iStream = res.getContent();
                String temp = CharStreams.toString(new InputStreamReader(iStream));

                parentID = getAttributeFromXMLResponse(temp, "id");

                if(parentID.equals("")) {
                    System.out.println("Test Set Path " + testSetPath + " does not exist. Please provide the correct Test Set Path");
                    break;
                }
            }

            objTestSet.writeValue("parent-id", parentID);
        }catch(Exception e) {
            System.out.println("Test Set Path " + testSetPath + " does not exist. Please provide the correct Test Set Path");
        }
    }

    /**
     * Delete all test steps for a given test step id.
     *
     * @param stepID the test step id
     * @throws IOException            the io exception
     * @throws ConfigurationException the configuration exception
     */
    public void deleteAllTestSteps(String[] stepID) throws IOException, ConfigurationException {

        String headers = isAuthenticated();

        GenericUrl testRunURL = BuildURL.getGenericURL("testRun");

        try {
            String strTestRunID = objTestRunStep.readValue("test-run-id");
            String strTemp = testRunURL.toString();

            for(int i = 0; i < stepID.length; i++) {
                GenericUrl runStepUrl = new GenericUrl(strTemp + "/" + strTestRunID + "/run-steps/" + stepID[i]);
                HttpRequest req = buildRequest.createDeleteRequest(runStepUrl);
                req.getHeaders().setContentType("application/xml");
                header.setHeader(headers, req);
                HttpResponse res = req.execute();
            }

        } catch (HttpResponseException e) {
            e.getLocalizedMessage();
        }
    }

}
