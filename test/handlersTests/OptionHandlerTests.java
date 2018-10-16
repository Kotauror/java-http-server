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
    private LinkedHashMap<String, String> headers;

    @Before
    public void setup() {
        optionHandler = new OptionHandler();
        headers = new LinkedHashMap<>();
    }

    @Test
    public void whenRequestedFileExits_ReturnsResponseWithMethodsAllowed() {
        String path = "/testFile.txt";
        Request request = new Request(Method.OPTIONS, path, null, headers, null);

        Response response = optionHandler.processRequest(request);

        assertEquals(response.getStatus(), ResponseStatus.OK);
        assertEquals("GET,HEAD,OPTIONS,PUT,DELETE", response.getHeaders().get("Allow"));
    }

    @Test
    public void whenRequestedFileDoesNotExist_ReturnsResponseWithMethodsAllowed() {
        String path = "/testFileNotExist.txt";
        Request request = new Request(Method.OPTIONS, path, null, headers, null);

        Response response = optionHandler.processRequest(request);

        assertEquals(response.getStatus(), ResponseStatus.OK);
        assertEquals("GET,HEAD,OPTIONS,PUT,DELETE", response.getHeaders().get("Allow"));
    }

    @Test
    public void whenRequestedLogsRoot_ReturnsResponseWithMethodsAllowed() {
        String path = "/logs";
        Request request = new Request(Method.OPTIONS, path, null, headers, null);

        Response response = optionHandler.processRequest(request);

        assertEquals(response.getStatus(), ResponseStatus.OK);
        assertEquals("GET,HEAD,OPTIONS", response.getHeaders().get("Allow"));
    }
}
