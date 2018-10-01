import httpserver.Method;
import httpserver.request.Request;
import httpserver.request.RequestParser;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static junit.framework.Assert.assertEquals;

public class RequestParserTests {

    private RequestParser requestParser;
    private String input;
    private HashMap<String, String> headers;

    @Before
    public void setup() {
        requestParser = new RequestParser();
        input = "GET http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1\n" +
                "Host: 0.0.0.0:5000'\n" +
                "Content-Length: 24\n" +
                "method body\r\nmethod body\n";
        headers = new HashMap<String, String>() {{
            put("Host", "0.0.0.0:5000");
            put("Content-Length", "24");
        }};
    }

    @Test
    public void getMethodReturnsRightMethod() {
        assertEquals(Method.GET, requestParser.getMethod("GET http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1"));
        assertEquals(Method.OPTIONS, requestParser.getMethod("OPTIONS http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1"));
        assertEquals(Method.POST, requestParser.getMethod("POST http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1"));
    }

    @Test
    public void getPathReturnsRightPath() {
        String path = "http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1";
        assertEquals(path, requestParser.getPath("GET http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1\n"));
    }
}
