package requestTests;

import httpserver.request.RequestBuilder;
import httpserver.request.Method;
import httpserver.request.Request;
import httpserver.request.RequestParser;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.LinkedHashMap;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RequestParserTests {

    private RequestParser requestParser;

    @Before
    public void setup() {
        requestParser = new RequestParser(new RequestBuilder());
    }

    @Test
    public void parseRequestWithCompleteStartLineHeadersAndBody() {
        String requestString = "GET http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1\n" +
                "Host: 0.0.0.0:5000\n" +
                "Content-Length: 23\n\r\n" +
                "nomethod body\ntestbody";
        ByteArrayInputStream mockInputStream = new ByteArrayInputStream(requestString.getBytes());
        LinkedHashMap expectedHeaders = new LinkedHashMap<String, String>() {{
            put("Host", "0.0.0.0:5000");
            put("Content-Length", "23");
        }};

        Request request = requestParser.parse(mockInputStream);

        assertEquals(Method.GET, request.getMethod());
        assertEquals("http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages", request.getPath());
        assertEquals("HTTP/1.1", request.getHttpVersion());
        assertEquals(expectedHeaders, request.getHeaders());
        assertTrue(request.getBody().contains("nomethod body"));
        assertTrue(request.getBody().contains("testbody"));
    }

    @Test
    public void parseRequestWithOnlyMethodAndPath() {
        String requestString = "GET http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1\n";
        ByteArrayInputStream mockInputStream = new ByteArrayInputStream(requestString.getBytes());
        LinkedHashMap headers = new LinkedHashMap<String, String>();

        Request request = requestParser.parse(mockInputStream);

        assertEquals(Method.GET, request.getMethod());
        assertEquals("http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages", request.getPath());
        assertEquals("HTTP/1.1", request.getHttpVersion());
        assertEquals(headers, request.getHeaders());
        assertEquals(null, request.getBody());
    }

    @Test
    public void parseReturnsRequestWithMethodPathAndHeaders() {
        String requestString = "GET http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1\n" +
                "Host: 0.0.0.0:5000\n";
        ByteArrayInputStream mockInputStream = new ByteArrayInputStream(requestString.getBytes());
        LinkedHashMap headers = new LinkedHashMap<String, String>() {{
            put("Host", "0.0.0.0:5000");
        }};

        Request request = requestParser.parse(mockInputStream);

        assertEquals(Method.GET, request.getMethod());
        assertEquals("http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages", request.getPath());
        assertEquals("HTTP/1.1", request.getHttpVersion());
        assertEquals(headers, request.getHeaders());
        assertEquals(null, request.getBody());
    }

    @Test
    public void parseTestCaseFromFitnesse_SimpleGet() {
        String input = "GET /file1 HTTP/1.1\n" +
        "Host: localhost:5000\n" +
        "Connection: Keep-Alive\n" +
        "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
        "Accept-Encoding: gzip,deflate";
        ByteArrayInputStream mockStream1 = new ByteArrayInputStream(input.getBytes());
        LinkedHashMap headers = new LinkedHashMap<String, String>() {{
            put("Host", "localhost:5000");
            put("Connection", "Keep-Alive");
            put("User-Agent", "Apache-HttpClient/4.3.5 (java 1.5)");
            put("Accept-Encoding", "gzip,deflate");
        }};

        Request request = requestParser.parse(mockStream1);

        assertEquals(Method.GET, request.getMethod());
        assertEquals("/file1", request.getPath());
        assertEquals("HTTP/1.1", request.getHttpVersion());
        assertEquals(headers, request.getHeaders());
        assertEquals(null, request.getBody());
    }

    @Test
    public void parseTestCaseFromFitnesse_BogusMethod() {
        String input = "test bogus /file1 HTTP/1.1\n" +
                "Host: localhost:5000\n" +
                "Connection: Keep-Alive\n" +
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                "Accept-Encoding: gzip,deflate";
        ByteArrayInputStream mockStream1 = new ByteArrayInputStream(input.getBytes());

        Request request = requestParser.parse(mockStream1);

        assertEquals(Method.INVALID, request.getMethod());
    }

    @Test
    public void parseInvalidHeaderRequestToReturnBadRequest() {
        String input = "test bogus /file1 HTTP/1.1\n" +
                "Hostlocalhost:5000\n" +
                "ConnectionKeep-Alive\n" +
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                "Accept-Encoding: gzip,deflate";
        ByteArrayInputStream mockStream1 = new ByteArrayInputStream(input.getBytes());

        Request request = requestParser.parse(mockStream1);

        assertEquals("Error in parsing", request.getBody());
    }
}
