package responseTests;

import httpserver.request.Request;
import httpserver.response.RangeRequestResponder;
import httpserver.utilities.Method;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

public class rangeRequestResponderTests {

    private Request request;
    private RangeRequestResponder rangeRequestResponder;

    @Before
    public void setup() {
        String path = "/partial_content.txt";
        String httpVersion = "HTTP/1.1";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("Host", "localhost");
            put("Accept-Language", "en-US");
            put("Range", "bytes=0-4");
        }};
        String body = "Example body";
        request = new Request(Method.GET, path, httpVersion, headers, body);
        rangeRequestResponder = new RangeRequestResponder();
    }

    @Test
    public void returnsMapOfLimitsOfRange() {
        Map<String, Integer> limitsOfRequest = rangeRequestResponder.getRangeLimits(request);

        assertEquals(limitsOfRequest.get("start"), (Integer)0);
        assertEquals(limitsOfRequest.get("end"), (Integer)4);
    }
}
