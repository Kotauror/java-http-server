package handlersTests;

import httpserver.handlers.PostHandler;
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

import static org.junit.Assert.assertTrue;

public class postHandlerTests {

    private String rootPath;
    private String httpVersion;
    private LinkedHashMap<String, String> headers;
    private String body;
    private PostHandler postHandler;

    @Before
    public void setup() {
        rootPath = "src/httpserver/utilities/sampleTestFiles";
        httpVersion = "HTTP/1.1";
        headers = new LinkedHashMap<>();
        body = "example body";
        postHandler = new PostHandler(rootPath);
    }

    @Test
    public void returns422WhenFileExists() throws IOException {
        String path = "/kosiowaty";
        PutHandler putHandler = new PutHandler(rootPath);
        Request request = new Request(Method.GET, path, httpVersion, headers, "Some content");
        putHandler.processRequest(request);

        Request request2 = new Request(Method.POST, path, httpVersion, headers, body);
        Response response = postHandler.processRequest(request2);

        assertTrue(Files.exists(Paths.get(rootPath + request.getPath())));
        assertTrue(response.getStatus().equals(ResponseStatus.UNPROCESSABLE));
    }

    @Test
    public void createsANewFileWhenDoesntExist() throws IOException {
        String path = "/testFileKotek";
        Request request = new Request(Method.POST, path, httpVersion, headers, body);
        Response response = postHandler.processRequest(request);

        assertTrue(response.getStatus().equals(ResponseStatus.CREATED));
    }

    @After
    public void deleteOutputFile() {
        File folder = new File("src/httpserver/utilities/sampleTestFiles");
        File[] listOfFiles = folder.listFiles();
        for(int i = 0; i < listOfFiles.length; i++){
            File file = listOfFiles[i];
            if (file.getName().equals("kosiowaty") || file.getName().equals("testFileKotek")){
                file.delete();
            }
        }
    }
}
