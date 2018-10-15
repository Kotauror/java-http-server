package handlersTests;

import httpserver.handlers.PatchHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;

import static junit.framework.Assert.assertEquals;

public class patchHandlerTests {

    private PatchHandler patchHandler;

    @Before
    public void setup() {
        String rootPath = "src/httpserver/utilities/sampleTestFiles";
        patchHandler = new PatchHandler(rootPath);
    }

    @Test
    public void patchHandlerReturnsResponseWith412WhenNoIfMatchHeader() {
        String path = "/";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {};
        Request request = new Request(Method.PATCH, path, null, headers, null);

        Response response = patchHandler.processRequest(request);

        assertEquals(ResponseStatus.PRECONDITION_FAILED, response.getStatus());
    }
}
