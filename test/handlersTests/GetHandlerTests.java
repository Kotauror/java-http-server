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
    private String rootPath;

    @Before
    public void setup() {
        rootPath = "src/httpserver/utilities/sampleTestFiles";
        getHandler = new GetHandler(rootPath);
    }

    @Test
    public void whenRequestedFileExists_ReturnStatus200AndFileContentInResponse() throws IOException {
        String path = "/testFile.txt";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {};
        Request request = new Request(Method.GET, path, null, headers, null);
        byte[] expectedBodyOfResponse = getHandler.getFileContentConverter().getFileContentFromFile(new File(rootPath + path));

        Response response = getHandler.processRequest(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals(expectedBodyOfResponse, response.getBodyContent());
    }

    @Test
    public void pathExists_WhenPathToFileIsValid_ReturnsTrue() {
        String pathToTestFile = "src/httpserver/utilities/sampleTestFiles/";

        boolean actual = getHandler.getFileOperator().fileExists(pathToTestFile);

        assertEquals(true, actual);
    }

    @Test
    public void pathExists_WhenPathDoesnNotExist_ReturnsFalse() {
        String pathToTestFile = "src/httpserver/utilities/sampleTestFiles/testFolder";

        boolean actual = getHandler.getFileOperator().fileExists(pathToTestFile);

        assertEquals(false, actual);
    }

    @Test
    public void onRangeResponse_WhenBothLimitsArePresent_ReturnsRangeResponse() {
        String path = "/partial_content.txt";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("Range", "bytes=0-6");
        }};
        byte[] expectedResponseBody = "This is".getBytes();
        String expectedContentRange = "bytes 0-6/77";
        Request request = new Request(Method.GET, path, null, headers, null);

        Response response = getHandler.processRequest(request);

        Assert.assertEquals(response.getStatus(), ResponseStatus.RANGE_REQUEST);
        Assert.assertArrayEquals(expectedResponseBody, response.getBodyContent());
        Assert.assertEquals("text/plain", response.getHeaders().get(ResponseHeader.CONTENT_TYPE.toString()));
        Assert.assertEquals(expectedContentRange, response.getHeaders().get(ResponseHeader.CONTENT_RANGE.toString()));
    }

    @Test
    public void onRangeResponse_WhenOnlySecondLimitInPresent_ReturnsRangeResponse() {
        String path = "/partial_content.txt";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("Range", "bytes=-6");
        }};
        String expectedContentRange = "bytes 71-76/77";
        Request request = new Request(Method.GET, path, null, headers, null);

        Response response = getHandler.processRequest(request);

        Assert.assertEquals(response.getStatus(), ResponseStatus.RANGE_REQUEST);
        Assert.assertEquals("text/plain", response.getHeaders().get(ResponseHeader.CONTENT_TYPE.toString()));
        Assert.assertEquals(expectedContentRange, response.getHeaders().get(ResponseHeader.CONTENT_RANGE.toString()));
    }

    @Test
    public void onRangeResponse_WHenOnlyFirstLimitIsPresent_ReturnsRangeResponse() {
        String path = "/partial_content.txt";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("Range", "bytes=60-");
        }};
        String expectedContentRange = "bytes 60-76/77";
        Request request = new Request(Method.GET, path, null, headers, null);

        Response response = getHandler.processRequest(request);

        Assert.assertEquals(response.getStatus(), ResponseStatus.RANGE_REQUEST);
        Assert.assertEquals("text/plain", response.getHeaders().get(ResponseHeader.CONTENT_TYPE.toString()));
        Assert.assertEquals(expectedContentRange, response.getHeaders().get(ResponseHeader.CONTENT_RANGE.toString()));
    }

    @Test
    public void onRangeResponse_WhenRangeIsTooWide_ReturnsStatus416() {
        String path = "/partial_content.txt";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("Range", "bytes=0-900");
        }};
        Request request = new Request(Method.GET, path, null, headers, null);

        Response response = getHandler.processRequest(request);

        Assert.assertEquals(response.getStatus(), ResponseStatus.RANGE_NOT_SATISFIABLE);
    }
}
