package handlersTests;

import httpserver.handlers.BasicAuthHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;

import static org.junit.Assert.assertTrue;

public class basicAuthTests {

    private BasicAuthHandler basicAuthHandler;
    private String httpVersion;
    private String path;
    private LinkedHashMap<String, String> headers;

    @Before
    public void setup() {
        basicAuthHandler = new BasicAuthHandler();
        path = "/logs";
        httpVersion = "HTTP/1.1";
        headers = new LinkedHashMap<String, String>() {{
            put("Host", "localhost");
            put("Accept-Language", "en-US");
        }};
    }

    @Test
    public void returnsResponseWithStatusOKWhenReqHasGetMethod() {
        Request request = new Request(Method.GET, path, httpVersion, headers, null);
        Response response = basicAuthHandler.processRequest(request);

        assertTrue(response.getStatus().equals(ResponseStatus.OK));
    }

    @Test
    public void returnsResponseWithStatusNotAllowedWhenReqHasMethodOtherThanGet() {
        Request request = new Request(Method.PUT, path, httpVersion, headers, null);
        Response response = basicAuthHandler.processRequest(request);

        assertTrue(response.getStatus().equals(ResponseStatus.NOT_ALLOWED));
    }
}
