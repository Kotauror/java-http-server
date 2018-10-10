package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;

import java.util.HashMap;

public class InternalErrorHandler extends Handler {

    public InternalErrorHandler() {
        setType(HandlerType.INTERNAL_ERROR_HANDLER);
    }

    @Override
    public Response processRequest(Request request) {
        return new Response(ResponseStatus.INTERNAL_SERVER_ERROR, null, new HashMap<>());
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return true;
    }
}
