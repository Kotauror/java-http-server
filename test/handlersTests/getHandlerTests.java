package handlersTests;

import httpserver.Method;
import httpserver.handlers.GetHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;

import static junit.framework.Assert.assertEquals;

public class getHandlerTests {

    private GetHandler getHandler;
    private Request request;

    @Before
    public void setup() {
        getHandler = new GetHandler();
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
    public void createsAResponseWithStatus200() {
        Response response = getHandler.getResponse(request);
        assertEquals(ResponseStatus.NOT_FOUND, response.getStatus());
    }
}
