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

public class putHandlerTests {

    private PutHandler putHandler;
    private String rootPath;
    private String httpVersion;
    private LinkedHashMap<String, String> headers;
    private String body;

    @Before
    public void setup() {
        rootPath = "src/httpserver/utilities/sampleTestFiles";
        putHandler = new PutHandler(rootPath);
        httpVersion = "HTTP/1.1";
        headers = new LinkedHashMap<>();
        body = "example body";
    }

    @Test
    public void returnsTrueWhenPathIsCovered() {
        String path = "src/httpserver/utilities/testFile";
        Request request = new Request(Method.GET, path, httpVersion, headers, body);

        assertTrue(putHandler.coversPathFromRequest(request));
    }

    @Test
    public void returnsFalseWhenPathIsNotCovered() {
        String path = "src/httpserver/utilities/testKoteczki/testFile";
        Request request = new Request(Method.GET, path, httpVersion, headers, body);

        assertFalse(putHandler.coversPathFromRequest(request));
    }

    @Test
    public void createsANewFileWithContent() throws IOException {
        String path = "/filetesting";
        Request request = new Request(Method.GET, path, httpVersion, headers, "Some content");
        Response response = putHandler.processRequest(request);
        String contentOfFile = new String(Files.readAllBytes(Paths.get("src/httpserver/utilities/sampleTestFiles/filetesting")));

        assertTrue(Files.exists(Paths.get(rootPath + request.getPath())));
        assertEquals("Some content", contentOfFile);
        assertEquals(ResponseStatus.CREATED, response.getStatus());;
    }

    @Test
    public void updatesAContentOfExistingFile() throws IOException {
        String path = "/fileToUpdate";
        Request request = new Request(Method.GET, path, httpVersion, headers, "Some content");
        putHandler.processRequest(request);
        Request request2 = new Request(Method.GET, path, httpVersion, headers, "Some updated content");
        Response response = putHandler.processRequest(request2);
        String contentOfFile = new String(Files.readAllBytes(Paths.get("src/httpserver/utilities/sampleTestFiles/fileToUpdate")));

        assertTrue(Files.exists(Paths.get(rootPath + request.getPath())));
        assertEquals("Some updated content", contentOfFile);
        assertEquals(ResponseStatus.OK, response.getStatus());
    }

    @After
    public void deleteOutputFile() {
        File folder = new File("src/httpserver/utilities/sampleTestFiles");
        File[] listOfFiles = folder.listFiles();
        for(int i = 0; i < listOfFiles.length; i++){
            File file = listOfFiles[i];
            if (file.getName().equals("filetesting") || file.getName().equals("fileToUpdate")){
                file.delete();
            }
        }
    }
}
