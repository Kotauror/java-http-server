package httpserver.request;

import httpserver.Method;

public class RequestParser {

    public RequestParser() {}

    public Method getMethod(String inputString) {
        String elementsOfFirstLine[] = inputString.split(" ", 2);
        return Method.valueOf(elementsOfFirstLine[0]);
    }

    public String getPath(String inputString) {
        String linesOfInputString[] = inputString.split("\\r?\\n");
        String elementsOfFirstLine[] = linesOfInputString[0].split(" ", 2);
        return stripOfNewLine(elementsOfFirstLine[1]);
    }

    private String stripOfNewLine(String string) {
        return string.replaceAll("[\n\r]", "");
    }
}
