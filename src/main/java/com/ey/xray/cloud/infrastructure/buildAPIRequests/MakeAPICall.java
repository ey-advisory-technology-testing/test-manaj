package com.ey.xray.cloud.infrastructure.buildAPIRequests;

//import okhttp3.*;
import okhttp3.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * The type Make api call.
 */
public class MakeAPICall {

    /**
     * Gets auth token.
     *
     * @param ignoreInvalidCertificate the ignore invalid certificate
     * @param url                      the url
     * @param body                     the body
     * @return the auth token
     * @throws KeyManagementException   the key management exception
     * @throws NoSuchAlgorithmException the no such algorithm exception
     * @throws IOException              the io exception
     */
    public static HttpsURLConnection getAuthToken(boolean ignoreInvalidCertificate, URL url, Object body) throws KeyManagementException, NoSuchAlgorithmException, IOException {
        SSLContext ctx = SSLContext.getInstance("TLS");
        if (ignoreInvalidCertificate){
            ctx.init(null, new TrustManager[] { new InvalidCertificateTrustManager() }, null);
        }
        SSLContext.setDefault(ctx);

        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");

        String jsonInputString = body.toString();
        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        if (ignoreInvalidCertificate){
            connection.setHostnameVerifier(new InvalidCertificateHostVerifier());
        }
        int responseCode = connection.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

        return connection;
    }

    /**
     * Gets unsafe ok http client.
     *
     * @return the unsafe ok http client
     */
    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
                                                       String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
                                                       String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            return new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    }).build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the latest Cucumber JSON report file from the directory after a test execution.
     *
     * @param reportDir the report directory where the Cucumber JSON report files are stored
     * @return the latest report file
     */
    public static File getLatestReportFile(File reportDir) throws InterruptedException {
        //File directory = new File("./target/reports");
        File[] files = reportDir.listFiles((dir, name) -> name.endsWith(".json"));
        long lastModifiedTime = Long.MIN_VALUE;
        File chosenFile = null;


        if (files != null)
        {
            for (File file : files)
            {
                //System.out.println("file name is:" +file.getName());
                if (file.lastModified() > lastModifiedTime) {
                    chosenFile = file;
                    lastModifiedTime = file.lastModified();
                }
            }
        }
        System.out.println("Chosen file is: " +chosenFile.getAbsolutePath());
        return chosenFile;
    }

    /**
     * Gets screenshot.
     *
     * @return the screenshot
     */
    public static File getScreenshot() {
        File directory = new File("./target/reports/cucumber-html-reports/embeddings");
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".png"));
        long lastModifiedTime = Long.MIN_VALUE;
        File chosenFile = null;


        if (files != null)
        {
            for (File file : files)
            {
                //System.out.println("file name is:" +file.getName());
                if (file.lastModified() > lastModifiedTime) {
                    chosenFile = file;
                    lastModifiedTime = file.lastModified();
                }
            }
        }
        System.out.println("Chosen file is: " +chosenFile.getName());
        return chosenFile;
    }

    /**
     * Import test execution results to JIRA.
     *
     * @param url       the url endpoint
     * @param authToken the auth token
     * @param reportDir the report dir
     * @param infoJson  the info json
     * @throws KeyManagementException   the key management exception
     * @throws NoSuchAlgorithmException the no such algorithm exception
     * @throws IOException              the io exception
     * @throws ParseException           the parse exception
     */
    public static void importRes(URL url, String authToken, File reportDir, File infoJson, URL jiraUrl, List<File> filesList, String xAtlassianToken) throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException {

        try {
            File chosenFile = getLatestReportFile(reportDir);
            System.out.println("chosen file name: " +chosenFile.getName());

            OkHttpClient client = getUnsafeOkHttpClient();
            MediaType mediaType = MediaType.parse("multipart/form-data");
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("info", infoJson.getName(),
                            RequestBody.create(MediaType.parse("application/octet-stream"),
                                    infoJson))
                    .addFormDataPart("results", chosenFile.getName(),
                            RequestBody.create(MediaType.parse("application/octet-stream"),
                                    chosenFile))
                    .build();
            Request request = new Request.Builder()
                    .url(url.toString())
                    .method("POST", body)
                    .addHeader("Content-Type", "multipart/form-data")
                    .addHeader("Authorization", "Bearer " + authToken.substring(1, authToken.lastIndexOf("\"")))
                    .build();

            Response response = client.newCall(request).execute();

            JSONParser parser = new JSONParser();
            JSONObject keyVal;

            Object json = parser.parse(response.body().string());
//        JSONObject obj = new JSONObject(text);
            keyVal = (JSONObject) json;
            System.out.println("response code: " +response.code());
            String jiraKey = keyVal.get("key").toString();
            System.out.println("jira key: " +jiraKey);
            response.close();
            attachFiles(jiraUrl,filesList,jiraKey,xAtlassianToken);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Attach files to JIRA by entering the JIRA key as a parameter
     *
     * @param jiraKey the jira key
     * @throws IOException the io exception
     */
    public static void attachFiles(URL jiraUrl, List<File> filesList, String jiraKey, String xAtlassianToken) throws IOException {

        try {

            OkHttpClient client = getUnsafeOkHttpClient();
            MediaType mediaType = MediaType.parse("text/plain");
            MultipartBody.Builder body = new MultipartBody.Builder().setType(MultipartBody.FORM);
            for (int i = 0; i < filesList.size(); i++) {
                body.addFormDataPart("file", filesList.get(i).getName(), RequestBody.create(MediaType.parse("application/octet-stream"), filesList.get(i)));
            }
            RequestBody reqBody = body.build();

            Request request = new Request.Builder()
                    .url(jiraUrl + jiraKey + "/attachments")
                    .method("POST", reqBody)
                    .addHeader("X-Atlassian-Token", "no-check")
                    .addHeader("Authorization", "Basic " +xAtlassianToken)
                    .build();
            Response response = client.newCall(request).execute();
            System.out.println("url: " + jiraUrl + jiraKey + "/attachments");
            System.out.println("response: " + response.body().string());
            //response.body().close();
            client.connectionPool().evictAll();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * Import Cucumber tests to JIRA via API call, passing in feature file, url of the endpoint, and auth token
     *
     * @param featureFile the feature file
     * @param url         the url endpoint
     * @param authToken   the auth token
     * @throws KeyManagementException   the key management exception
     * @throws NoSuchAlgorithmException the no such algorithm exception
     * @throws IOException              the io exception
     * @throws ParseException           the parse exception
     */
    public static void importCucumberTestsToJIRA(File featureFile, URL url, String authToken) throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException {

        //getUnsafeOkHttpClient();

        OkHttpClient client = getUnsafeOkHttpClient();
        MediaType mediaType = MediaType.parse("multipart/form-data");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file",featureFile.getName(),
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                featureFile))
                .build();
        Request request = new Request.Builder()
                .url(url.toString())
                .method("POST", body)
                .addHeader("Content-Type", "multipart/form-data")
                .addHeader("Authorization", "Bearer " +authToken.substring(1, authToken.lastIndexOf("\"")))
                .build();

        Response response = client.newCall(request).execute();
        System.out.println("response: " +response.body().string());

    }

//    public static void test() throws ParseException {
//
//        JSONParser parser = new JSONParser();
//
//        JSON = "{\"key\": \"text\"}";
//        JSONObject json = (JSONObject) parser.parse(text);
////        JSONObject obj = new JSONObject(text);
//        JSONObject keyVal = json.get("key");
//
//        String keyText = keyVal.toString();
//        System.out.println("key is:" +keyText);
//
//    }


}