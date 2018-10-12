package handlersTests;

import httpserver.handlers.FormHandler;
import httpserver.handlers.PutHandler;
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
import static org.junit.Assert.assertFalse;

public class formHandlerTests {

    private String rootPath;
    private FormHandler formHandler;
    private String catFormFilePath;

    @Before
    public void setup() {
        rootPath = "src/httpserver/utilities/sampleTestFiles";
        formHandler = new FormHandler(rootPath);
        catFormFilePath = "/cat-form";
    }


    @Test
    public void whenFileDoesntExistReturn404() throws IOException {
        String path = "/testtes/datat";
        String httpVersion = "HTTP/1.1";
        Request request = new Request(Method.GET, path, httpVersion, null,null);

        Response response = formHandler.processRequest(request);

        assertEquals(ResponseStatus.NOT_FOUND, response.getStatus());
    }

    @Test
    public void whenFileHasNoRequestedContentReturn404() throws IOException {
        String path = "/empty-form/data";
        String httpVersion = "HTTP/1.1";
        Request request = new Request(Method.GET, path, httpVersion, null,null);

        Response response = formHandler.processRequest(request);

        assertEquals(ResponseStatus.NOT_FOUND, response.getStatus());
    }

    @Test
    public void whenFileHasContentReturnStatusOK() throws IOException {
        String path = "/form-with-data/data";
        String httpVersion = "HTTP/1.1";
        byte[] expectedBody = "data=koteczek".getBytes();
        Request request = new Request(Method.GET, path, httpVersion, null,null);

        Response response = formHandler.processRequest(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals(expectedBody, response.getBodyContent());
    }

    @Test
    public void onPostRequestReturnsStatus201WhenFileDoesNotExist() throws IOException {
        String httpVersion = "HTTP/1.1";
        String body = "data=fatcat";
        Request request = new Request(Method.POST, "/samplesample", httpVersion, null,body);

        Response response = formHandler.processRequest(request);

        String contentOfFile = new String(Files.readAllBytes(Paths.get("src/httpserver/utilities/sampleTestFiles" + "/samplesample")));
        assertEquals(ResponseStatus.CREATED, response.getStatus());
        assertEquals("data=fatcat", contentOfFile);
        assertEquals("/samplesample" + "/data", response.getHeaders().get(ResponseHeader.LOCATION.toString()));
    }

    @Test
    public void onPutRequestReturnsStatus200WhenFileExists() throws IOException {
        String httpVersion = "HTTP/1.1";
        String body = "data=tabbycat";
        Request request = new Request(Method.PUT, "/cat-form/data", httpVersion, null,body);

        Response response = formHandler.processRequest(request);

        String contentOfFile = new String(Files.readAllBytes(Paths.get("src/httpserver/utilities/sampleTestFiles/cat-form")));
        assertEquals(ResponseStatus.OK, response.getStatus());
        assertEquals("data=tabbycat", contentOfFile);
    }

    @Test
    public void onDeleteRequestDeletesContentOfFileANdReturnsStatus200() throws IOException {
        // Create file to be deleted
        String path = "/anotherTestFile";
        Request request = new Request(Method.GET, path, "", null, "data=hihiihih");
        PutHandler putHandler = new PutHandler(rootPath);
        putHandler.processRequest(request);

        // request to delete the file
        String httpVersion = "HTTP/1.1";
        Request requestToDelete = new Request(Method.DELETE, "/anotherTestFile", httpVersion, null,null);
        Response response = formHandler.processRequest(requestToDelete);

        assertFalse(Files.exists(Paths.get(rootPath + "anotherTestFile")));
        assertEquals(ResponseStatus.OK, response.getStatus());
    }

    @After
    public void emptyOverwrittenFile() throws FileNotFoundException {
        File file = formHandler.getFileOperator().getRequestedFileByPath("src/httpserver/utilities/sampleTestFiles" + catFormFilePath);
        PrintWriter writer = new PrintWriter(file);
        writer.print("");
        writer.close();
    }

    @After
    public void deleteCreatedFile() {
        File file = formHandler.getFileOperator().getRequestedFileByPath("src/httpserver/utilities/sampleTestFiles/anotherTestFile");
        file.delete();
        File file2 = formHandler.getFileOperator().getRequestedFileByPath("src/httpserver/utilities/sampleTestFiles/samplesample");
        file2.delete();
    }
}
