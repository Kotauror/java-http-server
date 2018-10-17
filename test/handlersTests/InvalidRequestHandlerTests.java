package handlersTests;

import httpserver.handlers.InvalidRequestHandler;
import httpserver.handlers.RedirectHandler;
import httpserver.request.Request;
import httpserver.utilities.Method;
import org.junit.Before;
import org.junit.Test;

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
}
