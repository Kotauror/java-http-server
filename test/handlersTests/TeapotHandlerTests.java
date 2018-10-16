package handlersTests;

import httpserver.handlers.TeapotHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class TeapotHandlerTests {

    private TeapotHandler teapotHandler;

    @Before
    public void setup() {
        teapotHandler = new TeapotHandler();
    }

    @Test
    public void returns418WHenPathEqualsCoffee() {
        String httpVersion = "HTTP/1.1";
        LinkedHashMap<String, String> headers = new LinkedHashMap<>();
        String path = "/coffee";
        Request request = new Request(Method.GET, path, httpVersion, headers, null);
        byte[] expectedBody = "I'm a teapot".getBytes();
        Response response = teapotHandler.processRequest(request);

        assertEquals(ResponseStatus.TEAPOT, response.getStatus());
        assertArrayEquals(expectedBody, response.getBodyContent());
    }

    @Test
    public void returns418WHenPathEqualsTee() {
        String httpVersion = "HTTP/1.1";
        LinkedHashMap<String, String> headers = new LinkedHashMap<>();
        String path = "/tea";
        Request request = new Request(Method.GET, path, httpVersion, headers, null);

        Response response = teapotHandler.processRequest(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
    }
}
