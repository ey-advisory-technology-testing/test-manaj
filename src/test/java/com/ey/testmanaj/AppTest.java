package com.ey.testmanaj;

import static com.ey.testmanaj.App.updateTestCase;
import static org.junit.Assert.*;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import com.ey.testmanaj.urls.BuildURL;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponseException;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.SystemUtils;

import org.junit.*;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.logging.MockServerLogger;
import org.mockserver.model.Header;
import org.mockserver.model.Parameter;
import org.mockserver.model.Headers;
import org.mockserver.socket.PortFactory;
import org.mockserver.socket.tls.KeyStoreFactory;
import org.xml.sax.SAXException;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.*;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import java.nio.file.Paths;

public class AppTest {

    private ClientAndServer mockServer;
    private App app;
    private String runStepResponseXML;
    private String testSetResponseXML;


    @Before
    public void startMockServer() throws IOException {
        this.mockServer = startClientAndServer(8888);
        List<String> enc = new ArrayList<>();
        enc.add("gzip");
        List<String> auth = new ArrayList<>();
        auth.add("Basic dGVzdC51c2VybmFtZTo=");
        List<String> cookie = new ArrayList<>();
        cookie.add("JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW");
        List<String> contentLength = new ArrayList<>();
        contentLength.add("0");
        List<String> connection = new ArrayList<>();
        connection.add("keep-alive");

        this.runStepResponseXML = ("<Entities TotalResults=\"2\">\n" +
                "    <Entity Type=\"run-step\"> \n" +
                "        <Fields>\t\n" +
                "            <Field Name=\"test-id\">\n" +
                "                <Value>2</Value>\n" +
                "            </Field>           \n" +
                "            <Field Name=\"id\">\n" +
                "                <Value>1001</Value>\n" +
                "            </Field>\t\t\t\n" +
                "            <Field Name=\"actual\">\n" +
                "                <Value></Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"step-order\">\n" +
                "                <Value>1</Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"expected\">\n" +
                "                <Value></Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"name\">\n" +
                "                <Value>Step 1</Value>\n" +
                "            </Field>            \n" +
                "            <Field Name=\"status\">\n" +
                "                <Value>Passed</Value>\n" +
                "            </Field>\n" +
                "        </Fields>\n" +
                "    </Entity>\n" +
                "\t<Entity Type=\"run-step\"> \n" +
                "        <Fields>\t\n" +
                "            <Field Name=\"test-id\">\n" +
                "                <Value>2</Value>\n" +
                "            </Field>                       \n" +
                "\t\t\t<Field Name=\"id\">\n" +
                "                <Value>1002</Value>\n" +
                "            </Field>\t\t\t\n" +
                "            <Field Name=\"actual\">\n" +
                "                <Value></Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"step-order\">\n" +
                "                <Value>2</Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"expected\">\n" +
                "                <Value></Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"name\">\n" +
                "                <Value>Step 2</Value>\n" +
                "            </Field>            \n" +
                "            <Field Name=\"status\">\n" +
                "                <Value>Passed</Value>\n" +
                "            </Field>\n" +
                "        </Fields>\n" +
                "    </Entity>\n" +
                "</Entities>\n");

        this.testSetResponseXML = ("<Entities TotalResults=\"2\">\n" +
                "    <Entity Type=\"test-set\">\n" +
                "        <ChildrenCount>\n" +
                "            <Value>0</Value>\n" +
                "        </ChildrenCount>\n" +
                "        <Fields>\n" +
                "            <Field Name=\"os-config\">\n" +
                "                <Value></Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"pinned-baseline\">\n" +
                "                <Value></Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"ver-stamp\"/>\n" +
                "            <Field Name=\"report-settings\">\n" +
                "                <Value></Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"description\">\n" +
                "                <Value></Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"order-id\">\n" +
                "                <Value>1</Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"request-id\"/>\n" +
                "            <Field Name=\"has-linkage\">\n" +
                "                <Value>N</Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"exec-event-handle\">\n" +
                "                <Value></Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"last-modified\"/>\n" +
                "            <Field Name=\"environment\">\n" +
                "                <Value></Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"open-date\"/>\n" +
                "            <Field Name=\"subtype-id\">\n" +
                "                <Value>hp.qc.test-set.default</Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"attachment\">\n" +
                "                <Value></Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"close-date\"/>\n" +
                "            <Field Name=\"mail-settings\">\n" +
                "                <Value></Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"cycle-config\">\n" +
                "                <Value></Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"name\">\n" +
                "                <Value>default</Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"dynamic-data\">\n" +
                "                <Value></Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"comment\">\n" +
                "                <Value></Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"id\">\n" +
                "                <Value>501</Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"parent-id\">\n" +
                "                <Value>-2</Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"assign-rcyc\">\n" +
                "                <Value></Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"status\">\n" +
                "                <Value></Value>\n" +
                "            </Field>\n" +
                "        </Fields>\n" +
                "        <RelatedEntities/>\n" +
                "    </Entity>\n" +
                "    <singleElementCollection>false</singleElementCollection>\n" +
                "\n" +
                "</Entities>");

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


//getTestsById mock
        this.mockServer
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/qcbin")
                                .withQueryStringParameter("login-form-required", "y/rest/domains/TRAINING/projects/ALM_Sample/tests/2523")

                                .withHeaders(
                                        new Header("accept-encoding", enc),
                                        new Header("cookie", "JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW;")
                                )
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("content-length", contentLength),
                                        new Header("connection", connection)
                                )


                );

