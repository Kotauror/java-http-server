package handlersTests;

import httpserver.handlers.DirectoryLinksHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.utilities.Method;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class directoryListingHandlerTests {

    private DirectoryLinksHandler directoryLinksHandler;
    private Method method;
    private String httpVersion;
    private LinkedHashMap<String, String> headers;
    private String body;

    @Before
    public void setup() {
        httpVersion = "HTTP/1.1";
        headers = new LinkedHashMap<String, String>() {{
            put("Host", "localhost");
            put("Accept-Language", "en-US");
        }};
        body = "example body";
        method = Method.GET;
        String rootPath = "src/httpserver/utilities/sampleTestFiles";
        directoryLinksHandler = new DirectoryLinksHandler(rootPath);
    }

    @Test
    public void returnsTrueWhenRequestPathIsRoot() {
        String path = "/";
        Request request = new Request(method, path, httpVersion, headers, body);

        assertTrue(directoryLinksHandler.coversPathFromRequest(request));
    }

    @Test
    public void returnsFalseWhenRequestPathIsRoot() {
        String path = "/AnyOtherPath";
        Request request = new Request(method, path, httpVersion, headers, body);

        assertFalse(directoryLinksHandler.coversPathFromRequest(request));
    }

    @Test
    public void returnsResponseWithStatus200() {
        String path = "/";
        Request request = new Request(method, path, httpVersion, headers, body);
        byte[] expectedBody = ("<html><head></head><body>" +
                "<a href='/cat-form'>cat-form</a><br>"  +
                "<a href='/empty-form'>empty-form</a><br>"  +
                "<a href='/form-with-data'>form-with-data</a><br>"  +
                "<a href='/partial_content.txt'>partial_content.txt</a><br>"  +
                "<a href='/patch-content.txt'>patch-content.txt</a><br>"  +
                "<a href='/testFile.txt'>testFile.txt</a><br>" +
                "</body></html>").getBytes();

        Response response = directoryLinksHandler.processRequest(request);

        assertArrayEquals(expectedBody, response.getBodyContent());
    }
}
