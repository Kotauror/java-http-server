package handlersTests;

import httpserver.handlers.DirectoryListingHandler;
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

    private DirectoryListingHandler directoryListingHandler;
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
        directoryListingHandler = new DirectoryListingHandler(rootPath);
    }

    @Test
    public void returnsTrueWhenRequestPathIsRoot() {
        String path = "/";
        Request request = new Request(method, path, httpVersion, headers, body);

        assertTrue(directoryListingHandler.coversPathFromRequest(request));
    }

    @Test
    public void returnsFalseWhenRequestPathIsRoot() {
        String path = "/AnyOtherPath";
        Request request = new Request(method, path, httpVersion, headers, body);

        assertFalse(directoryListingHandler.coversPathFromRequest(request));
    }

    @Test
    public void returnsResponseWithStatus200() {
        String path = "/";
        Request request = new Request(method, path, httpVersion, headers, body);
        byte[] expectedBody = ("<html><head></head><body>" +
                "<a href='/testFile'>testFile</a><br>" +
                "<a href='/testFile.txt'>testFile.txt</a><br>" +
                "</body></html>").getBytes();

        Response response = directoryListingHandler.getResponse(request);

        assertArrayEquals(expectedBody, response.getBodyContent());
    }
}
