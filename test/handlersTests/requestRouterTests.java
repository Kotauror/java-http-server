package handlersTests;

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

    @Before
    public void setup() {
        String rootPath = "/Users/justynazygmunt/Desktop/HttpServerFitnesse/cob_spec/public/";
        requestRouter = new RequestRouter(rootPath);
        method1 = Method.GET;
        method2 = Method.POST;
        method3 = Method.OPTIONS;
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
        Request request1 = new Request(method1, path, httpVersion, headers, body);
        Handler handler = requestRouter.findHandler(request1);

        assertEquals("getHandler", handler.getType());
    }

    @Test
    public void findHandlerReturnsRightHandler() {
        Request request2 = new Request(method2, path, httpVersion, headers, body);
        Handler handler = requestRouter.findHandler(request2);

        assertEquals("postHandler", handler.getType());
    }

    @Test
    public void returnsNullWHenThereIsNoHandler() {
        Request request3 = new Request(method3, path, httpVersion, headers, body);

        assertEquals(null, requestRouter.findHandler(request3));
    }
}
