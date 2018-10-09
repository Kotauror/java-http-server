package handlersTests;

import httpserver.handlers.OptionHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;

public class optionHandlerTests {

    private String rootPath;
    private OptionHandler optionHandler;
    private String httpVersion;
    private LinkedHashMap<String, String> headers;
    private String body;

    @Before
    public void setup() {
        rootPath = "src/httpserver/utilities/sampleTestFiles";
        optionHandler = new OptionHandler(rootPath);
        httpVersion = "HTTP/1.1";
        headers = new LinkedHashMap<>();
        body = "example body";
    }

    @Test
    public void returnsResponseWithMethodsAllowedWhenFileExists() throws IOException {
        String path = "/testFile.txt";
        Request request = new Request(Method.OPTIONS, path, httpVersion, headers, body);

        Response response = optionHandler.processRequest(request);

        assertEquals(response.getStatus(), ResponseStatus.OK);
        assertEquals("GET,HEAD,OPTIONS,PUT,DELETE", response.getHeaders().get("Allow"));
    }

    @Test
    public void returnsResponseWithMethodsAllowedWhenFileDoesNotExist() throws IOException {
        String path = "/testFileNotExist.txt";
        Request request = new Request(Method.OPTIONS, path, httpVersion, headers, body);

        Response response = optionHandler.processRequest(request);

        assertEquals(response.getStatus(), ResponseStatus.OK);
        assertEquals("GET,HEAD,OPTIONS,PUT,DELETE", response.getHeaders().get("Allow"));
    }

    @Test
    public void returnsResponseWithMethodsAllowedWhenRequestedLogs() throws IOException {
        String path = "/logs";
        Request request = new Request(Method.OPTIONS, path, httpVersion, headers, body);

        Response response = optionHandler.processRequest(request);

        assertEquals(response.getStatus(), ResponseStatus.OK);
        assertEquals("GET,HEAD,OPTIONS", response.getHeaders().get("Allow"));
    }
}
