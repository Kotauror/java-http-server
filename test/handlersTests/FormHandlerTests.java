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

public class FormHandlerTests {

    private String rootPath;
    private FormHandler formHandler;
    private String pathForFileUpdatedOnPut;
    private String pathForFileCreatedOnPost;
    private String pathForFileCreatedForTestingDelete;

    @Before
    public void setup() {
        rootPath = "src/httpserver/utilities/sampleTestFiles";
        formHandler = new FormHandler(rootPath);
        pathForFileUpdatedOnPut = "/cat-form";
        pathForFileCreatedOnPost = "/samplesample";
        pathForFileCreatedForTestingDelete = "/anotherTestFile";
    }


    @Test
    public void onHandleGet_WhenFileDoesNotExist_ReturnStatus404() {
        String path = "/testtes/datataTest";
        Request request = new Request(Method.GET, path, null, null,null);

        Response response = formHandler.processRequest(request);

        assertEquals(ResponseStatus.NOT_FOUND, response.getStatus());
    }

    @Test
    public void onHandleGet_WhenFileExistsButHasNoRequestedContent_ReturnStatus404() {
        String path = "/empty-form/data";
        Request request = new Request(Method.GET, path, null, null,null);

        Response response = formHandler.processRequest(request);

        assertEquals(ResponseStatus.NOT_FOUND, response.getStatus());
    }

    @Test
    public void onHandleGet_WhenFileExistsAndHasContent_ReturnStatusOK() {
        String path = "/form-with-data/data";
        byte[] expectedBody = "data=koteczek".getBytes();
        Request request = new Request(Method.GET, path, null, null,null);

        Response response = formHandler.processRequest(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals(expectedBody, response.getBodyContent());
    }

    @Test
    public void onHandlePost_WhenFileDoesNotAlreadyExistAndActionIsSuccessful_ReturnStatus201() throws IOException {
        String body = "data=fatcat";
        Request request = new Request(Method.POST, pathForFileCreatedOnPost, null, null, body);

        Response response = formHandler.processRequest(request);

        String contentOfFile = new String(Files.readAllBytes(Paths.get("src/httpserver/utilities/sampleTestFiles" + pathForFileCreatedOnPost)));
        assertEquals(ResponseStatus.CREATED, response.getStatus());
        assertEquals(body, contentOfFile);
        assertEquals(pathForFileCreatedOnPost + "/data", response.getHeaders().get(ResponseHeader.LOCATION.toString()));
    }

    @Test
    public void onHandlePost_WhenFileAlreadyExists_ReturnsStatus405() {
        String body = "data=fatcat";
        Request request = new Request(Method.POST, "/empty-form", null, null, body);

        Response response = formHandler.processRequest(request);

        assertEquals(ResponseStatus.NOT_ALLOWED, response.getStatus());
    }

    @Test
    public void onHandlePut_WhenFileExistsAndChanged_ReturnsStatus200() throws IOException {
        String body = "data=tabbycat";
        Request request = new Request(Method.PUT, pathForFileUpdatedOnPut + "/data", null, null, body);

        Response response = formHandler.processRequest(request);

        String contentOfFile = new String(Files.readAllBytes(Paths.get("src/httpserver/utilities/sampleTestFiles" + pathForFileUpdatedOnPut)));
        assertEquals(ResponseStatus.OK, response.getStatus());
        assertEquals(body, contentOfFile);
    }

    @Test
    public void onDeleteRequest_WhenDeletesContentOfFile_ReturnsStatus200() {
        // Create file to be deleted
        Request request = new Request(Method.GET, pathForFileCreatedForTestingDelete, null, null, "data=hihiihih");
        PutHandler putHandler = new PutHandler(rootPath);
        putHandler.processRequest(request);

        // request to delete the file
        String httpVersion = "HTTP/1.1";
        Request requestToDelete = new Request(Method.DELETE, pathForFileCreatedForTestingDelete, httpVersion, null,null);
        Response response = formHandler.processRequest(requestToDelete);

        assertFalse(Files.exists(Paths.get(rootPath + pathForFileCreatedForTestingDelete)));
        assertEquals(ResponseStatus.OK, response.getStatus());
    }

    @After
    public void emptyOverwrittenFile() throws FileNotFoundException {
        File file = formHandler.getFileOperator().getRequestedFile(rootPath + pathForFileUpdatedOnPut);
        PrintWriter writer = new PrintWriter(file);
        writer.print("");
        writer.close();
    }

    @After
    public void deleteCreatedFile() {
        File file = formHandler.getFileOperator().getRequestedFile(rootPath + pathForFileCreatedForTestingDelete);
        file.delete();
        File file2 = formHandler.getFileOperator().getRequestedFile(rootPath + pathForFileCreatedOnPost);
        file2.delete();
    }
}
