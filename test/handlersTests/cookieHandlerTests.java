package handlersTests;

import httpserver.handlers.CookieHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class cookieHandlerTests {

    private CookieHandler cookieHandler;

    @Before
    public void setup() {
        cookieHandler = new CookieHandler();
    }

    @Test
    public void whenRequestHasEatAndPathAndCookieAsHeaderReturnsResponseWithOKAndValueOfCookieHeaderInBody() {
        String httpVersion = "HTTP/1.1";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("Cookie", "chocolate");
        }};
        String path = "/eat_cookie";
        byte[] expectedBody = "mmmm chocolate".getBytes();
        Request request = new Request(Method.GET, path, httpVersion, headers, "Some content");

        Response response = cookieHandler.processRequest(request);

        assertEquals(response.getStatus(), ResponseStatus.OK);
        assertArrayEquals(expectedBody, response.getBodyContent());
    }

    @Test
    public void whenRequestHasEatAndPathAndCookieAsHeaderReturnsResponseWithOKEatInBodyAndCookieTasteInHeader() {
        String httpVersion = "HTTP/1.1";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
        }};
        String path = "/cookie?type=chocolate";
        byte[] expectedBody = "Eat".getBytes();
        Request request = new Request(Method.GET, path, httpVersion, headers, "Some content");

        Response response = cookieHandler.processRequest(request);

        assertEquals(response.getStatus(), ResponseStatus.OK);
        assertEquals("chocolate", response.getHeaders().get("Set-Cookie"));
        assertArrayEquals(expectedBody, response.getBodyContent());
    }

    @Test
    public void whenRequestHasNoCookieHeaderAndNotSettingTypeReturns404() {
        String httpVersion = "HTTP/1.1";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
        }};
        String path = "/cookTestie?type=chocolate";
        Request request = new Request(Method.GET, path, httpVersion, headers, "Some content");

        Response response = cookieHandler.processRequest(request);

        assertEquals(response.getStatus(), ResponseStatus.NOT_FOUND);
    }
}
