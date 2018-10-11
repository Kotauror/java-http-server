package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
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
            byte[] body = "I'm a teapot".getBytes();
            return new Response(ResponseStatus.TEAPOT, body, new HashMap<>());
        } else {
            return new Response(ResponseStatus.OK, null, new HashMap<>());
        }
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        String path = request.getPath().toLowerCase();
        return path.equals("/coffee") || path.equals("/tea");
    }
}
