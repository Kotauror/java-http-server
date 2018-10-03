package responseTests;

import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

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
}
