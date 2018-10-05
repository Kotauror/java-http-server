package handlersTests;

import httpserver.handlers.PutHandler;
import httpserver.request.Request;
import httpserver.utilities.Method;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class putHandlerTests {

    private PutHandler putHandler;
    private Request request;

    @Before
    public void setup() {
        String rootPath = "src/httpserver/utilities/sampleTestFiles";
        putHandler = new PutHandler(rootPath);
    }

    @Test
    public void returnsTrueWhenPathIsCovered() {
        String path = "src/httpserver/utilities/testFile";
        String httpVersion = "HTTP/1.1";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("Host", "localhost");
            put("Accept-Language", "en-US");
        }};
        String body = "example body";
        request = new Request(Method.GET, path, httpVersion, headers, body);

        request = new Request(Method.GET, path, httpVersion, headers, body);
        assertTrue(putHandler.coversPathFromRequest(request));
    }

    @Test
    public void returnsFalseWhenPathIsNotCovered() {
        String path = "src/httpserver/utilities/testKoteczki/testFile";
        String httpVersion = "HTTP/1.1";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("Host", "localhost");
            put("Accept-Language", "en-US");
        }};
        String body = "example body";
        request = new Request(Method.GET, path, httpVersion, headers, body);

        request = new Request(Method.GET, path, httpVersion, headers, body);
        assertFalse(putHandler.coversPathFromRequest(request));
    }
}
