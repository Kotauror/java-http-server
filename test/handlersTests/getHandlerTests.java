package handlersTests;

import httpserver.utilities.Method;
import httpserver.handlers.GetHandler;
import httpserver.request.Request;
import httpserver.utilities.FileContentConverter;
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
        String rootPath = "src/httpserver/utilities/sampleTestFiles";
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
        Response response = getHandler.processRequest(request);
        assertEquals(ResponseStatus.OK, response.getStatus());
    }

    @Test
    public void createsResponseWithBody() throws IOException {
        String pathToTestFile = "src/httpserver/utilities/sampleTestFiles/testFile";
        byte[] expected = fileContentConverter.getFileContent(new File(pathToTestFile));

        Response response = getHandler.processRequest(request);

        assertArrayEquals(expected, response.getBodyContent());
    }

    @Test
    public void pathExistsReturnsTrueWHenExists() {
        String pathToTestFile = "src/httpserver/utilities/sampleTestFiles/";

        boolean actual = getHandler.fileExistsOnPath(request, pathToTestFile);

        assertEquals(true, actual);
    }

    @Test
    public void pathExistsReturnsFalseeWHenExists() {
        String pathToTestFile = "src/httpserver/utilities/sampleTestFiles/testFolder";

        boolean actual = getHandler.fileExistsOnPath(request, pathToTestFile);

        assertEquals(false, actual);
    }
}
