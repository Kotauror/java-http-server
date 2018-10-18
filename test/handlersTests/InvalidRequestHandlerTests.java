package handlersTests;

import httpserver.handlers.InvalidRequestHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.request.Method;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InvalidRequestHandlerTests {

    private InvalidRequestHandler invalidRequestHandler;

    @Before
    public void setup() {
        invalidRequestHandler = new InvalidRequestHandler();
    }

    @Test
    public void handlesRequest_WhoseBodySaysErrorInParsingHeader() {
        Request request = new Request(Method.GET, null, null, null, "Error in parsing");

        assertTrue(invalidRequestHandler.handles(request));
    }

    @Test
    public void whenRequestHasErrorInParsingInBody_ReturnsStatus400() {
        Request request = new Request(Method.GET, null, null, null, "Error in parsing");

        Response response = invalidRequestHandler.processRequest(request);

        assertEquals(ResponseStatus.BAD_REQUEST, response.getStatus());
    }
}
