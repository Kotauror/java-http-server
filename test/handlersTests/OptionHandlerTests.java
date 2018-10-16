package handlersTests;

import httpserver.handlers.OptionHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;

public class OptionHandlerTests {

    private OptionHandler optionHandler;
    private String httpVersion;
    private LinkedHashMap<String, String> headers;
    private String body;

    @Before
    public void setup() {
        optionHandler = new OptionHandler();
        httpVersion = "HTTP/1.1";
        headers = new LinkedHashMap<>();
        body = "example body";
    }

    @Test
    public void returnsResponseWithMethodsAllowedWhenFileExists() {
        String path = "/testFile.txt";
        Request request = new Request(Method.OPTIONS, path, httpVersion, headers, body);

        Response response = optionHandler.processRequest(request);

        assertEquals(response.getStatus(), ResponseStatus.OK);
        assertEquals("GET,HEAD,OPTIONS,PUT,DELETE", response.getHeaders().get("Allow"));
    }

    @Test
    public void returnsResponseWithMethodsAllowedWhenFileDoesNotExist() {
        String path = "/testFileNotExist.txt";
        Request request = new Request(Method.OPTIONS, path, httpVersion, headers, body);

        Response response = optionHandler.processRequest(request);

        assertEquals(response.getStatus(), ResponseStatus.OK);
        assertEquals("GET,HEAD,OPTIONS,PUT,DELETE", response.getHeaders().get("Allow"));
    }

    @Test
    public void returnsResponseWithMethodsAllowedWhenRequestedLogs() {
        String path = "/logs";
        Request request = new Request(Method.OPTIONS, path, httpVersion, headers, body);

        Response response = optionHandler.processRequest(request);

        assertEquals(response.getStatus(), ResponseStatus.OK);
        assertEquals("GET,HEAD,OPTIONS", response.getHeaders().get("Allow"));
    }
}
