package handlersTests;

import httpserver.handlers.DirectoryListingHandler;
import httpserver.request.Request;
import httpserver.utilities.Method;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;

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
        directoryListingHandler = new DirectoryListingHandler();
    }

    @Test
    public void returnsTrueWhenRequestPathIsRoot() {
        String path = "/";
        Request request = new Request(method, path, httpVersion, headers, body);

        assertTrue(directoryListingHandler.coversPath(request));
    }

    @Test
    public void returnsFalseWhenRequestPathIsRoot() {
        String path = "/AnyOtherPath";
        Request request = new Request(method, path, httpVersion, headers, body);

        assertFalse(directoryListingHandler.coversPath(request));
    }
}
