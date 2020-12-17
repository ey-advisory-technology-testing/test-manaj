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
import org.mockserver.model.Parameter;
import org.mockserver.model.Headers;
import org.mockserver.socket.PortFactory;
import org.mockserver.socket.tls.KeyStoreFactory;
import org.xml.sax.SAXException;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.*;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import java.nio.file.Paths;

public class AppTest {

    private ClientAndServer mockServer;
    private App app;

    @Before
    public void startMockServer() throws IOException {
        this.mockServer = startClientAndServer( 8888);
        List<String> enc = new ArrayList<>(); enc.add("gzip");
        List<String> auth = new ArrayList<>(); auth.add("Basic dGVzdC51c2VybmFtZTo=");
        List<String> cookie = new ArrayList<>(); cookie.add("JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW");
        List<String> contentLength = new ArrayList<>(); contentLength.add("0");
        List<String> connection = new ArrayList<>(); connection.add("keep-alive");
        List<String> paramValue2 = new ArrayList<>(); paramValue2.add("y/rest/domains/TRAINING/projects/ALM_Sample/test-sets/?query={name['2BrandNewTestSet3']");
        List<String> paramValue1 = new ArrayList<>(); paramValue1.add("");

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
                                .withPath("/qcbin/")
                                .withQueryStringParameter("login-form-required","y/rest/domains/TRAINING/projects/ALM_Sample/tests/1234")

                                .withHeaders(
                                        new Header("accept-encoding", enc),
                                        new Header("cookie", ";JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW")
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
                                .withPath("/qcbin/")
                                .withQueryStringParameters(

                                        new Parameter("login-form-required", "y/rest/domains/TRAINING/projects/ALM_Sample/tests/?query={name['test1']}")

                                )
                                .withHeaders(
                                        new Header("accept-encoding", enc),
                                        new Header("cookie", ";JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW")
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
                                        "                <Value>test1</Value>\n" +
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
                                        "                <Value>2222</Value>\n" +
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


 //getTestSet mock
        this.mockServer
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/qcbin/")
                                .withQueryStringParameters(

                                        new Parameter("parent-id[21]}", paramValue1),
                                        new Parameter("login-form-required", paramValue2)

                                )
                                .withHeaders(
                                        new Header("Accept-Encoding", enc),
                                        new Header("Cookie", ";JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW")
                                )
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header(CONTENT_TYPE.toString(), "application/xml"),
                                        new Header("connection", connection)
                                )
                                .withBody("<Entities TotalResults=\"2\">\n" +
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
                                        "                <Value>101</Value>\n" +
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
                                        "</Entities>")
                );

//updateTestRun Mock
        this.mockServer
                .when(
                        request()
                                .withMethod("PUT")
                                .withPath("/qcbin/")
                                .withQueryStringParameter("login-form-required","y/rest/domains/TRAINING/projects/ALM_Sample/runs/3532")
                                .withBody("<Entity Type=\"run\"><Fields><Field Name=\"subtype-id\"><Value>hp.qc.run.MANUAL</Value></Field><Field Name=\"name\"><Value>test case name</Value></Field><Field Name=\"test-instance\"><Value>1</Value></Field><Field Name=\"testcycl-id\"><Value>test cycle id</Value></Field><Field Name=\"cycle-id\"><Value>cycle id</Value></Field><Field Name=\"test-id\"><Value>test case id</Value></Field><Field Name=\"status\"><Value>status</Value></Field><Field Name=\"owner\"><Value>owner name</Value></Field></Fields></Entity>")
                                .withHeaders(
                                        new Header("accept-encoding", enc),
                                        new Header("cookie", ";JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW"),
                                        new Header(CONTENT_TYPE.toString(), "application/xml")

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



        //createTestStepAndStatus mock
        this.mockServer
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/qcbin/")
                                .withQueryStringParameter("login-form-required","y/rest/domains/TRAINING/projects/ALM_Sample/runs/3532/run-steps")
                                .withBody("<Entity Type=\"run-step\"><Fields><Field Name=\"name\"><Value>step1</Value></Field><Field Name=\"test-run-id\"><Value>test-run-id</Value></Field><Field Name=\"Testrunstep\"><Value>step id</Value></Field><Field Name=\"status\"><Value>pass</Value></Field><Field Name=\"description\"><Value>step description</Value></Field><Field Name=\"expected\"><Value>expected</Value></Field><Field Name=\"actual\"><Value>actual</Value></Field></Fields></Entity>")
                                .withHeaders(
                                        new Header("accept-encoding", enc),
                                        new Header("cookie", ";JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW"),
                                        new Header(CONTENT_TYPE.toString(), "application/xml")

                                )

                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("connection", connection)
                                )


                );




        //createTestStepAndStatusWithAttachment mock
        this.mockServer
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/qcbin/")
                                .withQueryStringParameter("login-form-required","y/rest/domains/TRAINING/projects/ALM_Sample/runs/3532/run-steps")
                                .withBody("<Entity Type=\"run-step\"><Fields><Field Name=\"name\"><Value>step1</Value></Field><Field Name=\"test-run-id\"><Value>test-run-id</Value></Field><Field Name=\"Testrunstep\"><Value>step id</Value></Field><Field Name=\"status\"><Value>pass</Value></Field><Field Name=\"description\"><Value>step description</Value></Field><Field Name=\"expected\"><Value>expected</Value></Field><Field Name=\"actual\"><Value>actual</Value></Field><Field Name=\"attachment\"><Value>Y</Value></Field></Fields></Entity>")
                                .withHeaders(
                                        new Header("accept-encoding", enc),
                                        new Header("cookie", ";JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW"),
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
                                        "    <Entity Type=\"run-step\">\n" +
                                        "        <ChildrenCount>\n" +
                                        "            <Value>0</Value>\n" +
                                        "        </ChildrenCount>\n" +
                                        "        <Fields>\n" +
                                        "            <Field Name=\"test-id\">\n" +
                                        "                <Value>2</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"comp-status\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"description\">\n" +
                                        "                <Value>&lt;html&gt;&lt;body&gt; \n" +
                                        "&lt;div align=&quot;left&quot; style=&quot;min-height:9pt&quot;&gt;\n" +
                                        "&lt;font face=&quot;Arial&quot;&gt;&lt;span dir=&quot;ltr&quot; style=&quot;font-size:8pt&quot;&gt;de&lt;/span&gt;&lt;/font&gt;\n" +
                                        "&lt;/div&gt;  \n" +
                                        "&lt;/body&gt;&lt;/html&gt;</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"rel-obj-id\"/>\n" +
                                        "            <Field Name=\"obj-id\"/>\n" +
                                        "            <Field Name=\"has-linkage\">\n" +
                                        "                <Value>N</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"execution-date\">\n" +
                                        "                <Value>2020-02-24</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"path\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"desstep-id\">\n" +
                                        "                <Value>1001</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"attachment\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"has-picture\">\n" +
                                        "                <Value>N</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"tree-parent-id\"/>\n" +
                                        "            <Field Name=\"id\">\n" +
                                        "                <Value>1001</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"component-data\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"bpt-path\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"actual\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"step-order\">\n" +
                                        "                <Value>1</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"level\"/>\n" +
                                        "            <Field Name=\"expected\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"line-no\"/>\n" +
                                        "            <Field Name=\"comp-subtype-name\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"extended-reference\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"name\">\n" +
                                        "                <Value>Step 1</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"execution-time\">\n" +
                                        "                <Value>14:13:40</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"bpta-condition\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"parent-id\">\n" +
                                        "                <Value>1</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"bpt-facet-type\">\n" +
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

        //getTestSteps mock

        this.mockServer
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/qcbin/")
                                .withQueryStringParameter("login-form-required","y/rest/domains/TRAINING/projects/ALM_Sample/runs/3532/run-steps")
                                .withHeaders(
                                        new Header("accept-encoding", enc),
                                        new Header("cookie", ";JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW")
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
                                        "    <Entity Type=\"run-step\">\n" +
                                        "        <ChildrenCount>\n" +
                                        "            <Value>0</Value>\n" +
                                        "        </ChildrenCount>\n" +
                                        "        <Fields>\n" +
                                        "            <Field Name=\"test-id\">\n" +
                                        "                <Value>2</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"comp-status\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"description\">\n" +
                                        "                <Value>&lt;html&gt;&lt;body&gt; \n" +
                                        "&lt;div align=&quot;left&quot; style=&quot;min-height:9pt&quot;&gt;\n" +
                                        "&lt;font face=&quot;Arial&quot;&gt;&lt;span dir=&quot;ltr&quot; style=&quot;font-size:8pt&quot;&gt;de&lt;/span&gt;&lt;/font&gt;\n" +
                                        "&lt;/div&gt;  \n" +
                                        "&lt;/body&gt;&lt;/html&gt;</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"rel-obj-id\"/>\n" +
                                        "            <Field Name=\"obj-id\"/>\n" +
                                        "            <Field Name=\"has-linkage\">\n" +
                                        "                <Value>N</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"execution-date\">\n" +
                                        "                <Value>2020-02-24</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"path\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"desstep-id\">\n" +
                                        "                <Value>1001</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"attachment\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"has-picture\">\n" +
                                        "                <Value>N</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"tree-parent-id\"/>\n" +
                                        "            <Field Name=\"id\">\n" +
                                        "                <Value>1001</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"component-data\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"bpt-path\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"actual\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"step-order\">\n" +
                                        "                <Value>1</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"level\"/>\n" +
                                        "            <Field Name=\"expected\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"line-no\"/>\n" +
                                        "            <Field Name=\"comp-subtype-name\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"extended-reference\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"name\">\n" +
                                        "                <Value>Step 1</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"execution-time\">\n" +
                                        "                <Value>14:13:40</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"bpta-condition\">\n" +
                                        "                <Value></Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"parent-id\">\n" +
                                        "                <Value>1</Value>\n" +
                                        "            </Field>\n" +
                                        "            <Field Name=\"bpt-facet-type\">\n" +
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



        //uploadAttachmentToStep mock
        this.mockServer
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/qcbin/")
                                .withQueryStringParameter("login-form-required","y/rest/domains/TRAINING/projects/ALM_Sample/run-steps/1001/attachments")

                                .withHeaders(
                                        new Header("accept-encoding", enc),
                                        new Header("cookie", ";JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW"),
                                        new Header(CONTENT_TYPE.toString(), "multipart/form-data; boundary=1608129522111")
                                )
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("connection", connection)
                                )


                );

        //CreateTestRun mock
        this.mockServer
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/qcbin/")
                                .withQueryStringParameter("login-form-required","y/rest/domains/TRAINING/projects/ALM_Sample/runs")
                                .withBody("<Entity Type=\"run\"><Fields><Field Name=\"test-id\"><Value>2523</Value></Field><Field Name=\"owner\"><Value>manjunath.purad</Value></Field><Field Name=\"subtype-id\"><Value>hp.qc.run.MANUAL</Value></Field><Field Name=\"cycle-id\"><Value>501</Value></Field><Field Name=\"test-instance\"><Value>1</Value></Field><Field Name=\"name\"><Value>Demo Test1</Value></Field><Field Name=\"testcycl-id\"><Value>1956</Value></Field><Field Name=\"status\"><Value>Not Completed</Value></Field></Fields></Entity>")

                                .withHeaders(
                                        new Header("cookie", ";JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW"),
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
                                        "                <Value>test1</Value>\n" +
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
                                        "                <Value>test1 [1]</Value>\n" +
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



    @Test(expected = Test.None.class)
    public void updateTestRun() throws IOException, ConfigurationException {
        LinkedHashMap<String, String> runProperties = new LinkedHashMap<>();
        runProperties.put("subtype-id", "hp.qc.run.MANUAL");
        runProperties.put("name", "test case name");
        runProperties.put("test-instance", "1");
        runProperties.put("testcycl-id", "test cycle id");
        runProperties.put("cycle-id", "cycle id");
        runProperties.put("test-id", "test case id");
        runProperties.put("status", "status");
        runProperties.put("owner", "owner name");
        app.updateTestRun(runProperties);
    }



    @Test(expected = Test.None.class)
    public void getTestsById() throws IOException, ConfigurationException {
        app.getTestsById("1234");
    }

    @Test(expected = Test.None.class)
    public void getTestsByName() throws ParserConfigurationException, SAXException, IOException, ConfigurationException {
        app.getTestsByName("test1");
    }



    @Test(expected = Test.None.class)
    public void createTestStepAndStatus() throws IOException, ConfigurationException, ParserConfigurationException, SAXException {
        LinkedHashMap<String, String> stepProperties = new LinkedHashMap<>();
        stepProperties.put("test-run-id", "test-run-id");
        stepProperties.put("Testrunstep", "step id");
        stepProperties.put("status", "pass");
        stepProperties.put("description", "step description");
        stepProperties.put("expected", "expected");
        stepProperties.put("actual","actual");
        app.createTestStepAndStatus(stepProperties, "step1");
    }


    @Test(expected = Test.None.class) //need to complete getRunStepID & uploadAttachmentToStep
    public void createTestStepAndStatusWithAttachment() throws IOException, ConfigurationException, ParserConfigurationException, SAXException {
        String filepath = System.getProperty("user.dir") + Paths.get("/src/test/resources/jpg-vs-jpeg-file-formats_1.jpg");
        LinkedHashMap<String, String> stepProperties = new LinkedHashMap<>();
        stepProperties.put("test-run-id", "test-run-id");
        stepProperties.put("Testrunstep", "step id");
        stepProperties.put("status", "pass");
        stepProperties.put("description", "step description");
        stepProperties.put("expected", "expected");
        stepProperties.put("actual","actual");
        stepProperties.put("attachment",filepath);
        app.createTestStepAndStatus(stepProperties, "step1");

    }
    @Test
    public void getRunStepID() throws IOException, ParserConfigurationException, SAXException {
        String responseXML = "<Entities TotalResults=\"1\">\n" +
                "    <Entity Type=\"run-step\">\n" +
                "        <ChildrenCount>\n" +
                "            <Value>0</Value>\n" +
                "        </ChildrenCount>\n" +
                "        <Fields>\n" +
                "            <Field Name=\"test-id\">\n" +
                "                <Value>2</Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"comp-status\">\n" +
                "                <Value></Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"description\">\n" +
                "                <Value>&lt;html&gt;&lt;body&gt; \n" +
                "&lt;div align=&quot;left&quot; style=&quot;min-height:9pt&quot;&gt;\n" +
                "&lt;font face=&quot;Arial&quot;&gt;&lt;span dir=&quot;ltr&quot; style=&quot;font-size:8pt&quot;&gt;de&lt;/span&gt;&lt;/font&gt;\n" +
                "&lt;/div&gt;  \n" +
                "&lt;/body&gt;&lt;/html&gt;</Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"rel-obj-id\"/>\n" +
                "            <Field Name=\"obj-id\"/>\n" +
                "            <Field Name=\"has-linkage\">\n" +
                "                <Value>N</Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"execution-date\">\n" +
                "                <Value>2020-02-24</Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"path\">\n" +
                "                <Value></Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"desstep-id\">\n" +
                "                <Value>1001</Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"attachment\">\n" +
                "                <Value></Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"has-picture\">\n" +
                "                <Value>N</Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"tree-parent-id\"/>\n" +
                "            <Field Name=\"id\">\n" +
                "                <Value>1001</Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"component-data\">\n" +
                "                <Value></Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"bpt-path\">\n" +
                "                <Value></Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"actual\">\n" +
                "                <Value></Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"step-order\">\n" +
                "                <Value>1</Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"level\"/>\n" +
                "            <Field Name=\"expected\">\n" +
                "                <Value></Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"line-no\"/>\n" +
                "            <Field Name=\"comp-subtype-name\">\n" +
                "                <Value></Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"extended-reference\">\n" +
                "                <Value></Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"name\">\n" +
                "                <Value>Step 1</Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"execution-time\">\n" +
                "                <Value>14:13:40</Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"bpta-condition\">\n" +
                "                <Value></Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"parent-id\">\n" +
                "                <Value>1</Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"bpt-facet-type\">\n" +
                "                <Value></Value>\n" +
                "            </Field>\n" +
                "            <Field Name=\"status\">\n" +
                "                <Value>Passed</Value>\n" +
                "            </Field>\n" +
                "        </Fields>\n" +
                "        <RelatedEntities/>\n" +
                "    </Entity>\n" +
                "    <singleElementCollection>false</singleElementCollection>\n" +
                "</Entities>";
        assertEquals("1001",app.getRunStepID(responseXML));

    }

    /*@Test(expected = Test.None.class) //404 error
    public void uploadAttachmentToStep() throws IOException, ConfigurationException, ParserConfigurationException, SAXException {
        String filepath = System.getProperty("user.dir") + Paths.get("/src/test/resources/");
        app.uploadAttachmentToStep(filepath, "jpg-vs-jpeg-file-formats_1.jpg", "1001");

    }*/




    @Test
    public void setLoginCredentials() throws ConfigurationException {
        assertEquals("dGVzdC51c2VybmFtZTo=", app.setLoginCredentials());
    }


//    @Test // need to complete getTestSteps
//    public void createTestRun() throws ConfigurationException, ParserConfigurationException, SAXException, IOException {
//        assertEquals("test", app.createTestRun());
//    }
//
//    @Test
//    public void getTestSteps() throws ConfigurationException, ParserConfigurationException, SAXException, IOException {
//        assertEquals("test", app.getTestSteps());
//    }


    @Test (expected = Test.None.class)
    public void getTestSet() throws IOException, ConfigurationException, ParserConfigurationException, SAXException {
        app.getTestSet(";JSESSIONID=2By8LOhBmaW5nZXJwcmludCIlMDAzMW");
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

