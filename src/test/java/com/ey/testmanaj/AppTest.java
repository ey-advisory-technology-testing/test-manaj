package com.ey.testmanaj;

import static com.ey.testmanaj.App.updateTestCase;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import com.ey.testmanaj.urls.BuildURL;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.SystemUtils;
import org.junit.*;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.logging.MockServerLogger;
import org.mockserver.model.Header;
import org.mockserver.model.Headers;
import org.mockserver.socket.PortFactory;
import org.mockserver.socket.tls.KeyStoreFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class AppTest {

    private ClientAndServer mockServer;
    private App app;

    @Before
    public void startMockServer() throws IOException {
        this.mockServer = startClientAndServer( 8888);
        List<String> enc = new ArrayList<>(); enc.add("gzip");
        List<String> auth = new ArrayList<>(); auth.add("Basic dGVzdC51c2VybmFtZTo=");
        List<String> cookie = new ArrayList<>(); cookie.add("JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW");

        this.mockServer
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/qcbin/authentication-point/authenticate")
                                .withBody("")
                                .withHeaders(
                                        new Header("accept-encoding", enc),
                                        new Header("authorization", auth)
                                )
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withCookie(
                                        "JSESSIONID", "2By8LOhBmaW5nZXJwcmludCIlMDAzMW"
                                )
        );

        this.mockServer
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/qcbin/rest/site-session")
                                .withBody("")
                                .withHeaders(
                                        new Header("accept-encoding", enc),
                                        new Header("cookie", cookie)
                                )
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withCookie(
                                        "JSESSIONID", "2By8LOhBmaW5nZXJwcmludCIlMDAzMW"
                                )
                );

        app = new App(SystemUtils.getUserDir().toString(),
                FileSystems.getDefault().getSeparator() +
                        "src" + FileSystems.getDefault().getSeparator() +
                        "test" + FileSystems.getDefault().getSeparator() +
                        "resources" + FileSystems.getDefault().getSeparator() +
                        "testResources" + FileSystems.getDefault().getSeparator());

        BuildURL.setEnv();
    }

    @After
    public void stopMockServer() {
        mockServer.stop();
    }

    @Test
    public void testConstructor() {
        new App("Path",
                FileSystems.getDefault().getSeparator() +
                "src" + FileSystems.getDefault().getSeparator() +
                "test" + FileSystems.getDefault().getSeparator() +
                "resources" + FileSystems.getDefault().getSeparator() +
                "testResources" + FileSystems.getDefault().getSeparator());
        assertEquals("Path", App.resourcesFilePath);
    }

    @Test
    public void authenticate() throws ConfigurationException {
        assertNotNull(app.authenticate());
    }

    @Test
    public void isAuthenticated() throws IOException, ConfigurationException {
        assertNotNull(app.isAuthenticated());
    }

    @Test
    public void executeApp() throws Exception {

        String testCaseName = "Demo Test1";

        LinkedHashMap<String, String> runProperties = new LinkedHashMap<>();
        runProperties.put("attachment", "src/test/resources/jpg-vs-jpeg-file-formats_1.jpg;src/test/resources/jpg-vs-jpeg-file-formats_2.jpg");
        runProperties.put("comments", "This is a comment");

        LinkedHashMap<String, LinkedHashMap<String, String>> steps = new LinkedHashMap<>();

        LinkedHashMap<String, String> stepProperties = new LinkedHashMap<>();
        stepProperties.put("status", "Passed");
        stepProperties.put("description", "Given I navigate to www.google.com");
        stepProperties.put("execution-time","14:38:14");
        steps.put("Step 1", stepProperties);

        stepProperties = new LinkedHashMap<>();
        stepProperties.put("status", "Passed");
        stepProperties.put("description", "Given I enter text search into search.bar");
        stepProperties.put("expected", "Google Home Page Should Load");
        steps.put("Step 2", stepProperties);

        stepProperties = new LinkedHashMap<>();
        stepProperties.put("status", "Failed");
        stepProperties.put("description", "Then google.results are displayed");
        stepProperties.put("actual", "Exception in thread \"main\" java.util.NoSuchElementException\n" +
                "at java.util.Scanner.throwFor(Scanner.java:838)\n" +
                "at java.util.Scanner.next(Scanner.java:1461)\n" +
                "at java.util.Scanner.nextInt(Scanner.java:2091)\n" +
                "at java.util.Scanner.nextInt(Scanner.java:2050)\n" +
                "at Addition.main(Addition.java:16)");
        stepProperties.put("attachment", "src/test/resources/jpg-vs-jpeg-file-formats_1.jpg");
        steps.put("Step 3", stepProperties);

        stepProperties = new LinkedHashMap<>();
        stepProperties.put("status", "No Run");
        stepProperties.put("description", "Then google.logo is displayed");
        stepProperties.put("execution-date","2009-01-27");
        steps.put("Step 4", stepProperties);

        stepProperties = new LinkedHashMap<>();
        stepProperties.put("status", "No Run");
        stepProperties.put("description", "Then I click google.home");
        steps.put("Step 5", stepProperties);

        String resoucesPath = SystemUtils.getUserDir().toString();
        String configFilesRoot = "resources" + FileSystems.getDefault().getSeparator();

        updateTestCase(testCaseName, steps, runProperties, resoucesPath, configFilesRoot);
    }
}

