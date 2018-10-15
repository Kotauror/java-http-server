package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.utilities.Method;

import java.util.HashMap;

public class TeapotHandler extends Handler {

    public TeapotHandler() {
        setType(HandlerType.TEAPOT_HANDLER);
        addHandledMethod(Method.GET);
    }

    @Override
    public Response processRequest(Request request) {
        String path = request.getPath().toLowerCase();
        if (path.equals("/coffee")) {
            return this.getResponseBuilder().getTeapotResponse();
        } else {
            return this.getResponseBuilder().getOKResponse(null, new HashMap<>());
        }
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        String path = request.getPath().toLowerCase();
        return path.equals("/coffee") || path.equals("/tea");
    }
}
