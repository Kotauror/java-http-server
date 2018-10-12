package handlersTests;

import httpserver.handlers.FormHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseHeader;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class formHandlerTests {

    private String rootPath;
    private FormHandler formHandler;

    @Before
    public void setup() {
        rootPath = "src/httpserver/utilities/sampleTestFiles";
        formHandler = new FormHandler(rootPath);
    }


    @Test
    public void whenFileDoesntExistReturn404() throws IOException {
        String path = "src/httpserver/utilities/sampleTestFiles/testtes/datat";
        String httpVersion = "HTTP/1.1";
        Request request = new Request(Method.GET, path, httpVersion, null,null);

        Response response = formHandler.processRequest(request);

        assertEquals(ResponseStatus.NOT_FOUND, response.getStatus());
    }

    @Test
    public void whenFileHasNoRequestedContentReturn404() throws IOException {
        String path = "src/httpserver/utilities/sampleTestFiles/empty-form/data";
        String httpVersion = "HTTP/1.1";
        Request request = new Request(Method.GET, path, httpVersion, null,null);

        Response response = formHandler.processRequest(request);

        assertEquals(ResponseStatus.NOT_FOUND, response.getStatus());
    }

    @Test
    public void whenFileHasContentReturnStatusOK() throws IOException {
        String path = "src/httpserver/utilities/sampleTestFiles/form-with-data/data";
        String httpVersion = "HTTP/1.1";
        byte[] expectedBody = "data=koteczek".getBytes();
        Request request = new Request(Method.GET, path, httpVersion, null,null);

        Response response = formHandler.processRequest(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals(expectedBody, response.getBodyContent());
    }

    @Test
    public void onPostRequestReturnsStatus201WhenFileExists() throws IOException {
        String path = "/cat-form";
        String httpVersion = "HTTP/1.1";
        String body = "data=fatcat";
        Request request = new Request(Method.POST, path, httpVersion, null,body);

        Response response = formHandler.processRequest(request);

        String contentOfFile = new String(Files.readAllBytes(Paths.get("src/httpserver/utilities/sampleTestFiles/cat-form")));
        assertEquals(ResponseStatus.CREATED, response.getStatus());
        assertEquals("data=fatcat", contentOfFile);
        assertEquals("/cat-form/data", response.getHeaders().get(ResponseHeader.LOCATION.toString()));
    }

    @After
    public void emptyOverwrittenFile() throws FileNotFoundException {
        File file = formHandler.getFileOperator().getRequestedFileByPath("src/httpserver/utilities/sampleTestFiles/cat-form");
        PrintWriter writer = new PrintWriter(file);
        writer.close();
    }
}
