package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;

public class InternalErrorHandler extends Handler {

    public InternalErrorHandler() {
        setType("internalErrorHandler");
    }

    @Override
    public Response getResponse(Request request) {
        return new Response(ResponseStatus.INTERNAL_SERVER_ERROR, null, null);
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return true;
    }
}
