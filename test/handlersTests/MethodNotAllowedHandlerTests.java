package handlersTests;

import httpserver.handlers.MethodNotAllowedHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;
import org.junit.Test;

import java.util.LinkedHashMap;

import static junit.framework.Assert.assertEquals;

public class MethodNotAllowedHandlerTests {

    @Test
    public void returnsStatusNotAllowed() {
        MethodNotAllowedHandler methodNotAllowedHandler = new MethodNotAllowedHandler();
        String path = "/testFile.txt";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>(){};
        Request request = new Request(Method.INVALID, path, null, headers, null);

        Response response = methodNotAllowedHandler.processRequest(request);

        assertEquals(ResponseStatus.NOT_ALLOWED, response.getStatus());
    }
}
