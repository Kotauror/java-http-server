package requestTests;

import httpserver.handlers.HandlerType;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;
import httpserver.handlers.Handler;
import httpserver.request.RequestRouter;
import httpserver.request.Request;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedHashMap;

import static junit.framework.Assert.assertEquals;

public class requestRouterTests {

    private RequestRouter requestRouter;
    private Method methodGet;
    private Method methodPost;
    private String path;
    private LinkedHashMap<String, String> headers;
    private String body;
    private Method methodOption;
    private String httpVersion;
    private Method methodHead;
    private Method methodPut;
    private Method methodDelete;

    @Before
    public void setup() {
        String rootPath = "/Users/justynazygmunt/Desktop/HttpServerFitnesse/cob_spec/public/";
        requestRouter = new RequestRouter(rootPath);
        methodGet = Method.GET;
        methodPost = Method.POST;
        methodOption = Method.OPTIONS;
        methodHead = Method.HEAD;
        methodPut = Method.PUT;
        methodDelete = Method.DELETE;
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
        Request request = new Request(methodGet, path, httpVersion, headers, body);
        Handler handler = requestRouter.findHandler(request);

        assertEquals(HandlerType.GET_HANDLER, handler.getType());
    }

    @Test
    public void findHandlerReturnsRightHandlerForPost() {
        Request request = new Request(methodPost, path, httpVersion, headers, body);
        Handler handler = requestRouter.findHandler(request);

        assertEquals(HandlerType.POST_HANDLER, handler.getType());
    }

    @Test
    public void findHandlerReturnsRightHandlerForDirectoryListing() {
        Request request = new Request(methodGet, "/", httpVersion, headers, body);
        Handler handler = requestRouter.findHandler(request);

        assertEquals(HandlerType.DIRECTORY_LISTING_HANDLER, handler.getType());
    }

    @Test
    public void findHandlerReturnsRightHandlerForHead() {
        Request request = new Request(methodHead, "/", httpVersion, headers, body);
        Handler handler = requestRouter.findHandler(request);

        assertEquals(HandlerType.HEAD_HANDLER, handler.getType());
    }

    @Test
    public void findHandlerReturnsRightHandlerForPut() {
        Request request = new Request(methodPut, "/", httpVersion, headers, body);
        Handler handler = requestRouter.findHandler(request);

        assertEquals(HandlerType.PUT_HANDLER, handler.getType());
    }

    @Test
    public void findHandlerReturnsRightHandlerForMethodNotAllowed() {
        Request request = new Request(Method.INVALID, "/", httpVersion, headers, body);
        Handler handler = requestRouter.findHandler(request);

        assertEquals(HandlerType.NOT_ALLOWED_HANDLER, handler.getType());
    }

    @Test
    public void findHandlerReturnsRightHandlerForDeleteHandler() {
        Request request = new Request(methodDelete, "/", httpVersion, headers, body);
        Handler handler = requestRouter.findHandler(request);

        assertEquals(HandlerType.DELETE_HANDLER, handler.getType());
    }

    @Test
    public void returnsGetWith404StatusWhenThereIsNoAppropriateHandler() throws IOException {
        Request request = new Request(methodOption, path, httpVersion, headers, body);
        Handler handler = requestRouter.findHandler(request);

        Response response = handler.processRequest(request);
        ResponseStatus responseStatus = response.getStatus();

        assertEquals(HandlerType.NOT_ALLOWED_HANDLER, handler.getType());
        assertEquals(ResponseStatus.NOT_ALLOWED, responseStatus);
    }
}
