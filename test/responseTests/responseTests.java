package responseTests;

import httpserver.response.ResponseHeader;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class responseTests {

    private Response fullResponse;
    private byte[] fileContent;
    private Response emptyResponse;

    @Before
    public void setup() {
        ResponseStatus responseStatus = ResponseStatus.OK;
        fileContent = "Kocia test".getBytes();
        String fileType = "text/plain";
        HashMap<ResponseHeader, String> headers = new HashMap<ResponseHeader, String>() {{
            put(ResponseHeader.CONTENT_TYPE, fileType);
        }};
        fullResponse = new Response(responseStatus, fileContent, headers);
        emptyResponse = new Response(ResponseStatus.NOT_FOUND, null, new HashMap<>());
    }

    @Test
    public void fullResponseHasStatusOK() {
        assertEquals(ResponseStatus.OK, fullResponse.getStatus());
    }

    @Test
    public void emptyResponseHasStatusNOT_FOUND() {
        assertEquals(ResponseStatus.NOT_FOUND, emptyResponse.getStatus());
    }

    @Test
    public void fullResponseHasBodyContent() {
        assertArrayEquals(fileContent, fullResponse.getBodyContent());
    }

    @Test
    public void emptyResponseHasEmptyBodyContent() {
        assertArrayEquals(null, emptyResponse.getBodyContent());
    }

    @Test
    public void fullResponseHasNoContentHeader() {
        HashMap<String, String> actual = fullResponse.getHeaders();
        assertEquals("text/plain", actual.get("Content-Type"));
    }

    @Test
    public void emptyResponseHasContentHeader() {
        HashMap<String, String> actual = emptyResponse.getHeaders();
        assertTrue(actual.isEmpty());
    }
}
