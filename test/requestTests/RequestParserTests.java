package requestTests;

import httpserver.Method;
import httpserver.request.Request;
import httpserver.request.RequestParser;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;

import static junit.framework.Assert.assertEquals;

public class RequestParserTests {

    private RequestParser requestParser;
    private LinkedHashMap<String, String> headers;
    private String path;
    private String httpVersion;
    private ByteArrayInputStream mockStream;

    @Before
    public void setup() {
        requestParser = new RequestParser();
        String requestString = "GET http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1\n" +
                "Host: 0.0.0.0:5000\n" +
                "Content-Length: 23\n\r\n" +
                "nomethod body\ntestbody";
        mockStream = new ByteArrayInputStream(requestString.getBytes());
        path = "http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages";
        httpVersion = "HTTP/1.1";
        headers = new LinkedHashMap<String, String>() {{
            put("Host", "0.0.0.0:5000");
            put("Content-Length", "23");
        }};
    }

    @Test
    public void parseReturnsRequestWithAllFieldsFilled() throws IOException {
        String body = "nomethod body\ntestbody";

        Request request = requestParser.parse(mockStream);

        assertEquals(Method.GET, request.getMethod());
        assertEquals(path, request.getPath());
        assertEquals(httpVersion, request.getHttpVersion());
        assertEquals(headers, request.getHeaders());
        assertEquals(body, request.getBody());
    }

    @Test
    public void parseReturnsRequestWithOnlyMethodAndPath() throws IOException {
        String requestString = "GET http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1\n";
        ByteArrayInputStream mockStream1 = new ByteArrayInputStream(requestString.getBytes());
        LinkedHashMap headers = new LinkedHashMap<String, String>();
        String body = "";

        Request request = requestParser.parse(mockStream1);

        assertEquals(Method.GET, request.getMethod());
        assertEquals(path, request.getPath());
        assertEquals(httpVersion, request.getHttpVersion());
        assertEquals(headers, request.getHeaders());
        assertEquals(body, request.getBody());
    }

    @Test
    public void parseReturnsRequestWithMethodPathAndHeaders() throws IOException {
        String requestString = "GET http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1\n" +
                "Host: 0.0.0.0:5000\n";
        ByteArrayInputStream mockStream1 = new ByteArrayInputStream(requestString.getBytes());
        String body = "";
        LinkedHashMap headers = new LinkedHashMap<String, String>() {{
            put("Host", "0.0.0.0:5000");
        }};

        Request request = requestParser.parse(mockStream1);

        assertEquals(Method.GET, request.getMethod());
        assertEquals(path, request.getPath());
        assertEquals(httpVersion, request.getHttpVersion());
        assertEquals(headers, request.getHeaders());
        assertEquals(body, request.getBody());
    }

    @Test
    public void testCaseFromFitnesseAPISimpleGet() throws IOException {
        String input = "GET /file1 HTTP/1.1\n" +
        "Host: localhost:5000\n" +
        "Connection: Keep-Alive\n" +
        "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
        "Accept-Encoding: gzip,deflate";
        LinkedHashMap headers = new LinkedHashMap<String, String>() {{
            put("Host", "localhost:5000");
            put("Connection", "Keep-Alive");
            put("User-Agent", "Apache-HttpClient/4.3.5 (java 1.5)");
            put("Accept-Encoding", "gzip,deflate");
        }};

        ByteArrayInputStream mockStream1 = new ByteArrayInputStream(input.getBytes());

        Request request = requestParser.parse(mockStream1);
        assertEquals(Method.GET, request.getMethod());
        assertEquals("/file1", request.getPath());
        assertEquals("HTTP/1.1", request.getHttpVersion());
        assertEquals(headers, request.getHeaders());
        assertEquals("", request.getBody());
    }
}
