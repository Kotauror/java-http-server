package handlersTests;

import httpserver.handlers.PatchHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class patchHandlerTests {

    private PatchHandler patchHandler;
    private String rootPath;
    private String patchFileName;

    @Before
    public void setup() {
        rootPath = "src/httpserver/utilities/sampleTestFiles";
        patchHandler = new PatchHandler(rootPath);
        patchFileName = "/patch-content";
    }

    @Test
    public void patchHandlerReturnsResponseWith412WhenNoIfMatchHeader() {
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {};
        Request request = new Request(Method.PATCH, patchFileName, null, headers, null);

        Response response = patchHandler.processRequest(request);

        assertEquals(ResponseStatus.PRECONDITION_FAILED, response.getStatus());
    }

    @Test
    public void patchHandlerReturnsResponseWith404WhenFileNotFound() {
        String path = "/nonExistingTestFilehehehe";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {};
        Request request = new Request(Method.PATCH, path, null, headers, null);

        Response response = patchHandler.processRequest(request);

        assertEquals(ResponseStatus.NOT_FOUND, response.getStatus());
    }

    @Test
    public void patchHandlerReturnsResponseWith412WhenShaOfFileDoesNotMatchIfMatch() {
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("If-Match", "kotekowyTest");
        }};
        Request request = new Request(Method.PATCH, patchFileName, null, headers, null);

        Response response = patchHandler.processRequest(request);

        assertEquals(ResponseStatus.PRECONDITION_FAILED, response.getStatus());
    }


    @Test
    public void patchChangesFileAndReturnsStatusNoContent() throws IOException {
        String httpVersion = "HTTP/1.1";
        String shaForFile = this.getShaForFilePath(patchFileName);
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("If-Match", shaForFile);
        }};
        String contentToPath = "patched content";
        Request request = new Request(Method.PATCH, patchFileName, httpVersion, headers, contentToPath);
        File file = patchHandler.getFileOperator().getRequestedFileByPath(rootPath + patchFileName);

        Response response = patchHandler.processRequest(request);

        byte[] fileContent = patchHandler.getFileContentConverter().getFileContentFromFile(file);
        assertArrayEquals(fileContent, contentToPath.getBytes());
        assertEquals(ResponseStatus.NO_CONTENT, response.getStatus());
        assertArrayEquals(contentToPath.getBytes(), response.getBodyContent());
    }

    private String getShaForFilePath(String path) throws IOException {
        File file = patchHandler.getFileOperator().getRequestedFileByPath(rootPath + path);
        byte[] fileContent = patchHandler.getFileContentConverter().getFileContentFromFile(file);
        return patchHandler.getEncoder().getHash(fileContent, "SHA-1");
    }

    @After
    public void revertFileContent() throws IOException {
        Request request = new Request(Method.PATCH, null, null, null, "default content");
        File file = patchHandler.getFileOperator().getRequestedFileByPath(rootPath + patchFileName);
        patchHandler.getFileOperator().writeToFile(file, request);
    }
}
