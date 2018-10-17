package handlersTests;

import httpserver.handlers.InternalErrorHandler;
import httpserver.request.Request;
import httpserver.utilities.Method;
import org.junit.Before;
import org.junit.Test;

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
}
