package responseTests;

import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class responseTests {

    private Response response;

    @Before
    public void setup() {
        response = new Response();
    }

    @Test
    public void responseHasDefaultStatusOf500() {
        assertEquals(ResponseStatus.INTERNAL_SERVER_ERROR, response.getStatus());
    }

    @Test
    public void setStatusChangesResponseStatus() {
        response.setStatus(ResponseStatus.OK);

        assertEquals(ResponseStatus.OK, response.getStatus());
    }

    @Test
    public void returnsFullResponse() {
        response.setStatus(ResponseStatus.OK);
        String expected = "HTTP/1.1 200";

        assertEquals(expected, response.getFullResponse());
    }

    @Test
    public void setBodyContentSetsContentOfBody() {
        byte[] bodyContent = "Future Body Content".getBytes();
        response.setBodyContent(bodyContent);

        assertArrayEquals(bodyContent, response.getBodyContent());
    }

    @Test
    public void contentTypeHeaderIsSetToTxt() {
        response.setContentTypeHeader("text/plain");
        HashMap<String, String> actual = response.getHeaders();
        assertEquals(actual.get("Content-Type"), "text/plain");
    }

    @Test
    public void contentTypeHeaderIsSetToJPEG() {
        response.setContentTypeHeader("image/jpeg");
        HashMap<String, String> actual = response.getHeaders();
        assertEquals(actual.get("Content-Type"), "image/jpeg");
    }

    @Test
    public void contentTypeHeaderDefaultTOTxt() {
        response.setContentTypeHeader("text/plain");
        HashMap<String, String> actual = response.getHeaders();
        assertEquals(actual.get("Content-Type"), "text/plain");
    }
}
