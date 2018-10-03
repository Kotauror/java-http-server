package handlersTests;

import httpserver.Method;
import httpserver.handlers.GetHandler;
import httpserver.request.Request;
import httpserver.response.FileContentConverter;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class getHandlerTests {

    private GetHandler getHandler;
    private Request request;
    private FileContentConverter fileContentConverter;

    @Before
    public void setup() {
        fileContentConverter = new FileContentConverter();
        String rootPath = "test/handlersTests/sampleTestFiles";
        getHandler = new GetHandler(rootPath);
        String path = "/testFile";
        String httpVersion = "HTTP/1.1";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("Host", "localhost");
            put("Accept-Language", "en-US");
        }};
        String body = "example body";

        request = new Request(Method.GET, path, httpVersion, headers, body);
    }

    @Test
    public void createsAResponseWithStatus200WhenFileIsFound() throws IOException {
        Response response = getHandler.getResponse(request);
        assertEquals(ResponseStatus.OK, response.getStatus());
    }

    @Test
    public void createsResponseWithBody() throws IOException {
        String pathToTestFile = "test/handlersTests/sampleTestFiles/testFile";
        byte[] expected = fileContentConverter.getFileContent(new File(pathToTestFile));

        Response response = getHandler.getResponse(request);

        assertArrayEquals(expected, response.getBodyContent());}
}
