package httpserver.request;

import httpserver.Method;

import java.util.Arrays;
import java.util.LinkedHashMap;

public class RequestParser {

    public RequestParser() {}

    public Request parse(String requestString) {
        Method method = getMethod(requestString);
        String path = getPath(requestString);
        LinkedHashMap<String, String> headers = getHeaders(requestString);
        String body = getBody(requestString);
        return new Request(method, path, headers, body);
    }

    public Method getMethod(String requestString) {
        String firstLine = this.getFirstLine(requestString);
        String elementsOfFirstLine[] = firstLine.split(" ", 2);
        return Method.valueOf(elementsOfFirstLine[0]);
    }

    public String getPath(String requestString) {
        String firstLine = this.getFirstLine(requestString);
        String elementsOfFirstLine[] = firstLine.split(" ", 2);
        return stripOfNewLine(elementsOfFirstLine[1]);
    }

    public LinkedHashMap getHeaders(String requestString) {
        LinkedHashMap<String, String> headers = new LinkedHashMap<>();
        String linesOfRequestString[] = splitRequestIntoLines(requestString);
        String linesOfInputWithoutFirst[] = Arrays.copyOfRange(linesOfRequestString, 1, linesOfRequestString.length);
        for (String line : linesOfInputWithoutFirst) {
            if (line.equals("")) {
                break;
            }
            if (!line.equals("")) {
                String ElementsOfLine[] = line.split(" ", 2);
                String key = this.removeLastCharacter(ElementsOfLine[0]);
                String value = this.stripOfNewLine(ElementsOfLine[1]);
                headers.put(key, value);
            }
        }
        return headers;
    }

    public String getBody(String requestString) {
        String linesOfInputString[] = requestString.split("\\r");
        return this.removeEmptyLines(linesOfInputString[1]);
    }

    private String getFirstLine(String requestString) {
        String linesOfInputString[] = requestString.split("\\r?\\n");
        return linesOfInputString[0];
    }

    private String[] splitRequestIntoLines(String requestString) {
        return requestString.split("\\r?\\n");
    }

    private String stripOfNewLine(String string) {
        return string.replaceAll("[\n\r]", "");
    }

    private String removeLastCharacter(String string) {
        return string.substring(0, string.length() -1);
    }

    private String removeEmptyLines(String string) {
        return string.replaceAll("(?m)^[ \t]*\r?\n", "");
    }
}
