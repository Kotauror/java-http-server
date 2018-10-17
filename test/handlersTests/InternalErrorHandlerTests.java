package handlersTests;

import httpserver.handlers.InternalErrorHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InternalErrorHandlerTests {

    private InternalErrorHandler internalErrorHandler;

    @Before
    public void setup() {
        internalErrorHandler = new InternalErrorHandler();
    }

    @Test
    public void handlesRequest_WhoseBodySaysErrorInBufferingHeader() {
        Request request = new Request(Method.GET, null, null, null, "Error in buffering");

        assertTrue(internalErrorHandler.handles(request));
    }

    @Test
    public void whenRequestHasErrorInBufferingInBody_ReturnsStatus500() {
        Request request = new Request(Method.GET, null, null, null, "Error in buffering");

        Response response = internalErrorHandler.processRequest(request);

        assertEquals(ResponseStatus.INTERNAL_SERVER_ERROR, response.getStatus());
    }
}
