package handlersTests;

import httpserver.handlers.ParametersHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class ParametersHandlerTests {

    private ParametersHandler parametersHandler;

    @Before
    public void setup() {
        parametersHandler = new ParametersHandler();
    }

    @Test
    public void returnsResponseWithDecodedBodyWhenOneVariableInPath() {
        String path = "/parameters?variable_1=a%20query%20string%20parameter";
        String httpVersion = "HTTP/1.1";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>(){};
        Request request = new Request(Method.GET, path, httpVersion, headers, null);
        byte[] expectedBody = "variable_1 = a query string parameter\n".getBytes();

        Response response = parametersHandler.processRequest(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals(expectedBody, response.getBodyContent());
    }

    @Test
    public void returnsResponseWithDecodedBodyWhenTwoVariablesInPath() {
        String path = "/parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%" +
                "26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff";
        String httpVersion = "HTTP/1.1";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>(){};
        Request request = new Request(Method.GET, path, httpVersion, headers, null);
        byte[] expectedBody = "variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?\nvariable_2 = stuff\n".getBytes();

        Response response = parametersHandler.processRequest(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals(expectedBody, response.getBodyContent());
    }
}
