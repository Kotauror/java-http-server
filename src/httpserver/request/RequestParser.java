package httpserver.request;

import httpserver.Method;

import java.util.Arrays;
import java.util.LinkedHashMap;

public class RequestParser {

    public RequestParser() {}

    public Request parse(String requestString) {
        String[] linesOfRequest = this.splitRequestIntoLines(requestString);

        Method method = getMethod(linesOfRequest[0]);
        String path = getPath(linesOfRequest[0]);
        LinkedHashMap<String, String> headers = getHeaders(linesOfRequest);
        String body = getBody(requestString);
        return new Request(method, path, headers, body);
    }

    private Method getMethod(String firstLineOfRequest) {
        String[] methodAndPath = this.getMethodAndPath(firstLineOfRequest);
        return Method.valueOf(methodAndPath[0]);
    }

    private String getPath(String firstLineOfRequest) {
        String[] methodAndPath = this.getMethodAndPath(firstLineOfRequest);
        return stripOfNewLine(methodAndPath[1]);
    }

    private LinkedHashMap getHeaders(String[] linesOfRequest) {
        LinkedHashMap<String, String> headers = new LinkedHashMap<>();
        String linesOfRequestWithoutFirst[] = Arrays.copyOfRange(linesOfRequest, 1, linesOfRequest.length);
        for (String line : linesOfRequestWithoutFirst) {
            if (this.hitAnEmptyLine(line)) {
                break;
            }
            if (!(this.hitAnEmptyLine(line))) {
                String ElementsOfLine[] = line.split(" ", 2);
                String key = this.removeLastCharacter(ElementsOfLine[0]);
                String value = this.stripOfNewLine(ElementsOfLine[1]);
                headers.put(key, value);
            }
        }
        return headers;
    }

    private String getBody(String requestString) {
        String partsOfInputString[] = requestString.split("\\r");
        return (hasNoBody(partsOfInputString)) ? "" : this.removeEmptyLines(partsOfInputString[1]);
    }

    private String[] getMethodAndPath(String FirstLineOfRequest) {
        return FirstLineOfRequest.split(" ", 2);
    }

    private Boolean hitAnEmptyLine(String line) {
        return line.equals("");
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

    private Boolean hasNoBody(String[] partsOfInputString) {
        return partsOfInputString.length == 1;
    }
}
