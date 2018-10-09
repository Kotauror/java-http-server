package responseTests;

import httpserver.request.Request;
import httpserver.response.RangeRequestResponder;
import httpserver.utilities.FileContentConverter;
import httpserver.utilities.FileOperator;
import httpserver.utilities.FileTypeDecoder;
import httpserver.utilities.Method;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static junit.framework.Assert.assertEquals;

public class RangeRequestResponderTests {

    private RangeRequestResponder rangeRequestResponder;

    @Before
    public void setup() {
        String rootPath = "src/httpserver/utilities/sampleTestFiles";
        rangeRequestResponder = new RangeRequestResponder(rootPath, new FileOperator(), new FileContentConverter(), new FileTypeDecoder());
    }

    @Test
    public void getRangeLimitsReturnsRightLimitsWhenBothValuesGiven() throws IOException {
        String httpVersion = "HTTP/1.1";
        String body = null;
        String path = "/partial_content.txt";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("Host", "localhost");
            put("Accept-Language", "en-US");
            put("Range", "bytes=0-6");
        }};
        Request request = new Request(Method.GET, path, httpVersion, headers, body);

        HashMap<String, Integer> map = rangeRequestResponder.getRangeLimits(request);
        assertEquals((Integer)0, map.get("start"));
        assertEquals((Integer)6, map.get("end"));
    }

    @Test
    public void getRangeLimitsReturnsRightLimitsWhenOnlySecondValueGiven() throws IOException {
        String httpVersion = "HTTP/1.1";
        String body = null;
        String path = "/partial_content.txt";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("Host", "localhost");
            put("Accept-Language", "en-US");
            put("Range", "bytes=-6");
        }};
        Request request = new Request(Method.GET, path, httpVersion, headers, body);

        HashMap<String, Integer> map = rangeRequestResponder.getRangeLimits(request);
        assertEquals((Integer)71, map.get("start"));
        assertEquals((Integer)76, map.get("end"));
    }

    @Test
    public void getRangeLimitsReturnsRightLimitsWhenOnlyFirstValueGiven() throws IOException {
        String httpVersion = "HTTP/1.1";
        String body = null;
        String path = "/partial_content.txt";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("Host", "localhost");
            put("Accept-Language", "en-US");
            put("Range", "bytes=6-");
        }};
        Request request = new Request(Method.GET, path, httpVersion, headers, body);

        HashMap<String, Integer> map = rangeRequestResponder.getRangeLimits(request);
        assertEquals((Integer)6, map.get("start"));
        assertEquals((Integer)76, map.get("end"));
    }
}
