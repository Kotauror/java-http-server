package httpserver.request;

import httpserver.utilities.Method;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;

public class RequestParser {

    public RequestParser() {}

    public Request parse(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String firstLine = reader.readLine();

        Method method = getMethod(firstLine);
        String path = getPath(firstLine);
        String httpVersion = getHttpVersion(firstLine);
        LinkedHashMap<String, String> headers = getHeaders(reader);

        String contentLengthKey = headers.get("Content-Length");
        String body = getBody(contentLengthKey, reader);
        return new Request(method, path, httpVersion, headers, body);
    }

    private Method getMethod(String firstLineOfRequest) {
        String[] methodAndPath = this.getMethodPathAndHttpVersion(firstLineOfRequest);
        try {
            return Method.valueOf(methodAndPath[0]);
        } catch (IllegalArgumentException e) {
            return Method.INVALID;
        }
    }

    private String getPath(String firstLineOfRequest) {
        String[] methodAndPath = this.getMethodPathAndHttpVersion(firstLineOfRequest);
        return stripOfNewLine(methodAndPath[1]);
    }

    private String getHttpVersion(String firstLineOfRequest) {
        String[] methodAndPath = this.getMethodPathAndHttpVersion(firstLineOfRequest);
        return stripOfNewLine(methodAndPath[2]);
    }

    private LinkedHashMap getHeaders(BufferedReader bufferedReader) throws IOException {
        LinkedHashMap<String, String> headers = new LinkedHashMap<>();

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (this.hitAnEmptyLine(line)) {
                break;
            } else {
                String ElementsOfLine[] = line.split(" ", 2);
                String key = this.removeLastCharacter(ElementsOfLine[0]);
                String value = this.stripOfNewLine(ElementsOfLine[1]);
                headers.put(key, value);
            }
        }
        return headers;
    }

    private String getBody(String contentLengthKey, BufferedReader bufferedReader) throws IOException {
        if (contentLengthKey == null) {
            return null;
        } else {
            int contentLengthAsInt = Integer.parseInt(contentLengthKey);
            StringBuilder body = new StringBuilder();
            for (int i = 0; i < contentLengthAsInt; i++) {
                body.append((char)bufferedReader.read());
            }
            return body.toString().trim();
        }
    }

    private String[] getMethodPathAndHttpVersion(String FirstLineOfRequest) {
        return FirstLineOfRequest.split(" ", 3);
    }

    private Boolean hitAnEmptyLine(String line) {
        return line.equals("");
    }

    private String stripOfNewLine(String string) {
        return string.replaceAll("[\n\r]", "");
    }

    private String removeLastCharacter(String string) {
        return string.substring(0, string.length() -1);
    }
}
