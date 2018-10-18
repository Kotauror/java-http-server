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
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class PatchHandlerTests {

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
    public void whenNoIfMatchHeader_ReturnsStatus412() {
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {};
        Request request = new Request(Method.PATCH, patchFileName, null, headers, null);

        Response response = patchHandler.processRequest(request);

        assertEquals(ResponseStatus.PRECONDITION_FAILED, response.getStatus());
    }

    @Test
    public void whenRequestedFileNotFound_ReturnsStatus404() {
        String path = "/nonExistingTestFilehehehe";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {};
        Request request = new Request(Method.PATCH, path, null, headers, null);

        Response response = patchHandler.processRequest(request);

        assertEquals(ResponseStatus.NOT_FOUND, response.getStatus());
    }

    @Test
    public void whenIfMatchHeaderAndFilePresentButSHAOfRequestedFileDoesNotMatchIfMatchHeader_ReturnStatus412() {
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("If-Match", "kotekowyTestInvalid");
        }};
        Request request = new Request(Method.PATCH, patchFileName, null, headers, null);

        Response response = patchHandler.processRequest(request);

        assertEquals(ResponseStatus.PRECONDITION_FAILED, response.getStatus());
    }

    @Test
    public void updatesFileOnPatchWhenFileExistsAndSHAMatch_andReturnStatus204() throws IOException, NoSuchAlgorithmException {
        String contentToPatch = "patched content";
        Request request = this.getAValidRequest(contentToPatch);
        File requestedFile = patchHandler.getFileOperator().getRequestedFile(rootPath + patchFileName);

        Response response = patchHandler.processRequest(request);

        byte[] fileContent = patchHandler.getFileContentConverter().getFileContentFromFile(requestedFile);
        assertArrayEquals(fileContent, contentToPatch.getBytes());
        assertEquals(ResponseStatus.NO_CONTENT, response.getStatus());
        assertArrayEquals(contentToPatch.getBytes(), response.getBodyContent());
    }

    private Request getAValidRequest(String contentToPath) throws IOException, NoSuchAlgorithmException {
        String shaOfRequestedFile = this.getShaForFilePath(patchFileName);
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("If-Match", shaOfRequestedFile);
        }};
        return new Request(Method.PATCH, patchFileName, null, headers, contentToPath);
    }

    private String getShaForFilePath(String path) throws IOException, NoSuchAlgorithmException {
        File file = patchHandler.getFileOperator().getRequestedFile(rootPath + path);
        byte[] fileContent = patchHandler.getFileContentConverter().getFileContentFromFile(file);
        return patchHandler.getEncoder().encode(fileContent, "SHA-1");
    }

    @After
    public void revertPatch() throws IOException {
        File file = patchHandler.getFileOperator().getRequestedFile(rootPath + patchFileName);
        patchHandler.getFileOperator().writeToFile(file, "default content");
    }
}
