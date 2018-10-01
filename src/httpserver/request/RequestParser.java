package httpserver.request;

import httpserver.Method;

import java.util.Arrays;
import java.util.LinkedHashMap;

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

    public LinkedHashMap getHeaders(String inputString) {
        LinkedHashMap<String, String> headers = new LinkedHashMap<>();
        String linesOfInputString[] = inputString.split("\\r?\\n");
        String linesOfInputWithoutFirst[] = Arrays.copyOfRange(linesOfInputString, 1, linesOfInputString.length);
        for (String line : linesOfInputWithoutFirst) {
            if (line.equals("")) {
                break;
            }
            if (line != "") {
                String partsOfLine[] = line.split(" ", 2);
                String key = partsOfLine[0].substring(0, partsOfLine[0].length() - 1);
                String value = this.stripOfNewLine(partsOfLine[1]);
                headers.put(key, value);
            }
        }
        return headers;
    }

    private String stripOfNewLine(String string) {
        return string.replaceAll("[\n\r]", "");
    }
}
