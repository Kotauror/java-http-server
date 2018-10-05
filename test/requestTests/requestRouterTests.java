package requestTests;

import httpserver.handlers.HandlerType;
import httpserver.utilities.Method;
import httpserver.handlers.Handler;
import httpserver.request.RequestRouter;
import httpserver.request.Request;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;

import static junit.framework.Assert.assertEquals;

public class requestRouterTests {

    private RequestRouter requestRouter;
    private Method method1;
    private Method method2;
    private String path;
    private LinkedHashMap<String, String> headers;
    private String body;
    private Method method3;
    private String httpVersion;
    private Method method4;

    @Before
    public void setup() {
        String rootPath = "/Users/justynazygmunt/Desktop/HttpServerFitnesse/cob_spec/public/";
        requestRouter = new RequestRouter(rootPath);
        method1 = Method.GET;
        method2 = Method.POST;
        method3 = Method.OPTIONS;
        method4 = Method.HEAD;
        path = "http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1";
        httpVersion = " HTTP/1.1";
        headers = new LinkedHashMap<String, String>() {{
            put("Host", "localhost");
            put("Accept-Language", "en-US");
        }};
        body = "example body";
    }

    @Test
    public void findHandlerReturnsRightHandlerForGet() {
        Request request = new Request(method1, path, httpVersion, headers, body);
        Handler handler = requestRouter.findHandler(request);

        assertEquals(HandlerType.GET_HANDLER, handler.getType());
    }

    @Test
    public void findHandlerReturnsRightHandlerForPost() {
        Request request = new Request(method2, path, httpVersion, headers, body);
        Handler handler = requestRouter.findHandler(request);

        assertEquals(HandlerType.POST_HANDLER, handler.getType());
    }

    @Test
    public void findHandlerReturnsRightHandlerForDirectoryListing() {
        Request request = new Request(method1, "/", httpVersion, headers, body);
        Handler handler = requestRouter.findHandler(request);

        assertEquals(HandlerType.DIRECTORY_LISTING_HANDLER, handler.getType());
    }

    @Test
    public void findHandlerReturnsRightHandlerForHead() {
        Request request = new Request(method4, "/", httpVersion, headers, body);
        Handler handler = requestRouter.findHandler(request);

        assertEquals(HandlerType.HEAD_HANDLER, handler.getType());
    }

    @Test
    public void returnsInternalErrorHandlerWjenThereIsNoOtherHandler() {
        Request request = new Request(method3, path, httpVersion, headers, body);
        Handler handler = requestRouter.findHandler(request);
        assertEquals(HandlerType.INTERNAL_ERROR_HANDLER, handler.getType());
    }
}
