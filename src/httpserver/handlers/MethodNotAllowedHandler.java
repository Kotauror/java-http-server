package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;


public class MethodNotAllowedHandler extends Handler{

    private final String rootPath;

    public MethodNotAllowedHandler(String rootPath) {
        this.rootPath = rootPath;
        setType(HandlerType.NOT_ALLOWED_HANDLER);
        addHandledMethod(Method.INVALID);
    }

    @Override
    public Response processRequest(Request request) {
        return new Response(ResponseStatus.NOT_ALLOWED, null, null);
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return true;
    }
}
