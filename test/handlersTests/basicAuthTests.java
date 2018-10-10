package handlersTests;

import httpserver.handlers.BasicAuthHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;

public class basicAuthTests {

    private BasicAuthHandler basicAuthHandler;
    private String httpVersion;
    private String path;

    @Before
    public void setup() {
        basicAuthHandler = new BasicAuthHandler();
        path = "/logs";
        httpVersion = "HTTP/1.1";
    }

    @Test
    public void returnsResponseWithStatusOKWhenReqHasGetMethod() {
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("Host", "localhost");
            put("Accept-Language", "en-US");
            put("Authorization", "test");
        }};
        Request request = new Request(Method.GET, path, httpVersion, headers, null);
        Response response = basicAuthHandler.processRequest(request);

        assertEquals(response.getStatus(), ResponseStatus.OK);
    }

    @Test
    public void returnsResponseWithStatusUnauthorizedWhenNotAuthorized() {
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("Host", "localhost");
            put("Accept-Language", "en-US");
        }};
        Request request = new Request(Method.GET, path, httpVersion, headers, null);
        Response response = basicAuthHandler.processRequest(request);

        assertEquals(response.getStatus(), ResponseStatus.UNAUTHORIZED);
    }

    @Test
    public void returnsResponseWithStatusNotAllowedWhenReqHasMethodOtherThanGet() {
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("Host", "localhost");
            put("Accept-Language", "en-US");
            put("Authorization", "test");
        }};
        Request request = new Request(Method.PUT, path, httpVersion, headers, null);
        Response response = basicAuthHandler.processRequest(request);

        assertEquals(response.getStatus(), ResponseStatus.NOT_ALLOWED);
    }
}
