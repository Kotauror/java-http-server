package handlersTests;

import httpserver.handlers.RedirectHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;

import static junit.framework.Assert.assertEquals;

public class redirectHandlerTests {

    private RedirectHandler redirectHandler;

    @Before
    public void setup() {
        redirectHandler = new RedirectHandler();
    }

    @Test
    public void returnsResponseWIthStatus302() {
        String path = "/logs";
        String httpVersion = "HTTP/1.1";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {};
        Request request = new Request(Method.GET, path, httpVersion, headers, null);

        Response response = redirectHandler.processRequest(request);

        assertEquals(ResponseStatus.FOUND, response.getStatus());
    }
}
