package httpserver.handlers;

import httpserver.Method;
import httpserver.request.Request;

public abstract class Handler {

    private final String type;
    private final Method handledMethods;

    public Handler() {
        this.type = "";
        this.handledMethods = null;
    }

    public abstract boolean handles(Request request);

    public abstract String getType();
}
