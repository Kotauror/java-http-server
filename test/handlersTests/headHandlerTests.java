package handlersTests;

import httpserver.handlers.HeadHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedHashMap;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class headHandlerTests {

    private HeadHandler headHandler;
    private Request request;

    @Before
    public void setup() {
        String rootPath = "src/httpserver/utilities/sampleTestFiles";
        headHandler = new HeadHandler(rootPath);

        String path = "/";
        String httpVersion = "HTTP/1.1";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("Host", "localhost");
            put("Accept-Language", "en-US");
        }};

        request = new Request(Method.GET, path, httpVersion, headers, null);
    }

    @Test
    public void returnsResponseWithStatus200() throws IOException {
        Response response = headHandler.processRequest(request);
        assertEquals(ResponseStatus.OK, response.getStatus());
    }

    @Test
    public void returnsResponseWithEmptyBody() throws IOException {
        Response response = headHandler.processRequest(request);
        assertEquals(null, response.getBodyContent());
    }

    @Test
    public void returnsResponseWithEmptyHeaders() throws IOException {
        Response response = headHandler.processRequest(request);
        assertTrue(response.getHeaders().isEmpty());
    }
}
