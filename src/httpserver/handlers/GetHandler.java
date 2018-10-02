package httpserver.handlers;

import httpserver.Method;
import httpserver.request.Request;

public class GetHandler extends Handler{

    private final Method handledMethods;
    private final String type;

    public GetHandler() {
        this.type = "getHandler";
        this.handledMethods = Method.GET;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public boolean handles(Request request) {
        return request.getMethod() == this.handledMethods;
    }
}
