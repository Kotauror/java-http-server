package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;

import java.io.IOException;
import java.util.HashMap;

public class BasicAuthHandler extends Handler {

    public BasicAuthHandler() {
        setType(HandlerType.BASIC_AUTH_HANDLER);
        addHandledMethod(Method.GET);
    }

    @Override
    public Response processRequest(Request request) throws IOException {
        return new Response(ResponseStatus.OK, null, new HashMap<>());
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return (request.getPath().equals("/logs"));
    }
}
