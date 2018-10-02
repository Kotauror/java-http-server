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
    private String path;

    @Before
    public void setup() {
        requestParser = new RequestParser();
        input = "GET http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1\n" +
                "Host: 0.0.0.0:5000\n" +
                "Content-Length: 24\n\r\n" +
                "nomethod body\ntestbody";
        path = "http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1";
        headers = new LinkedHashMap<String, String>() {{
            put("Host", "0.0.0.0:5000");
            put("Content-Length", "24");
        }};
    }

    @Test
    public void parseReturnsRequestWithAllFieldsFilled() {
        String body = "nomethod body\ntestbody";

        Request request = requestParser.parse(input);

        assertEquals(Method.GET, request.getMethod());
        assertEquals(path, request.getPath());
        assertEquals(headers, request.getHeaders());
        assertEquals(body, request.getBody());
    }

    @Test
    public void parseReturnsRequestWithOnlyMethodAndPath() {
        String input = "GET http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1\n";
        LinkedHashMap headers = new LinkedHashMap<String, String>();
        String body = "";

        Request request = requestParser.parse(input);

        assertEquals(Method.GET, request.getMethod());
        assertEquals(path, request.getPath());
        assertEquals(headers, request.getHeaders());
        assertEquals(body, request.getBody());
    }

    @Test
    public void parseReturnsRequestWithMethodPathAndHeaders() {
        String input = "GET http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1\n" +
                "Host: 0.0.0.0:5000\n" +
                "Content-Length: 24\n";
        String body = "";

        Request request = requestParser.parse(input);

        assertEquals(Method.GET, request.getMethod());
        assertEquals(path, request.getPath());
        assertEquals(headers, request.getHeaders());
        assertEquals(body, request.getBody());
    }
}
