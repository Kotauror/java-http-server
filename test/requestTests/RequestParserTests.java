package requestTests;

import httpserver.Method;
import httpserver.request.Request;
import httpserver.request.RequestParser;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;

import static junit.framework.Assert.assertEquals;

public class RequestParserTests {

    private RequestParser requestParser;
    private String input;
    private LinkedHashMap<String, String> headers;

    @Before
    public void setup() {
        requestParser = new RequestParser();
        input = "GET http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1\n" +
                "Host: 0.0.0.0:5000\n" +
                "Content-Length: 24\n\r\n" +
                "nomethod body\ntestbody";
        headers = new LinkedHashMap<String, String>() {{
            put("Host", "0.0.0.0:5000");
            put("Content-Length", "24");
        }};
    }

    @Test
    public void getMethodReturnsRightMethod() {
        assertEquals(Method.GET, requestParser.getMethod("GET http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1 test\n test"));
        assertEquals(Method.OPTIONS, requestParser.getMethod("OPTIONS http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1\n test"));
        assertEquals(Method.POST, requestParser.getMethod("POST http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1\n test"));
    }

    @Test
    public void getPathReturnsRightPath() {
        String ExpectedPath = "http://developer.mozilla.org/en-US/HTTP/Messages HTTP/1.1";
        assertEquals(ExpectedPath, requestParser.getPath("GET http://developer.mozilla.org/en-US/HTTP/Messages HTTP/1.1\n test \n testing new lines"));
    }

    @Test
    public void getHeadersReturnsRightHeaders() {
        assertEquals(headers, requestParser.getHeaders(input));
    }

    @Test
    public void getBodyReturnsRightBody() {
        assertEquals("nomethod body\ntestbody", requestParser.getBody(input));
    }

    @Test
    public void parseReturnsRequest() {
        String path = "http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1";
        String body = "nomethod body\ntestbody";

        Request request = requestParser.parse(input);

        assertEquals(Method.GET, request.getMethod());
        assertEquals(path, request.getPath());
        assertEquals(headers, request.getHeaders());
        assertEquals(body, request.getBody());
    }
}
