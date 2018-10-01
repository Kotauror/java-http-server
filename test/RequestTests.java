import httpserver.Method;
import httpserver.request.Request;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static junit.framework.Assert.assertEquals;

public class RequestTests {

    Request request;

    @Before
    public void setup() {
//        String method = "GET";
        String path = "http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1";
        HashMap<String, String> headers = new HashMap<String, String>() {{
            put("Host", "localhost");
            put("Accept-Language", "en-US");
        }};
        String body = "example body";

        request = new Request(Method.GET, path, headers, body);
    }

    @Test
    public void returnsMethod() {
        assertEquals(Method.GET, request.getMethod());
    }

    @Test
    public void returnsPath() {
        assertEquals("http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1", request.getPath());
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
