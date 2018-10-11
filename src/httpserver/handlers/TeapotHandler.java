package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.utilities.Method;

public class TeapotHandler extends Handler {

    public TeapotHandler() {
        setType(HandlerType.TEAPOT_HANDLER);
        addHandledMethod(Method.GET);
    }

    @Override
    public Response processRequest(Request request) {
        return null;
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        String path = request.getPath().toLowerCase();
        return path.equals("/coffee") || path.equals("/tee");
    }
}
