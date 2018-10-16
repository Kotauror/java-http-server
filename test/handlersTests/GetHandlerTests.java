package handlersTests;

import httpserver.response.ResponseHeader;
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
import static org.junit.Assert.assertArrayEquals;

public class GetHandlerTests {

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
        byte[] expected = fileContentConverter.getFileContentFromFile(new File(pathToTestFile));

        Response response = getHandler.processRequest(request);

        assertArrayEquals(expected, response.getBodyContent());
    }

    @Test
    public void pathExistsReturnsTrueWHenExists() {
        String pathToTestFile = "src/httpserver/utilities/sampleTestFiles/";

        boolean actual = getHandler.getFileOperator().fileExists(pathToTestFile + request.getPath());

        assertEquals(true, actual);
    }

    @Test
    public void pathExistsReturnsFalseeWHenExists() {
        String pathToTestFile = "src/httpserver/utilities/sampleTestFiles/testFolder";

        boolean actual = getHandler.getFileOperator().fileExists(pathToTestFile + request.getPath());

        assertEquals(false, actual);
    }

    @Test
    public void returnsRangeResponseWhenBothLimitsPresent() throws IOException {
        String path = "/partial_content.txt";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("Host", "localhost");
            put("Accept-Language", "en-US");
            put("Range", "bytes=0-6");
        }};
        byte[] partOfFile = "This is".getBytes();
        String expectedContentRange = "bytes 0-6/77";
        Request request = new Request(Method.GET, path, httpVersion, headers, body);

        Response response = getHandler.processRequest(request);

        Assert.assertEquals(response.getStatus(), ResponseStatus.RANGE_REQUEST);
        Assert.assertArrayEquals(partOfFile, response.getBodyContent());
        Assert.assertEquals("text/plain", response.getHeaders().get(ResponseHeader.CONTENT_TYPE.toString()));
        Assert.assertEquals(expectedContentRange, response.getHeaders().get(ResponseHeader.CONTENT_RANGE.toString()));
    }

    @Test
    public void returnsRangeResponseWhenOnlyEndLimitPresent() throws IOException {
        String path = "/partial_content.txt";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("Host", "localhost");
            put("Accept-Language", "en-US");
            put("Range", "bytes=-6");
        }};
        String expectedContentRange = "bytes 71-76/77";
        Request request = new Request(Method.GET, path, httpVersion, headers, body);

        Response response = getHandler.processRequest(request);

        Assert.assertEquals(response.getStatus(), ResponseStatus.RANGE_REQUEST);
        Assert.assertEquals("text/plain", response.getHeaders().get(ResponseHeader.CONTENT_TYPE.toString()));
        Assert.assertEquals(expectedContentRange, response.getHeaders().get(ResponseHeader.CONTENT_RANGE.toString()));
    }

    @Test
    public void returnsRangeResponseWhenOnlyStartLimitPresent() throws IOException {
        String path = "/partial_content.txt";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("Host", "localhost");
            put("Accept-Language", "en-US");
            put("Range", "bytes=60-");
        }};
        String expectedContentRange = "bytes 60-76/77";
        Request request = new Request(Method.GET, path, httpVersion, headers, body);

        Response response = getHandler.processRequest(request);

        Assert.assertEquals(response.getStatus(), ResponseStatus.RANGE_REQUEST);
        Assert.assertEquals("text/plain", response.getHeaders().get(ResponseHeader.CONTENT_TYPE.toString()));
        Assert.assertEquals(expectedContentRange, response.getHeaders().get(ResponseHeader.CONTENT_RANGE.toString()));
    }

    @Test
    public void returnsResponseWithStatus416IfRangeTooWide() throws IOException {
        String path = "/partial_content.txt";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("Host", "localhost");
            put("Accept-Language", "en-US");
            put("Range", "bytes=0-900");
        }};
        Request request = new Request(Method.GET, path, httpVersion, headers, body);

        Response response = getHandler.processRequest(request);

        Assert.assertEquals(response.getStatus(), ResponseStatus.RANGE_NOT_SATISFIABLE);
    }
}
