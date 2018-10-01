package httpserver.request;

import httpserver.Method;

import java.util.HashMap;

public class Request {

    private final Method method;
    private final String path;
    private final HashMap<String, String> headers;
    private final String body;

    public Request(Method method, String path, HashMap<String, String> headers, String body) {
        this.method = method;
        this.path = path;
        this.headers = headers;
        this.body = body;
    }

    public Method getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public HashMap getHeaders() {
        return this.headers;
    }

    public String getBody() {
        return this.body;
    }
}
