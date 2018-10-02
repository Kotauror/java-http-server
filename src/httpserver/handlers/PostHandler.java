package httpserver.handlers;

import httpserver.Method;
import httpserver.request.Request;

public class PostHandler extends Handler {

    private final Method handledMethods;
    private final String type;

    public PostHandler() {
        this.type = "postHandler";
        this.handledMethods = Method.POST;
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
