package httpserver.request;

import httpserver.utilities.Method;

import java.util.LinkedHashMap;

public class RequestBuilder {

    public Request getRequest(Method method, String path, String httpVersion, LinkedHashMap<String, String> headers, String body) {
        return new Request(method, path, httpVersion, headers, body);
    }

    public Request getBadRequest() {
        return new Request(null, null, null, null, "Error in parsing");
    }

    public Request getRequestForInternalError() {
        return new Request(null, null, null, null, "Error in buffering");
    }
}
