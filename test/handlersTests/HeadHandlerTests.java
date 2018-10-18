package handlersTests;

import httpserver.handlers.HeadHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class HeadHandlerTests {

    private HeadHandler headHandler;
    private Request request;

    @Before
    public void setup() {
        String rootPath = "test/sampleTestFiles";
        headHandler = new HeadHandler(rootPath);
        String path = "/";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>(){};
        request = new Request(Method.GET, path, null, headers, null);
    }

    @Test
    public void returnsResponseWithStatus200() {
        Response response = headHandler.processRequest(request);
        assertEquals(ResponseStatus.OK, response.getStatus());
    }

    @Test
    public void returnsResponseWithEmptyBody() {
        Response response = headHandler.processRequest(request);
        assertNull(response.getBodyContent());
    }

    @Test
    public void returnsResponseWithEmptyHeaders() {
        Response response = headHandler.processRequest(request);
        assertTrue(response.getHeaders().isEmpty());
    }
}
