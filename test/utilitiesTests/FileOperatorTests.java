package utilitiesTests;

import httpserver.request.Request;
import httpserver.utilities.FileOperator;
import httpserver.utilities.Method;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedHashMap;

import static junit.framework.Assert.assertEquals;

public class FileOperatorTests {

    private Request request;
    private FileOperator fileOperator;
    private String rootPath;

    @Before
    public void setup() {
        rootPath = "src/httpserver/utilities/sampleTestFiles";
        String path = "/testFile.txt";
        String httpVersion = "HTTP/1.1";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("Host", "localhost");
            put("Accept-Language", "en-US");
        }};
        String body = "example body";
        request = new Request(Method.GET, path, httpVersion, headers, body);
        fileOperator = new FileOperator();
    }

    @Test
    public void returnsLengthOfFileContent() throws IOException {
        int actual = fileOperator.getLengthOfFileContent(request, rootPath);
        int expected = 20;
        assertEquals(actual, expected);
    }
}
