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
        input = "GET http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1" +
                "Host: 0.0.0.0:5000" +
                "Content-Length: 24" +
                "method body\r\nmethod body";
        headers = new HashMap<String, String>() {{
            put("Host", "0.0.0.0:5000");
            put("Content-Length", "24");
        }};
    }

    @Test
    public void returnsParsedRequestWithAllDataInIt() {
        Request request = requestParser.parse(input);

        assertEquals(Method.GET, request.getMethod());
        assertEquals("http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1", request.getPath());
        assertEquals(headers, request.getHeaders());
        assertEquals("method body\\r\\nmethod body", request.getBody());
    }
}
