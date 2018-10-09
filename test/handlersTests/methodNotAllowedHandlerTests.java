package handlersTests;

import httpserver.handlers.MethodNotAllowedHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;
import org.junit.Test;

import java.util.LinkedHashMap;

import static junit.framework.Assert.assertEquals;

public class methodNotAllowedHandlerTests {

    @Test
    public void returnsStatusNotAllowed() {
        MethodNotAllowedHandler methodNotAllowedHandler = new MethodNotAllowedHandler();
        String path = "/testFile.txt";
        String httpVersion = "HTTP/1.1";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("Host", "localhost");
            put("Accept-Language", "en-US");
        }};
        String body = "example body";
        Request request = new Request(Method.INVALID, path, httpVersion, headers, body);

        Response response = methodNotAllowedHandler.processRequest(request);

        assertEquals(ResponseStatus.NOT_ALLOWED, response.getStatus());
    }
}