//getTestsByName mock
        this.mockServer
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/qcbin")
                                .withQueryStringParameters(

                                        new Parameter("login-form-required", "y/rest/domains/TRAINING/projects/ALM_Sample/tests/?query={name['Demo Test1']}")

                                )
                                .withHeaders(
                                        new Header("accept-encoding", enc),
                                        new Header("cookie", "JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW;")
                                )
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("connection", connection)
                                )
                                .withBody("<Entities TotalResults=\"1\">\n" +
                                        "    <Entity Type=\"test\">\n" +
                                        "        <Fields>\n" +
                                        "            <Field Name=\"name\">\n" +
                                        "                <Value>Demo Test1</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"order-id\">\n" +
                                        "                <Value>1</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"request-id\"/>\n" +
                                        "            <Field Name=\"has-linkage\">\n" +
                                        "                <Value>N</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"last-modified\"/>\n" +
                                        "            <Field Name=\"environment\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"open-date\"/>\n" +
                                        "            <Field Name=\"subtype-id\">\n" +
                                        "                <Value>hp.qc.test-set.default</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"attachment\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"cycle-config\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"comment\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"id\">\n" +
                                        "                <Value>2523</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"parent-id\">\n" +
                                        "                <Value>-2</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"status\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "        </Fields>\n" +
                                        "        <RelatedEntities/>\n" +
                                        "    </Entity>\n" +
                                        "</Entities>")


                );

        //createTestInstance Get mock
        this.mockServer
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/qcbin")
                                .withQueryStringParameters(

                                        new Parameter("login-form-required", "y/rest/domains/TRAINING/projects/ALM_Sample/test-instances/?query={cycle-id[501]"),
                                        new Parameter("test-id[2523]}", "")
                                )
                                .withHeaders(
                                        new Header("cookie", "JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW;")
                                )
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(new Header("connection", connection))
                                .withBody("<Entities TotalResults=\"0\">\n" +
                                        "</Entities>")
                );


//createTestInstance POST mock
        this.mockServer
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/qcbin")
                                .withBody("<Entity Type=\"test-instance\"><ChildrenCount>0</ChildrenCount><Fields><Field Name=\"test-id\"><Value>2523</Value></Field><Field Name=\"subtype-id\"><Value>hp.qc.test-instance.MANUAL</Value></Field><Field Name=\"cycle-id\"><Value>501</Value></Field><Field Name=\"test-instance\"><Value>1</Value></Field><Field Name=\"order-id\"><Value>1</Value></Field><Field Name=\"cycle\"><Value>1</Value></Field><Field Name=\"status\"><Value>Not Completed</Value></Field></Fields><RelatedEntities/></Entity>")
                                .withQueryStringParameters(
                                        new Parameter("login-form-required", "y/rest/domains/TRAINING/projects/ALM_Sample/test-instances")
                                )
                                .withHeaders(
                                        new Header("cookie", "JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW;")
                                )
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(new Header("connection", connection))
                                .withBody("<Entities TotalResults=\"1\">\n" +
                                        "    <Entity Type=\"test-instance\">\n" +
                                        "        <ChildrenCount>\n" +
                                        "            <Value>0</Value>\n" +
                                        "        </ChildrenCount>\n" +
                                        "        <Fields>\n" +
                                        "            <Field Name=\"test-id\">\n" +
                                        "                <Value>2</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"os-config\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"data-obj\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"is-dynamic\">\n" +
                                        "                <Value>N</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"exec-time\">\n" +
                                        "                <Value>14:13:41</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"cycle\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"has-linkage\">\n" +
                                        "                <Value>N</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"exec-event-handle\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"exec-date\">\n" +
                                        "                <Value>2020-02-24</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"last-modified\">\n" +
                                        "                <Value>2020-02-24 14:13:41</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"subtype-id\">\n" +
                                        "                <Value>hp.qc.test-instance.MANUAL</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"cycle-id\">\n" +
                                        "                <Value>1</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"attachment\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"id\">\n" +
                                        "                <Value>501</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"plan-scheduling-date\"/>\n" +
                                        "            <Field Name=\"assign-rcyc\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"test-config-id\">\n" +
                                        "                <Value>1002</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"owner\">\n" +
                                        "                <Value>sa</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"pinned-baseline\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"ver-stamp\">\n" +
                                        "                <Value>2</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"test-instance\">\n" +
                                        "                <Value>1</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"host-name\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"order-id\">\n" +
                                        "                <Value>1</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"eparams\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"iterations\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"environment\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"actual-tester\">\n" +
                                        "                <Value>sa</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"name\">\n" +
                                        "                <Value>Demo Test1 [1]</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"bpta-change-awareness\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"plan-scheduling-time\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"status\">\n" +
                                        "                <Value>Passed</Value>\n" +
                                        "            </Field>\n" +
                                        "        </Fields>\n" +
                                        "        <RelatedEntities/>\n" +
                                        "    </Entity>\n" +
                                        "    <singleElementCollection>false</singleElementCollection>\n" +
                                        "</Entities>")
                );


        //getTestSet mock
        this.mockServer
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/qcbin")
                                .withQueryStringParameters(

                                        new Parameter("parent-id[21]}", ""),
                                        new Parameter("login-form-required", "y/rest/domains/TRAINING/projects/ALM_Sample/test-sets/?query={name['2BrandNewTestSet3']")

                                )
                                .withHeaders(
                                        new Header("Accept-Encoding", enc),
                                        new Header("Cookie", "JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW;")
                                )
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header(CONTENT_TYPE.toString(), "application/xml"),
                                        new Header("connection", connection)
                                )
                                .withBody(testSetResponseXML)
                );

        //createTestSet mock
        this.mockServer
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/qcbin/rest/domains/TRAINING/projects/ALM_Sample/test-sets")
//                                .withQueryStringParameter("login-form-required", "y/rest/domains/TRAINING/projects/ALM_Sample/test-sets")
                                .withBody("<Entity Type=\"test-set\"><Fields><Field Name=\"subtype-id\"><Value>hp.qc.test-set.default</Value></Field><Field Name=\"name\"><Value>2BrandNewTestSet3</Value></Field><Field Name=\"parent-id\"><Value>21</Value></Field><Field Name=\"status\"><Value></Value></Field></Fields></Entity>")
                                .withHeaders(
                                        new Header("content-type", "application/xml"),
                                        new Header("cookie", "JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW;")
                                )
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeader(new Header("connection", connection))
                                .withBody(testSetResponseXML)
                );


