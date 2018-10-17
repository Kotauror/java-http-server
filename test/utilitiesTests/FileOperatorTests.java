package utilitiesTests;

import httpserver.request.Request;
import httpserver.utilities.FileOperator;
import httpserver.utilities.Method;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedHashMap;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FileOperatorTests {

    private Request request;
    private FileOperator fileOperator;
    private String rootPath;

    @Before
    public void setup() {
        rootPath = "src/httpserver/utilities/sampleTestFiles";
        fileOperator = new FileOperator();
    }

    @Test
    public void returnsLengthOfFileContent() throws IOException {
        String path = "/testFile.txt";
        String httpVersion = "HTTP/1.1";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("Host", "localhost");
            put("Accept-Language", "en-US");
        }};
        String body = "example body";
        request = new Request(Method.GET, path, httpVersion, headers, body);

        int actual = fileOperator.getLengthOfFileContent(rootPath + request.getPath());
        int expected = 20;
        assertEquals(actual, expected);
    }

    @Test
    public void returnsTrueWhenPathRefersContent() {
        String path = "/form-with-data/data";
        String httpVersion = "HTTP/1.1";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("Host", "localhost");
            put("Accept-Language", "en-US");
        }};
        String body = "example body";
        request = new Request(Method.GET, path, httpVersion, headers, body);

        assertTrue(fileOperator.filePathRefersFileContent(rootPath, request.getPath()));
    }


    @Test
    public void returnsFalseWhenPathDoesNotReferContent() {
        String path = "/form-with-data";
        String httpVersion = "HTTP/1.1";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>() {{
            put("Host", "localhost");
            put("Accept-Language", "en-US");
        }};
        String body = "example body";
        request = new Request(Method.GET, path, httpVersion, headers, body);

        assertFalse(fileOperator.filePathRefersFileContent(rootPath, request.getPath()));
    }

    @Test
    public void returnsTrueWhenFileExists() {
        assertTrue(fileOperator.fileExists(rootPath + "/form-with-data"));
    }

    @Test
    public void returnsFalseWhenFileDoesNotExist() {
        assertFalse(fileOperator.fileExists(rootPath + "/form-with-data/data"));
    }

}
