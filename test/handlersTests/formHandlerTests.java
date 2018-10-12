package handlersTests;

import httpserver.handlers.FormHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;

public class formHandlerTests {

    private String rootPath;
    private FormHandler formHandler;

    @Before
    public void setup() {
        rootPath = "src/httpserver/utilities/sampleTestFiles";
        formHandler = new FormHandler(rootPath);
    }


    @Test
    public void WhenFileDoesntExistReturn404() throws IOException {
        String path = "src/httpserver/utilities/sampleTestFiles/testtes/datat";
        String httpVersion = "HTTP/1.1";
        Request request = new Request(Method.GET, path, httpVersion, null,null);

        Response response = formHandler.processRequest(request);

        assertEquals(ResponseStatus.NOT_FOUND, response.getStatus());
    }

    @Test
    public void WhenFileHasNoRequestedContentReturn404() throws IOException {
        String path = "src/httpserver/utilities/sampleTestFiles/empty-form/data";
        String httpVersion = "HTTP/1.1";
        Request request = new Request(Method.GET, path, httpVersion, null,null);

        Response response = formHandler.processRequest(request);

        assertEquals(ResponseStatus.NOT_FOUND, response.getStatus());
    }

    @Test
    public void WhenFileHasContentReturnStatusOK() throws IOException {
        String path = "src/httpserver/utilities/sampleTestFiles/form-with-data/data";
        String httpVersion = "HTTP/1.1";
        Request request = new Request(Method.GET, path, httpVersion, null,null);

        Response response = formHandler.processRequest(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
    }
//
//    @Test
//    public void returnsPathOfFileWithoutLastElement() {
//        String path = "src/httpserver/utilities/empty-form/data";
//        String actual = formHandler.getPathWithoutFileContent(path);
//
//        assertEquals("src/httpserver/utilities/empty-form", actual);
//    }
}
