package handlersTests;

import httpserver.handlers.ParametersHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class parametersHandlerTests {

    private ParametersHandler parametersHandler;

    @Before
    public void setup() {
        parametersHandler = new ParametersHandler();
    }

    @Test
    public void returnsResponseWithDecodedBody() throws UnsupportedEncodingException {
        String path = "/parameters?variable_1=a%20query%20string%20parameter";
        String httpVersion = "HTTP/1.1";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>(){};
        Request request = new Request(Method.GET, path, httpVersion, headers, null);
        byte[] expectedBody = "variable_1 = a query string parameter".getBytes();

        Response response = parametersHandler.processRequest(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals(expectedBody, response.getBodyContent());
    }
}
