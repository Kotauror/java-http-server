package handlersTests;

import httpserver.handlers.BasicAuthHandler;
import httpserver.request.Request;
import httpserver.response.ResponseHeader;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.server.Logger;
import httpserver.request.Method;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class BasicAuthTests {

    private BasicAuthHandler basicAuthHandler;
    private String path;

    @Before
    public void setup() {
        Logger logger = new Logger();
        basicAuthHandler = new BasicAuthHandler(logger);
        logger.addNewSocketLog("port fake info");
        path = "/logs";
    }

    @Test
    public void returnsResponseWIthStatusOKKAndLogs_WhenRequestWithValidCredentials() {
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("Authorization", "Basic YWRtaW46aHVudGVyMg==");
        }};
        Request request = new Request(Method.GET, path, null, headers, null);
        String bodyString = "GET /logs HTTP/1.1 PUT /these HTTP/1.1 HEAD /requests HTTP/1.1\n" +
                "NEW_SOCKET: port fake info\n";
        byte [] expectedBody = bodyString.getBytes();

        Response response = basicAuthHandler.processRequest(request);

        assertEquals(response.getStatus(), ResponseStatus.OK);
        assertArrayEquals(expectedBody, response.getBodyContent());
    }

    @Test
    public void returnsResponseWithStatusNotAllowedWhen_RequestHasWrongCredentials() {
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("Authorization", "Basic YWRtaW46aHVudTestTestGVyMg==");
        }};
        Request request = new Request(Method.GET, path, null, headers, null);

        Response response = basicAuthHandler.processRequest(request);

        assertEquals(response.getStatus(), ResponseStatus.UNAUTHORIZED);
        assertEquals("Basic realm=\"Access to staging site\"", response.getHeaders().get(ResponseHeader.AUTHENTICATE.toString()));
    }

    @Test
    public void returnsResponseWithStatusUnauthorizedWhen_RequestHasNoAuthorizationHeader() {
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
        }};
        Request request = new Request(Method.GET, path, null, headers, null);

        Response response = basicAuthHandler.processRequest(request);

        assertEquals(response.getStatus(), ResponseStatus.UNAUTHORIZED);
        assertEquals("Basic realm=\"Access to staging site\"", response.getHeaders().get(ResponseHeader.AUTHENTICATE.toString()));
    }

    @Test
    public void returnsResponseWithStatusNotAllowedWhen_RequestHasMethodOtherThanGet() {
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("Authorization", "Basic YWRtaW46aHVudGVyMg==");
        }};
        Request request = new Request(Method.PUT, path, null, headers, null);

        Response response = basicAuthHandler.processRequest(request);

        assertEquals(response.getStatus(), ResponseStatus.NOT_ALLOWED);
    }
}
