package handlersTests;

import httpserver.handlers.PutHandler;
import httpserver.request.Request;
import httpserver.utilities.Method;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

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
    public void createsANewFile() throws IOException {
        String path = "/filetesting";
        Request request = new Request(Method.GET, path, httpVersion, headers, body);

        putHandler.getResponse(request);

        assertTrue(Files.exists(Paths.get(rootPath + request.getPath())));
    }

    @After
    public void deleteOutputFile() {
        File folder = new File("src/httpserver/utilities/sampleTestFiles");
        File[] listOfFiles = folder.listFiles();
        for(int i = 0; i < listOfFiles.length; i++){
            File file = listOfFiles[i];
            if (file.getName().equals("filetesting")){
                file.delete();
            }
        }
    }
}
