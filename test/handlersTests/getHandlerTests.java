package handlersTests;

import httpserver.utilities.Method;
import httpserver.handlers.GetHandler;
import httpserver.request.Request;
import httpserver.utilities.FileContentConverter;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class getHandlerTests {

    private GetHandler getHandler;
    private Request request;
    private FileContentConverter fileContentConverter;
    private String httpVersion;
    private String body;

    @Before
    public void setup() {
        fileContentConverter = new FileContentConverter();
        String rootPath = "src/httpserver/utilities/sampleTestFiles";
        getHandler = new GetHandler(rootPath);
        String path = "/testFile.txt";
        httpVersion = "HTTP/1.1";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("Host", "localhost");
            put("Accept-Language", "en-US");
        }};
        body = "example body";

        request = new Request(Method.GET, path, httpVersion, headers, body);
    }

    @Test
    public void createsAResponseWithStatus200WhenFileIsFound() throws IOException {
        Response response = getHandler.processRequest(request);
        assertEquals(ResponseStatus.OK, response.getStatus());
    }

    @Test
    public void createsResponseWithBody() throws IOException {
        String pathToTestFile = "src/httpserver/utilities/sampleTestFiles/testFile.txt";
        byte[] expected = fileContentConverter.getFileContent(new File(pathToTestFile));

        Response response = getHandler.processRequest(request);

        assertArrayEquals(expected, response.getBodyContent());
    }

    @Test
    public void pathExistsReturnsTrueWHenExists() {
        String pathToTestFile = "src/httpserver/utilities/sampleTestFiles/";

        boolean actual = getHandler.getFileOperator().fileExistsOnPath(request, pathToTestFile);

        assertEquals(true, actual);
    }

    @Test
    public void pathExistsReturnsFalseeWHenExists() {
        String pathToTestFile = "src/httpserver/utilities/sampleTestFiles/testFolder";

        boolean actual = getHandler.getFileOperator().fileExistsOnPath(request, pathToTestFile);

        assertEquals(false, actual);
    }

    @Test
    public void goesToPartialResponseAndReturnsResponseWithRangeRequestStatus() throws IOException {
        String path = "/partial_content.txt";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("Host", "localhost");
            put("Accept-Language", "en-US");
            put("Range", "bytes=0-4");
        }};
        Request request = new Request(Method.GET, path, httpVersion, headers, body);

        Response response = getHandler.processRequest(request);

        Assert.assertEquals(response.getStatus(), ResponseStatus.RANGE_REQUEST);
    }
}
