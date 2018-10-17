package handlersTests;

import httpserver.handlers.PutHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PutHandlerTests {

    private PutHandler putHandler;
    private String rootPath;
    private LinkedHashMap<String, String> headers;
    private String body;
    private String pathToNewFile1;
    private String pathToNewFile2;

    @Before
    public void setup() {
        rootPath = "src/httpserver/utilities/sampleTestFiles";
        putHandler = new PutHandler(rootPath);
        headers = new LinkedHashMap<>();
        body = "example body";
        pathToNewFile1 = "/filetesting";
        pathToNewFile2 = "/fileToUpdate";
    }

    @Test
    public void coversPath_when_PathIsValid_ReturnTrue() {
        String path = "src/httpserver/utilities/testFile";
        Request request = new Request(Method.GET, path, null, headers, body);

        assertTrue(putHandler.coversPathFromRequest(request));
    }

    @Test
    public void whenFileDoesNotExist_createsANewFile_ReturnsStatus201() throws IOException {
        this.createFileToExecutePutUpon(pathToNewFile1);

        String contentOfFile = putHandler.getFileContentConverter().getFileContentAsString(rootPath + pathToNewFile1);

        assertTrue(Files.exists(Paths.get(rootPath + pathToNewFile1)));
        assertEquals("Some content", contentOfFile);
    }

    @Test
    public void whenFileExists_updatesAContentOfExistingFile_ReturnsStatus200() throws IOException {
        this.createFileToExecutePutUpon(pathToNewFile2);
        Request request2 = new Request(Method.GET, pathToNewFile2, null, headers, "Some updated content");

        Response response = putHandler.processRequest(request2);

        String contentOfFile = putHandler.getFileContentConverter().getFileContentAsString(rootPath + pathToNewFile2);
        assertTrue(Files.exists(Paths.get(rootPath + pathToNewFile2)));
        assertEquals("Some updated content", contentOfFile);
        assertEquals(ResponseStatus.OK, response.getStatus());
    }

    private void createFileToExecutePutUpon(String nameOfFile) {
        Request request = new Request(Method.GET, nameOfFile, null, headers, "Some content");
        putHandler.processRequest(request);
    }

    @After
    public void deleteCreatedFiles() {
        putHandler.getFileOperator().deleteFile(rootPath + pathToNewFile1);
        putHandler.getFileOperator().deleteFile(rootPath + pathToNewFile2);
    }
}