//updateTestRun Mock
        this.mockServer
                .when(
                        request()
                                .withMethod("PUT")
                                .withPath("/qcbin")
                                .withQueryStringParameter("login-form-required", "y/rest/domains/TRAINING/projects/ALM_Sample/runs/3532")
                                .withBody("<Entity Type=\"run\"><Fields><Field Name=\"attachment\"><Value>Y</Value></Field><Field Name=\"comments\"><Value>This is a comment</Value></Field><Field Name=\"status\"><Value>Failed</Value></Field></Fields></Entity>")
                                .withHeaders(
                                        new Header("cookie", "JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW;")

                                )

                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("content-length", contentLength),
                                        new Header("connection", connection)
                                )


                );


        //createTestStepAndStatus mock1
        this.mockServer
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/qcbin")
                                .withQueryStringParameter("login-form-required", "y/rest/domains/TRAINING/projects/ALM_Sample/runs/3532/run-steps")
                                .withBody("<Entity Type=\"run-step\"><Fields><Field Name=\"name\"><Value>Step 1</Value></Field><Field Name=\"status\"><Value>Passed</Value></Field><Field Name=\"description\"><Value>Given I navigate to www.google.com</Value></Field><Field Name=\"execution-time\"><Value>14:38:14</Value></Field></Fields></Entity>")
                                .withHeaders(
                                        new Header("cookie", "JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW;")

                                )

                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("connection", connection)
                                )


                );

        //createTestStepAndStatus mock2
        this.mockServer
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/qcbin")
                                .withQueryStringParameter("login-form-required", "y/rest/domains/TRAINING/projects/ALM_Sample/runs/3532/run-steps")
                                .withBody("<Entity Type=\"run-step\"><Fields><Field Name=\"name\"><Value>Step 2</Value></Field><Field Name=\"status\"><Value>Passed</Value></Field><Field Name=\"description\"><Value>Given I enter text search into search.bar</Value></Field><Field Name=\"expected\"><Value>Google Home Page Should Load</Value></Field></Fields></Entity>")
                                .withHeaders(
                                        new Header("cookie", "JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW;")

                                )

                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("connection", connection)
                                )


                );

        //createTestStepAndStatus mock3
        this.mockServer
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/qcbin")
                                .withQueryStringParameter("login-form-required", "y/rest/domains/TRAINING/projects/ALM_Sample/runs/3532/run-steps")
                                .withBody("<Entity Type=\"run-step\"><Fields><Field Name=\"name\"><Value>Step 3</Value></Field><Field Name=\"status\"><Value>Failed</Value></Field><Field Name=\"description\"><Value>Then google.results are displayed</Value></Field><Field Name=\"actual\"><Value>Exception in thread \"main\" java.util.NoSuchElementException\n" +
                                        "at java.util.Scanner.throwFor(Scanner.java:838)\n" +
                                        "at java.util.Scanner.next(Scanner.java:1461)\n" +
                                        "at java.util.Scanner.nextInt(Scanner.java:2091)\n" +
                                        "at java.util.Scanner.nextInt(Scanner.java:2050)\n" +
                                        "at Addition.main(Addition.java:16)</Value></Field><Field Name=\"attachment\"><Value>Y</Value></Field></Fields></Entity>")
                                .withHeaders(
                                        new Header("cookie", "JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW;")

                                )

                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("connection", connection)
                                )
                                .withBody(runStepResponseXML)


                );

        //createTestStepAndStatus mock4
        this.mockServer
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/qcbin")
                                .withQueryStringParameter("login-form-required", "y/rest/domains/TRAINING/projects/ALM_Sample/runs/3532/run-steps")
                                .withBody("<Entity Type=\"run-step\"><Fields><Field Name=\"name\"><Value>Step 4</Value></Field><Field Name=\"status\"><Value>No Run</Value></Field><Field Name=\"description\"><Value>Then google.logo is displayed</Value></Field><Field Name=\"execution-date\"><Value>2009-01-27</Value></Field></Fields></Entity>")
                                .withHeaders(
                                        new Header("cookie", "JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW;")

                                )

                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("connection", connection)
                                )


                );

        //createTestStepAndStatus mock5
        this.mockServer
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/qcbin")
                                .withQueryStringParameter("login-form-required", "y/rest/domains/TRAINING/projects/ALM_Sample/runs/3532/run-steps")
                                .withBody("<Entity Type=\"run-step\"><Fields><Field Name=\"name\"><Value>Step 5</Value></Field><Field Name=\"status\"><Value>No Run</Value></Field><Field Name=\"description\"><Value>Then I click google.home</Value></Field></Fields></Entity>")
                                .withHeaders(
                                        new Header("cookie", "JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW;")

                                )

                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("connection", connection)
                                )
                );


        //getTestSteps mock

        this.mockServer
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/qcbin")
                                .withQueryStringParameter("login-form-required", "y/rest/domains/TRAINING/projects/ALM_Sample/runs/3532/run-steps/")
                                .withHeaders(
                                        new Header("accept-encoding", enc),
                                        new Header("cookie", "JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW;")
                                )

                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("connection", connection),
                                        new Header(CONTENT_TYPE.toString(), "application/xml")
                                )
                                .withBody(runStepResponseXML)


                );


        //CreateTestRun mock
        this.mockServer
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/qcbin")
                                .withQueryStringParameter("login-form-required", "y/rest/domains/TRAINING/projects/ALM_Sample/runs")
                                .withBody("<Entity Type=\"run\"><Fields><Field Name=\"test-id\"><Value>2523</Value></Field><Field Name=\"owner\"><Value>test.username</Value></Field><Field Name=\"subtype-id\"><Value>hp.qc.run.MANUAL</Value></Field><Field Name=\"cycle-id\"><Value>501</Value></Field><Field Name=\"test-instance\"><Value>1</Value></Field><Field Name=\"name\"><Value>Demo Test1</Value></Field><Field Name=\"testcycl-id\"><Value>501</Value></Field><Field Name=\"status\"><Value>Not Completed</Value></Field></Fields></Entity>")

                                .withHeaders(
                                        new Header("cookie", "JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW;"),
                                        new Header(CONTENT_TYPE.toString(), "application/xml")
                                )
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("connection", connection),
                                        new Header(CONTENT_TYPE.toString(), "application/xml")
                                )
                                .withBody("<Entities TotalResults=\"1\">\n" +
                                        "    <Entity Type=\"run\">\n" +
                                        "        <ChildrenCount>\n" +
                                        "            <Value>0</Value>\n" +
                                        "        </ChildrenCount>\n" +
                                        "        <Fields>\n" +
                                        "            <Field Name=\"test-id\">\n" +
                                        "                <Value>2</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"test-name\">\n" +
                                        "                <Value>Demo Test1</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"has-linkage\">\n" +
                                        "                <Value>N</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"path\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"cycle-id\">\n" +
                                        "                <Value>1</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"vc-version-number\"/>\n" +
                                        "            <Field Name=\"draft\">\n" +
                                        "                <Value>N</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"host\">\n" +
                                        "                <Value>CNWUJIEJ02</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"id\">\n" +
                                        "                <Value>3532</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"state\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"test-config-id\">\n" +
                                        "                <Value>1002</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"ver-stamp\">\n" +
                                        "                <Value>2</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"iters-params-values\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"os-build\">\n" +
                                        "                <Value>Build 17134</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"os-sp\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"name\">\n" +
                                        "                <Value>Run_2-24_14-13-32</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"testcycl-name\">\n" +
                                        "                <Value>Demo Test1 [1]</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"status\">\n" +
                                        "                <Value>Passed</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"os-config\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"vc-locked-by\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"has-vtc\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"bpt-structure\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"cycle\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"duration\">\n" +
                                        "                <Value>8</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"execution-date\">\n" +
                                        "                <Value>2020-02-24</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"last-modified\">\n" +
                                        "                <Value>2020-02-24 14:13:41</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"subtype-id\">\n" +
                                        "                <Value>hp.qc.run.MANUAL</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"attachment\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"test-description\">\n" +
                                        "                <Value>&lt;html&gt;&lt;body&gt;\n" +
                                        "ddddddddddddddd\n" +
                                        "&lt;/body&gt;&lt;/html&gt;</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"text-sync\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"assign-rcyc\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"owner\">\n" +
                                        "                <Value>sa</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"pinned-baseline\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"comments\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"iters-sum-status\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"bpta-change-detected\"/>\n" +
                                        "            <Field Name=\"test-instance\">\n" +
                                        "                <Value>1</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"cycle-name\">\n" +
                                        "                <Value>testset1</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"os-name\">\n" +
                                        "                <Value>Windows 10</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"environment\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"vc-status\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"execution-time\">\n" +
                                        "                <Value>14:13:41</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"bpta-change-awareness\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"testcycl-id\">\n" +
                                        "                <Value>1</Value>\n" +
                                        "            </Field>\n" +
                                        "        </Fields>\n" +
                                        "        <RelatedEntities/>\n" +
                                        "    </Entity>\n" +
                                        "    <singleElementCollection>false</singleElementCollection>\n" +
                                        "</Entities>")


                );

        //deleteAllTestSteps mock

        this.mockServer
                .when(
                        request()
                                .withMethod("DELETE")
                                .withPath("/qcbin")
                                .withQueryStringParameter("login-form-required", "y/rest/domains/TRAINING/projects/ALM_Sample/runs/3532/run-steps/1001")
                                .withHeaders(
                                        new Header("accept-encoding", enc),
                                        new Header("cookie", "JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW;")
                                )

                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("content-length", contentLength),
                                        new Header("connection", connection)
                                )


                );

        this.mockServer
                .when(
                        request()
                                .withMethod("DELETE")
                                .withPath("/qcbin")
                                .withQueryStringParameter("login-form-required", "y/rest/domains/TRAINING/projects/ALM_Sample/runs/3532/run-steps/1002")
                                .withHeaders(
                                        new Header("accept-encoding", enc),
                                        new Header("cookie", "JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW;")
                                )

                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("content-length", contentLength),
                                        new Header("connection", connection)
                                )


                );

        //  findTestSetFolderIDTestMock1
        this.mockServer
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/qcbin")
                                .withQueryStringParameters(
                                        new Parameter("parent-id['-1']}", ""),
                                        new Parameter("login-form-required", "y/rest/domains/TRAINING/projects/ALM_Sample/test-set-folders/?query={name['Root']")
                                )
                                .withHeaders(
                                        new Header("cookie", "JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW;")
                                )
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeader(new Header("connection", connection))
                                .withBody("<Entities TotalResults=\"1\">\n" +
                                        "    <Entity Type=\"test-set-folder\">\n" +
                                        "        <ChildrenCount>\n" +
                                        "            <Value>0</Value>\n" +
                                        "        </ChildrenCount>\n" +
                                        "        <Fields>\n" +
                                        "            <Field Name=\"last-modified\"/>\n" +
                                        "            <Field Name=\"ver-stamp\">\n" +
                                        "                <Value>1</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"attachment\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"workflow\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"hierarchical-path\">\n" +
                                        "                <Value>AAA</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"name\">\n" +
                                        "                <Value>Root</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"description\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"no-of-sons\"/>\n" +
                                        "            <Field Name=\"id\">\n" +
                                        "                <Value>0</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"parent-id\">\n" +
                                        "                <Value>-1</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"order-id\">\n" +
                                        "                <Value>1</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"assign-rcyc\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "        </Fields>\n" +
                                        "        <RelatedEntities/>\n" +
                                        "    </Entity>\n" +
                                        "    <singleElementCollection>false</singleElementCollection>\n" +
                                        "</Entities>")
                );

        //  findTestSetFolderIDTestMock2
        this.mockServer
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/qcbin")
                                .withQueryStringParameters(
                                        new Parameter("parent-id['0']}", ""),
                                        new Parameter("login-form-required", "y/rest/domains/TRAINING/projects/ALM_Sample/test-set-folders/?query={name['NEXTGEN_ETAF_DEMO']")
                                )
                                .withHeaders(
                                        new Header("cookie", "JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW;")
                                )
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeader(new Header("connection", connection))
                                .withBody("<Entities TotalResults=\"1\">\n" +
                                        "    <Entity Type=\"test-set-folder\">\n" +
                                        "        <ChildrenCount>\n" +
                                        "            <Value>0</Value>\n" +
                                        "        </ChildrenCount>\n" +
                                        "        <Fields>\n" +
                                        "            <Field Name=\"last-modified\"/>\n" +
                                        "            <Field Name=\"ver-stamp\">\n" +
                                        "                <Value>1</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"attachment\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"workflow\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"hierarchical-path\">\n" +
                                        "                <Value>AAA</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"name\">\n" +
                                        "                <Value>Root</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"description\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"no-of-sons\"/>\n" +
                                        "            <Field Name=\"id\">\n" +
                                        "                <Value>21</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"parent-id\">\n" +
                                        "                <Value>-1</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"order-id\">\n" +
                                        "                <Value>1</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"assign-rcyc\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "        </Fields>\n" +
                                        "        <RelatedEntities/>\n" +
                                        "    </Entity>\n" +
                                        "    <singleElementCollection>false</singleElementCollection>\n" +
                                        "</Entities>")
                );

        //uploadAttachment GET mock
        this.mockServer
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/qcbin")
                                .withQueryStringParameter("login-form-required", "y/rest/domains/TRAINING/projects/ALM_Sample/runs/3532/attachments")
                                .withHeaders(
                                        new Header("cookie", "JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW;")

                                )
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("content-length", contentLength),
                                        new Header("connection", connection)
                                )


                );



        //  attachWithOctetStream mock

        this.mockServer
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/qcbin")
                                .withQueryStringParameter("login-form-required", "y/rest/domains/TRAINING/projects/ALM_Sample/runs/3532/attachments")

                                .withHeaders(
                                        new Header("accept-encoding", enc),
                                        new Header("cookie", "JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW;"),
                                        new Header("content-type", "application/octet-stream"),
                                        new Header("slug", "jpg-vs-jpeg-file-formats_1.jpg")
                                )
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("content-length", contentLength),
                                        new Header("connection", connection)
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
    public void authenticate() throws ConfigurationException, IOException {
        assertNotNull(app.authenticate());
    }

    @Test
    public void isAuthenticated() throws IOException, ConfigurationException {
        assertNotNull(app.isAuthenticated());
    }

    @Test
    public void getHeader() throws IOException {
        assertEquals("", app.getHeader("JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW"));
    }


    @Test
    public void getSession() throws IOException {
        GenericUrl url = new GenericUrl("http://localhost:8888/qcbin/rest/site-session");
        assertNotNull(app.getSession(url, "<session-parameters><client-type>REST Client</client-type></session-parameters>", "JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW"));
    }


    @Test(expected = Test.None.class)
    public void updateTestRun() throws IOException, ConfigurationException {

        LinkedHashMap<String, String> runProperties = new LinkedHashMap<>();
        runProperties.put("attachment", "src/test/resources/jpg-vs-jpeg-file-formats_1.jpg;src/test/resources/jpg-vs-jpeg-file-formats_2.jpg");
        runProperties.put("comments", "This is a comment");
        runProperties.put("status", "Failed");
        app.updateTestRun(runProperties);
    }


    @Test(expected = Test.None.class)
    public void getTestsById() throws IOException, ConfigurationException {
        app.getTestsById("2523");
    }

    @Test(expected = Test.None.class)
    public void getTestsByName() throws ParserConfigurationException, SAXException, IOException, ConfigurationException {
        app.getTestsByName("Demo Test1");
    }


    @Test(expected = Test.None.class)
    public void createTestInstanceWithTestName() throws ParserConfigurationException, SAXException, IOException, ConfigurationException {
        app.createTestInstance("Demo Test1");
    }

    @Test(expected = Test.None.class)
    public void createTestInstanceWithTestIDAndNoExistingTestInstance() throws ParserConfigurationException, SAXException, IOException, ConfigurationException {
        app.createTestInstance("2523");
    }


    @Test(expected = Test.None.class)
    public void createTestStepAndStatus() throws IOException, ConfigurationException, ParserConfigurationException, SAXException {
        LinkedHashMap<String, String> stepProperties = new LinkedHashMap<>();
        stepProperties.put("status", "Passed");
        stepProperties.put("description", "Given I navigate to www.google.com");
        stepProperties.put("execution-time", "14:38:14");
        app.createTestStepAndStatus(stepProperties, "Step 1");
    }


    @Test(expected = Test.None.class) //need to complete  uploadAttachmentToStep
    public void createTestStepAndStatusWithAttachment() throws IOException, ConfigurationException, ParserConfigurationException, SAXException {
        LinkedHashMap<String, String> stepProperties = new LinkedHashMap<>();
        stepProperties.put("status", "Failed");
        stepProperties.put("description", "Then google.results are displayed");
        stepProperties.put("actual", "Exception in thread \"main\" java.util.NoSuchElementException\n" +
                "at java.util.Scanner.throwFor(Scanner.java:838)\n" +
                "at java.util.Scanner.next(Scanner.java:1461)\n" +
                "at java.util.Scanner.nextInt(Scanner.java:2091)\n" +
                "at java.util.Scanner.nextInt(Scanner.java:2050)\n" +
                "at Addition.main(Addition.java:16)");
        stepProperties.put("attachment", System.getProperty("user.dir") + Paths.get("/src/test/resources/jpg-vs-jpeg-file-formats_1.jpg"));
        app.createTestStepAndStatus(stepProperties, "Step 3");
    }


    @Test
    public void getRunStepID() throws IOException, ParserConfigurationException, SAXException {
        assertEquals("1001;1002", app.getRunStepID(runStepResponseXML));
    }




    @Test
    public void setLoginCredentials() throws ConfigurationException {
        assertEquals("dGVzdC51c2VybmFtZTo=", app.setLoginCredentials());
    }


    @Test
    public void getTestSteps() throws ConfigurationException, ParserConfigurationException, SAXException, IOException {
        assertEquals("1001;1002", app.getTestSteps());
    }

    @Test
    public void createTestRun() throws ConfigurationException, ParserConfigurationException, SAXException, IOException {
        assertArrayEquals(new String[]{"1001", "1002"}, app.createTestRun());
    }


    @Test(expected = Test.None.class)
    public void getTestSet() throws IOException, ConfigurationException, ParserConfigurationException, SAXException {
        app.getTestSet("JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW;");
    }

    @Test
    public void getAttributeFromXMLResponse() throws ParserConfigurationException, SAXException, IOException {
        assertEquals("1001", app.getAttributeFromXMLResponse(runStepResponseXML, "id"));
    }

    @Test
    public void getAttributeFromXMLResponseWithInvalidAttribute() throws ParserConfigurationException, SAXException, IOException {
        assertEquals("", app.getAttributeFromXMLResponse(runStepResponseXML, "invalid_id"));
    }

    @Test(expected = Test.None.class)
    public void deleteAllTestSteps() throws ParserConfigurationException, SAXException, IOException, ConfigurationException {
        app.deleteAllTestSteps(new String[]{"1001", "1002"});
    }

    @Test(expected = Test.None.class)
    public void attachWithOctetStream() throws Exception {
        String filepath = System.getProperty("user.dir") + Paths.get("/src/test/resources/");
        app.attachWithOctetStream(filepath, "jpg-vs-jpeg-file-formats_1.jpg");

    }

    @Test(expected = Test.None.class)
    public void findTestSetFolderIDTest() throws IOException, ConfigurationException, ParserConfigurationException, SAXException {
        app.findTestSetFolderID();
    }

    @Test(expected = Test.None.class)
    public void createTestSetTest() throws ParserConfigurationException, SAXException, ConfigurationException, IOException {
        app.createTestSet();
    }


    @Test(expected = Test.None.class) //need to complete uploadattachment
    public void updateTestResult() throws Exception {
        String testCaseName = "Demo Test1";
        LinkedHashMap<String, LinkedHashMap<String, String>> steps = new LinkedHashMap<>();

        LinkedHashMap<String, String> stepProperties = new LinkedHashMap<>();
        stepProperties.put("status", "Passed");
        stepProperties.put("description", "Given I navigate to www.google.com");
        stepProperties.put("execution-time", "14:38:14");
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
        stepProperties.put("attachment", System.getProperty("user.dir") + Paths.get("/src/test/resources/jpg-vs-jpeg-file-formats_1.jpg"));
        steps.put("Step 3", stepProperties);

        stepProperties = new LinkedHashMap<>();
        stepProperties.put("status", "No Run");
        stepProperties.put("description", "Then google.logo is displayed");
        stepProperties.put("execution-date", "2009-01-27");
        steps.put("Step 4", stepProperties);

        stepProperties = new LinkedHashMap<>();
        stepProperties.put("status", "No Run");
        stepProperties.put("description", "Then I click google.home");
        steps.put("Step 5", stepProperties);

        LinkedHashMap<String, String> runProperties = new LinkedHashMap<>();
        runProperties.put("attachment", System.getProperty("user.dir") + Paths.get("/src/test/resources/jpg-vs-jpeg-file-formats_1.jpg"));
        runProperties.put("comments", "This is a comment");
        app.updateTestResult(testCaseName, steps, runProperties);
    }

    @Test(expected = Test.None.class)// need to complete uploadattachment
    public void executeApp() throws Exception {

        String testCaseName = "Demo Test1";

        LinkedHashMap<String, String> runProperties = new LinkedHashMap<>();
        // runProperties.put("attachment", "src/test/resources/jpg-vs-jpeg-file-formats_1.jpg;src/test/resources/jpg-vs-jpeg-file-formats_2.jpg");
        runProperties.put("attachment", System.getProperty("user.dir") + Paths.get("/src/test/resources/jpg-vs-jpeg-file-formats_1.jpg"));
        runProperties.put("comments", "This is a comment");

        LinkedHashMap<String, LinkedHashMap<String, String>> steps = new LinkedHashMap<>();

        LinkedHashMap<String, String> stepProperties = new LinkedHashMap<>();
        stepProperties.put("status", "Passed");
        stepProperties.put("description", "Given I navigate to www.google.com");
        stepProperties.put("execution-time", "14:38:14");
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
        // stepProperties.put("attachment", "src/test/resources/jpg-vs-jpeg-file-formats_1.jpg");
        stepProperties.put("attachment", System.getProperty("user.dir") + Paths.get("/src/test/resources/jpg-vs-jpeg-file-formats_1.jpg"));
        steps.put("Step 3", stepProperties);

        stepProperties = new LinkedHashMap<>();
        stepProperties.put("status", "No Run");
        stepProperties.put("description", "Then google.logo is displayed");
        stepProperties.put("execution-date", "2009-01-27");
        steps.put("Step 4", stepProperties);

        stepProperties = new LinkedHashMap<>();
        stepProperties.put("status", "No Run");
        stepProperties.put("description", "Then I click google.home");
        steps.put("Step 5", stepProperties);

        String resourcesPath = SystemUtils.getUserDir().toString();
        String configFilesRoot = FileSystems.getDefault().getSeparator() +
                "src" + FileSystems.getDefault().getSeparator() +
                "test" + FileSystems.getDefault().getSeparator() +
                "resources" + FileSystems.getDefault().getSeparator() +
                "testResources" + FileSystems.getDefault().getSeparator();

        app.updateTestCase(testCaseName, steps, runProperties, resourcesPath, configFilesRoot);
    }


    //Exceptions Test

    @Test(expected = HttpResponseException.class)
    public void authenticateExceptionTest() throws IOException, ConfigurationException {
        mockServer.stop();
        this.mockServer = startClientAndServer(8888);
        app.authenticate();
    }


    @Test(expected = HttpResponseException.class)
    public void updateTestResultExceptionTest() throws Exception {

        String testCaseName = "Demo Test1";
        LinkedHashMap<String, LinkedHashMap<String, String>> steps = new LinkedHashMap<>();

        LinkedHashMap<String, String> stepProperties = new LinkedHashMap<>();
        stepProperties.put("status", "Passed");
        stepProperties.put("description", "Given I navigate to www.google.com");
        stepProperties.put("execution-time", "14:38:14");
        steps.put("Step 1", stepProperties);

        LinkedHashMap<String, String> runProperties = new LinkedHashMap<>();
        runProperties.put("attachment", System.getProperty("user.dir") + Paths.get("/src/test/resources/jpg-vs-jpeg-file-formats_12.jpg"));
        app.updateTestResult(testCaseName, steps, runProperties);
    }


    @Test(expected = HttpResponseException.class)
    public void getTestSetExceptionTest() throws Exception {
        app.getTestSet("fakeHeader");
    }


    @Test(expected = HttpResponseException.class)
    public void createTestStepAndStatusExceptionTest() throws IOException, ConfigurationException, ParserConfigurationException, SAXException {
        LinkedHashMap<String, String> stepProperties = new LinkedHashMap<>();
        stepProperties.put("status", "Passed");
        stepProperties.put("description", "Given I navigate to www.google.com");
        stepProperties.put("execution-time", "14:38:14");
        app.createTestStepAndStatus(stepProperties, "Step1");
    }

    @Test(expected = HttpResponseException.class)
    public void getTestsByIDExceptionTest() throws Exception {
        app.getTestsById("1111");
    }

    @Test(expected = HttpResponseException.class)
    public void getTestsByNameExceptionTest() throws Exception {
        app.getTestsByName("Test123");
    }


    @Test(expected = HttpResponseException.class)
    public void deleteAllTestStepsExceptionTest() throws Exception {
        app.deleteAllTestSteps(new String[]{"1001", "2222"});

    }



    @Test(expected = HttpResponseException.class)
    public void attachWithOctetStreamExceptionTest() throws Exception {
        mockServer.stop();
        this.mockServer = startClientAndServer(8888);
        List<String> enc = new ArrayList<>();
        enc.add("gzip");
        List<String> auth = new ArrayList<>();
        auth.add("Basic dGVzdC51c2VybmFtZTo=");
        List<String> cookie = new ArrayList<>();
        cookie.add("JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW");
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
                                .withCookie("JSESSIONID", "2By8LOhBmaW5nZXJwcmludCIlMDAzMW")
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
                                .withCookie("JSESSIONID", "2By8LOhBmaW5nZXJwcmludCIlMDAzMW")

                );


        String filepath = System.getProperty("user.dir") + Paths.get("/src/test/resources/");
        app.attachWithOctetStream(filepath, "jpg-vs-jpeg-file-formats_1.jpg");


    }


    @Test(expected = HttpResponseException.class)
    public void findTestSetFolderIDExceptionTest() throws Exception {
        mockServer.stop();
        this.mockServer = startClientAndServer(8888);
        List<String> enc = new ArrayList<>();
        enc.add("gzip");
        List<String> auth = new ArrayList<>();
        auth.add("Basic dGVzdC51c2VybmFtZTo=");
        List<String> cookie = new ArrayList<>();
        cookie.add("JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW");
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
                                .withCookie("JSESSIONID", "2By8LOhBmaW5nZXJwcmludCIlMDAzMW")
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
                                .withCookie("JSESSIONID", "2By8LOhBmaW5nZXJwcmludCIlMDAzMW")

                );
        app.findTestSetFolderID();

    }


    @Test(expected = HttpResponseException.class)
    public void getTestStepsExceptionTest() throws Exception {
        mockServer.stop();
        this.mockServer = startClientAndServer(8888);
        List<String> enc = new ArrayList<>();
        enc.add("gzip");
        List<String> auth = new ArrayList<>();
        auth.add("Basic dGVzdC51c2VybmFtZTo=");
        List<String> cookie = new ArrayList<>();
        cookie.add("JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW");
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
                                .withCookie("JSESSIONID", "2By8LOhBmaW5nZXJwcmludCIlMDAzMW")
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
                                .withCookie("JSESSIONID", "2By8LOhBmaW5nZXJwcmludCIlMDAzMW")

                );
        app.getTestSteps();
    }

    @Test(expected = HttpResponseException.class)
    public void createTestRunExceptionTest() throws Exception {
        mockServer.stop();
        this.mockServer = startClientAndServer(8888);
        List<String> enc = new ArrayList<>();
        enc.add("gzip");
        List<String> auth = new ArrayList<>();
        auth.add("Basic dGVzdC51c2VybmFtZTo=");
        List<String> cookie = new ArrayList<>();
        cookie.add("JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW");
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
                                .withCookie("JSESSIONID", "2By8LOhBmaW5nZXJwcmludCIlMDAzMW")
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
                                .withCookie("JSESSIONID", "2By8LOhBmaW5nZXJwcmludCIlMDAzMW")

                );
        app.createTestRun();
    }

    @Test(expected = HttpResponseException.class)
    public void createTestSetExceptionTest() throws Exception {
        mockServer.stop();
        this.mockServer = startClientAndServer(8888);
        List<String> enc = new ArrayList<>();
        enc.add("gzip");
        List<String> auth = new ArrayList<>();
        auth.add("Basic dGVzdC51c2VybmFtZTo=");
        List<String> cookie = new ArrayList<>();
        cookie.add("JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW");
        List<String> connection = new ArrayList<>();
        connection.add("keep-alive");

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
                                .withCookie("JSESSIONID", "2By8LOhBmaW5nZXJwcmludCIlMDAzMW")
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
                                .withCookie("JSESSIONID", "2By8LOhBmaW5nZXJwcmludCIlMDAzMW")

                );
        //  findTestSetFolderIDTestMock1
        this.mockServer
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/qcbin")
                                .withQueryStringParameters(
                                        new Parameter("parent-id['-1']}", ""),
                                        new Parameter("login-form-required", "y/rest/domains/TRAINING/projects/ALM_Sample/test-set-folders/?query={name['Root']")
                                )
                                .withHeaders(
                                        new Header("cookie", "JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW;")
                                )
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeader(new Header("connection", connection))
                                .withBody("<Entities TotalResults=\"1\">\n" +
                                        "    <Entity Type=\"test-set-folder\">\n" +
                                        "        <Fields>\n" +
                                        "            <Field Name=\"id\">\n" +
                                        "                <Value>0</Value>\n" +
                                        "            </Field>\n" +
                                        "        </Fields>\n" +
                                        "    </Entity>\n" +
                                        "</Entities>")
                );

        //  findTestSetFolderIDTestMock2
        this.mockServer
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/qcbin")
                                .withQueryStringParameters(
                                        new Parameter("parent-id['0']}", ""),
                                        new Parameter("login-form-required", "y/rest/domains/TRAINING/projects/ALM_Sample/test-set-folders/?query={name['NEXTGEN_ETAF_DEMO']")
                                )
                                .withHeaders(
                                        new Header("cookie", "JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW;")
                                )
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeader(new Header("connection", connection))
                                .withBody("<Entities TotalResults=\"1\">\n" +
                                        "    <Entity Type=\"test-set-folder\">\n" +
                                        "        <Fields>\n" +
                                        "            <Field Name=\"id\">\n" +
                                        "                <Value>21</Value>\n" +
                                        "            </Field>\n" +
                                        "        </Fields>\n" +
                                        "    </Entity>\n" +
                                        "</Entities>")
                );

        app.createTestSet();
    }

    @Test(expected = HttpResponseException.class)
    public void createTestInstanceExceptionTest() throws Exception {
        mockServer.stop();
        this.mockServer = startClientAndServer(8888);
        List<String> enc = new ArrayList<>(); enc.add("gzip");
        List<String> auth = new ArrayList<>(); auth.add("Basic dGVzdC51c2VybmFtZTo=");
        List<String> cookie = new ArrayList<>(); cookie.add("JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW");
        List<String> connection = new ArrayList<>(); connection.add("keep-alive");
        List<String> contentLength = new ArrayList<>(); contentLength.add("0");

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
                                .withCookie("JSESSIONID", "2By8LOhBmaW5nZXJwcmludCIlMDAzMW")
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
                                .withCookie("JSESSIONID", "2By8LOhBmaW5nZXJwcmludCIlMDAzMW")

                );

        //getTestsById mock
        this.mockServer
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/qcbin")
                                .withQueryStringParameter("login-form-required", "y/rest/domains/TRAINING/projects/ALM_Sample/tests/2523")

                                .withHeaders(
                                        new Header("accept-encoding", enc),
                                        new Header("cookie", "JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW;")
                                )
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("content-length", contentLength),
                                        new Header("connection", connection)
                                )


                );

        //createTestInstance Get mock
        this.mockServer
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/qcbin")
                                .withQueryStringParameters(

                                        new Parameter("login-form-required", "y/rest/domains/TRAINING/projects/ALM_Sample/test-instances/?query={cycle-id[501]"),
                                        new Parameter("test-id[2523]}", "")
                                )
                                .withHeaders(
                                        new Header("cookie", "JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW;")
                                )
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(new Header("connection", connection))
                                .withBody("<Entities TotalResults=\"0\">\n" +
                                        "</Entities>")
                );

        app.createTestInstance("2523");


    }

}