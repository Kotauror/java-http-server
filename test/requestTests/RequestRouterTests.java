package requestTests;

import httpserver.handlers.HandlerType;
import httpserver.server.Logger;
import httpserver.request.Method;
import httpserver.handlers.Handler;
import httpserver.request.RequestRouter;
import httpserver.request.Request;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;

import static junit.framework.Assert.assertEquals;

public class RequestRouterTests {

    private RequestRouter requestRouter;
    private LinkedHashMap<String, String> headers;

    @Before
    public void setup() {
        String rootPath = "test/sampleTestFiles";
        Logger logger = new Logger();
        requestRouter = new RequestRouter(rootPath, logger);
        headers = new LinkedHashMap<String, String>() {{
            put("Host", "localhost");
            put("Accept-Language", "en-US");
        }};
    }

    @Test
    public void findHandlerReturnsRightHandlerForGet() {
        String path = "http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1";
        Request request = new Request(Method.GET, path, null, headers, null);
        Handler handler = requestRouter.findHandler(request);

        assertEquals(HandlerType.GET_HANDLER, handler.getTypeOfHandler());
    }

    @Test
    public void findHandlerReturnsRightHandlerForPost() {
        String path = "http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1";
        Request request = new Request(Method.POST, path, null, headers, null);
        Handler handler = requestRouter.findHandler(request);

        assertEquals(HandlerType.POST_HANDLER, handler.getTypeOfHandler());
    }

    @Test
    public void findHandlerReturnsRightHandlerForDirectoryListing() {
        Request request = new Request(Method.GET, "/", null, headers, null);
        Handler handler = requestRouter.findHandler(request);

        assertEquals(HandlerType.DIRECTORY_LISTING_HANDLER, handler.getTypeOfHandler());
    }

    @Test
    public void findHandlerReturnsRightHandlerForHead() {
        Request request = new Request(Method.HEAD, "/", null, headers, null);
        Handler handler = requestRouter.findHandler(request);

        assertEquals(HandlerType.HEAD_HANDLER, handler.getTypeOfHandler());
    }

    @Test
    public void findHandlerReturnsRightHandlerForPut() {
        Request request = new Request(Method.PUT, "/", null, headers, null);
        Handler handler = requestRouter.findHandler(request);

        assertEquals(HandlerType.PUT_HANDLER, handler.getTypeOfHandler());
    }

    @Test
    public void findHandlerReturnsRightHandlerForMethodNotAllowed() {
        Request request = new Request(Method.INVALID, "/", null, headers, null);
        Handler handler = requestRouter.findHandler(request);

        assertEquals(HandlerType.NOT_ALLOWED_HANDLER, handler.getTypeOfHandler());
    }

    @Test
    public void findHandlerReturnsRightHandlerForDeleteHandler() {
        Request request = new Request(Method.DELETE, "/", null, headers, null);
        Handler handler = requestRouter.findHandler(request);

        assertEquals(HandlerType.DELETE_HANDLER, handler.getTypeOfHandler());
    }

    @Test
    public void findHandlerReturnsRightHandlerForOptionHandler() {
        Request request = new Request(Method.OPTIONS, "/", null, headers, null);
        Handler handler = requestRouter.findHandler(request);

        assertEquals(HandlerType.OPTION_HANDLER, handler.getTypeOfHandler());
    }

    @Test
    public void findHandlerReturnsRightHandlerForBasicAuthHandler() {
        Request request = new Request(Method.GET, "/logs", null, headers, null);
        Handler handler = requestRouter.findHandler(request);

        assertEquals(HandlerType.BASIC_AUTH_HANDLER, handler.getTypeOfHandler());
    }

    @Test
    public void findHandlerReturnsRightHandlerForCookieHandler() {
        Request request = new Request(Method.GET, "/cookie?type=chocolate", null, headers, null);
        Handler handler = requestRouter.findHandler(request);

        assertEquals(HandlerType.COOKIE_HANDLER, handler.getTypeOfHandler());
    }

    @Test
    public void findHandlerReturnsRightHandlerForTeapotHandlerWhenCoffee() {
        Request request = new Request(Method.GET, "/coffee", null, headers, null);
        Handler handler = requestRouter.findHandler(request);

        assertEquals(HandlerType.TEAPOT_HANDLER, handler.getTypeOfHandler());
    }

    @Test
    public void findHandlerReturnsRightHandlerForTeapotHandlerWhenTea() {
        Request request = new Request(Method.GET, "/tea", null, headers, null);
        Handler handler = requestRouter.findHandler(request);

        assertEquals(HandlerType.TEAPOT_HANDLER, handler.getTypeOfHandler());
    }

    @Test
    public void findHandlerReturnsRightHandlerForRedirectHandler() {
        Request request = new Request(Method.GET, "/redirect", null, headers, null);
        Handler handler = requestRouter.findHandler(request);

        assertEquals(HandlerType.REDIRECT_HANDLER, handler.getTypeOfHandler());
    }

    @Test
    public void findHandlerReturnsRightHandlerForFormHandlerWithGet() {
        Request request = new Request(Method.GET, "/cat-form/data", null, headers, null);
        Handler handler = requestRouter.findHandler(request);

        assertEquals(HandlerType.FORM_HANDLER, handler.getTypeOfHandler());
    }

    @Test
    public void findHandlerReturnsRightHandlerForFormHandlerWithDelete() {
        Request request = new Request(Method.DELETE, "/cat-form/data", null, headers, null);
        Handler handler = requestRouter.findHandler(request);

        assertEquals(HandlerType.FORM_HANDLER, handler.getTypeOfHandler());
    }

    @Test
    public void findHandlerReturnsRightHandlerForHandlerWithParameters() {
        Request request = new Request(Method.GET, "/parameters?variable_1=a%20query%20string%20parameter", null, headers, null);
        Handler handler = requestRouter.findHandler(request);

        assertEquals(HandlerType.PARAMETERS_HANDLER, handler.getTypeOfHandler());
    }

    @Test
    public void findHandlerReturnsRightHandlerForPatch() {
        Request request = new Request(Method.PATCH, "/patch-content", null, headers, null);
        Handler handler = requestRouter.findHandler(request);

        assertEquals(HandlerType.PATCH_HANDLER, handler.getTypeOfHandler());
    }

    @Test
    public void whenRequestComesWithErrorInParsingInBodyReturnInvalidRequestHandler() {
        Request request = new Request(null, null, null, null, "Error in parsing");
        Handler handler = requestRouter.findHandler(request);

        assertEquals(HandlerType.INVALID_REQUEST_HANDLER, handler.getTypeOfHandler());
    }

    @Test
    public void whenRequestComesWithErrorInBufferingInBodyReturnInternalErrorHandler() {
        Request request = new Request(null, null, null, null, "Error in buffering");
        Handler handler = requestRouter.findHandler(request);

        assertEquals(HandlerType.INTERNAL_ERROR_HANDLER, handler.getTypeOfHandler());
    }
}
