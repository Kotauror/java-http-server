package httpserver.request;

import httpserver.Method;

public class RequestParser {

    public RequestParser() {}

    public Method getMethod(String inputString) {
        String parts[] = inputString.split(" ", 2);
        return Method.valueOf(parts[0]);
    }
}
