package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.utilities.Method;


public class MethodNotAllowedHandler extends Handler{

    public MethodNotAllowedHandler() {
        setTypeOfHandler(HandlerType.NOT_ALLOWED_HANDLER);
        addHandledMethod(Method.INVALID);
    }

    @Override
    public Response processRequest(Request request) {
        return this.getResponseBuilder().getNotAllowedResponse();
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return true;
    }
}
