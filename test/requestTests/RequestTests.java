package requestTests;

import httpserver.utilities.Method;
import httpserver.request.Request;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;

import static junit.framework.Assert.assertEquals;

public class RequestTests {

    Request request;

    @Before
    public void setup() {
        String path = "http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages";
        String httpVersion = "HTTP/1.1";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("Host", "localhost");
            put("Accept-Language", "en-US");
        }};
        String body = "example body";

        request = new Request(Method.GET, path, httpVersion, headers, body);
    }

    @Test
    public void returnsMethod() {
        assertEquals(Method.GET, request.getMethod());
    }

    @Test
    public void returnsPath() {
        assertEquals("http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages", request.getPath());
    }

    @Test
    public void returnsHttpVersion() {
        assertEquals("HTTP/1.1", request.getHttpVersion());
    }

    @Test
    public void returnsHeaders() {
        HashMap<String, String> expected = new HashMap<String, String>() {{
            put("Host", "localhost");
            put("Accept-Language", "en-US");
        }};
        assertEquals(expected, request.getHeaders());
    }

    @Test
    public void returnsBody() {
        assertEquals("example body", request.getBody());
    }
}
