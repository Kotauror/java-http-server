package responseTests;

import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import org.junit.Before;
import org.junit.Test;

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
}
