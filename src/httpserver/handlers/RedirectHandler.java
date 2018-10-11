package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;

import java.util.HashMap;

public class RedirectHandler extends Handler {

    public RedirectHandler() {
        setType(HandlerType.REDIRECT_HANDLER);
        addHandledMethod(Method.GET);
    }

    @Override
    public Response processRequest(Request request) {
        return new Response(ResponseStatus.FOUND, null, new HashMap<>());
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return request.getPath().equals("/redirect");
    }
}
