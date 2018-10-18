package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.utilities.Method;

public class RedirectHandler extends Handler {

    public RedirectHandler() {
        setTypeOfHandler(HandlerType.REDIRECT_HANDLER);
        addHandledMethod(Method.GET);
    }

    @Override
    public Response processRequest(Request request) {
        return this.getResponseBuilder().getFoundResponse("/");
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return request.getPath().equals("/redirect");
    }
}
