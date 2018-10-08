package httpserver.handlers;

import httpserver.utilities.Method;
import httpserver.request.Request;
import httpserver.response.Response;

public class PostHandler extends Handler {

    public PostHandler() {
        setType(HandlerType.POST_HANDLER);
        addHandledMethod(Method.POST);
    }

    @Override
    public Response processRequest(Request request) {
        return null;
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return true;
    }
}
