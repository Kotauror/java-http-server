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
        ResponseStatus responseStatus = ResponseStatus.OK;
        response = new Response(responseStatus);
    }

    @Test
    public void responseHasStatusOK() {
        assertEquals(ResponseStatus.OK, response.getStatus());
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
