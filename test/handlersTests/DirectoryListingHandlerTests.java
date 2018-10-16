package handlersTests;

import httpserver.handlers.DirectoryLinksHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.utilities.Method;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DirectoryListingHandlerTests {

    private DirectoryLinksHandler directoryLinksHandler;
    private Method method;

    @Before
    public void setup() {
        method = Method.GET;
        String rootPath = "src/httpserver/utilities/sampleTestFiles";
        directoryLinksHandler = new DirectoryLinksHandler(rootPath);
    }

    @Test
    public void coversPathReturnsTrueWhen_RequestPathIsRoot() {
        String path = "/";
        Request request = new Request(method, path, null, null, null);

        assertTrue(directoryLinksHandler.coversPathFromRequest(request));
    }

    @Test
    public void coversPathReturnsFalseWhen_RequestPathIsNotRoot() {
        String path = "/AnyOtherPath";
        Request request = new Request(method, path, null, null, null);

        assertFalse(directoryLinksHandler.coversPathFromRequest(request));
    }

    @Test
    public void returnsResponseWithStatus200() {
        String path = "/";
        Request request = new Request(method, path, null, null, null);
        byte[] expectedBody = ("<html><head></head><body>" +
                "<a href='/cat-form'>cat-form</a><br>"  +
                "<a href='/empty-form'>empty-form</a><br>"  +
                "<a href='/form-with-data'>form-with-data</a><br>"  +
                "<a href='/partial_content.txt'>partial_content.txt</a><br>"  +
                "<a href='/patch-content'>patch-content</a><br>"  +
                "<a href='/testFile.txt'>testFile.txt</a><br>" +
                "</body></html>").getBytes();

        Response response = directoryLinksHandler.processRequest(request);

        assertArrayEquals(expectedBody, response.getBodyContent());
    }
}
