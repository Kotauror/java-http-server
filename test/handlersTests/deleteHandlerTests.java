package handlersTests;

import httpserver.handlers.DeleteHandler;
import httpserver.handlers.PutHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class deleteHandlerTests {

    private String rootPath;
    private DeleteHandler deleteHandler;
    private LinkedHashMap<String, String> headers;
    private String httpVersion;
    private String body;
    private PutHandler putHandler;

    @Before
    public void setup() {
        rootPath = "src/httpserver/utilities/sampleTestFiles";
        deleteHandler = new DeleteHandler(rootPath);
        putHandler = new PutHandler(rootPath);
        httpVersion = "HTTP/1.1";
        headers = new LinkedHashMap<>();
        body = "example body";
    }

    @Test
    public void deletesFile() {
        String path = "/filetesting";
        Request request = new Request(Method.GET, path, httpVersion, headers, "Some content");
        putHandler.processRequest(request);
        Request request2 = new Request(Method.DELETE, path, httpVersion, headers, "Some content");
        Response response = deleteHandler.processRequest(request2);

        assertFalse(Files.exists(Paths.get(rootPath + request2.getPath())));
        assertEquals(ResponseStatus.OK, response.getStatus());
    }
}
