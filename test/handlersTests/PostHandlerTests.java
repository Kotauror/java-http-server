package handlersTests;

import httpserver.handlers.PostHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PostHandlerTests {

    private String rootPath;
    private LinkedHashMap<String, String> headers;
    private String body;
    private PostHandler postHandler;
    private String pathToFileCreatedOnPost;

    @Before
    public void setup() {
        rootPath = "test/sampleTestFiles";
        headers = new LinkedHashMap<>();
        body = "example body";
        postHandler = new PostHandler(rootPath);
        pathToFileCreatedOnPost = "/testFileKotek";
    }

    @Test
    public void whenFileExists_ReturnStatus405() {
        String pathOfExistingFile = "/cat-form";
        Request request = new Request(Method.POST, pathOfExistingFile, null, headers, body);

        Response response = postHandler.processRequest(request);

        assertTrue(Files.exists(Paths.get(rootPath + request.getPath())));
        assertEquals(response.getStatus(), ResponseStatus.NOT_ALLOWED);
    }

    @Test
    public void whenFileDoesNotExist_createsANewFile_ReturnsStatus201() {
        Request request = new Request(Method.POST, pathToFileCreatedOnPost, null, headers, body);
        Response response = postHandler.processRequest(request);

        assertEquals(response.getStatus(), ResponseStatus.CREATED);
    }

    @After
    public void deleteOutputFile() {
        File file = postHandler.getFileOperator().getRequestedFile(rootPath + pathToFileCreatedOnPost);
        file.delete();
    }
}
