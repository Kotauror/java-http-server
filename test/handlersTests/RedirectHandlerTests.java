package handlersTests;

import httpserver.handlers.RedirectHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.request.Method;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;

import static junit.framework.Assert.assertEquals;

public class RedirectHandlerTests {

    private RedirectHandler redirectHandler;

    @Before
    public void setup() {
        redirectHandler = new RedirectHandler();
    }

    @Test
    public void onPathLogs_ReturnsStatus302() {
        String path = "/logs";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {};
        Request request = new Request(Method.GET, path, null, headers, null);

        Response response = redirectHandler.processRequest(request);

        assertEquals(ResponseStatus.FOUND, response.getStatus());
        assertEquals("/", response.getHeaders().get("Location"));
    }
}
