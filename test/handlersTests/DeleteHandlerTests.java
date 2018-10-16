package handlersTests;

import httpserver.handlers.DeleteHandler;
import httpserver.handlers.PutHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DeleteHandlerTests {

    private String rootPath;
    private String pathOfFileToBeDeleted;
    private DeleteHandler deleteHandler;

    @Before
    public void setupTestAndCreateFileToBeLaterDeleted() {
        rootPath = "src/httpserver/utilities/sampleTestFiles";
        pathOfFileToBeDeleted = "/filetesting";
        PutHandler putHandler = new PutHandler(rootPath);
        Request request = new Request(Method.GET, pathOfFileToBeDeleted, null, null, "Some content");
        putHandler.processRequest(request);
        deleteHandler = new DeleteHandler(rootPath);
    }

    @Test
    public void returnsNotFoundWhen_RequestedFileDoestExist() {
        String nonExistingPath = "/hehehhehhehehe";
        Request request = new Request(Method.DELETE, nonExistingPath, null, null, "Some content");

        Response response = deleteHandler.processRequest(request);

        assertEquals(ResponseStatus.NOT_FOUND, response.getStatus());
    }

    @Test
    public void deletesFile_WhenTheFileExists() {
        Request request = new Request(Method.DELETE, pathOfFileToBeDeleted, null, null, "Some content");

        Response response = deleteHandler.processRequest(request);

        assertFalse(Files.exists(Paths.get(rootPath + request.getPath())));
        assertEquals(ResponseStatus.OK, response.getStatus());
    }

    @After
    public void deleteCreatedFiles() {
        deleteHandler.getFileOperator().deleteFile(this.rootPath + pathOfFileToBeDeleted);
    }
}
