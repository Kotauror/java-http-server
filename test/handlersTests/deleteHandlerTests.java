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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class deleteHandlerTests {

    private String rootPath;
    private LinkedHashMap<String, String> headers;
    private String httpVersion;
    private String path;
    private PutHandler putHandler;

    @Before
    public void setupAndAddFile() {
        rootPath = "src/httpserver/utilities/sampleTestFiles";
        httpVersion = "HTTP/1.1";
        headers = new LinkedHashMap<>();
        path = "/filetesting";

        putHandler = new PutHandler(rootPath);
        Request request = new Request(Method.GET, path, httpVersion, headers, "Some content");
        putHandler.processRequest(request);
    }

    @Test
    public void returnsNotFoundWhenDoestExist() {
        String nonExistingPath = "/hehehhehhehehe";
        Request request = new Request(Method.DELETE, nonExistingPath, httpVersion, headers, "Some content");
        DeleteHandler deleteHandler = new DeleteHandler(rootPath);
        Response response = deleteHandler.processRequest(request);

        assertEquals(ResponseStatus.NOT_FOUND, response.getStatus());
    }

    @Test
    public void deletesFileWhenItExists() {
        Request request = new Request(Method.DELETE, path, httpVersion, headers, "Some content");
        DeleteHandler deleteHandler = new DeleteHandler(rootPath);
        Response response = deleteHandler.processRequest(request);

        assertFalse(Files.exists(Paths.get(rootPath + request.getPath())));
        assertEquals(ResponseStatus.OK, response.getStatus());
    }

    @After
    public void deleteCreatedFiles() {
        File file = putHandler.getFileOperator().getRequestedFile(this.rootPath + "/filetesting");
        file.delete();
    }
}
