package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;

public class InternalErrorHandler extends Handler {

    public InternalErrorHandler(){
        setType(HandlerType.INTERNAL_ERROR_HANDLER);
    }

    @Override
    public boolean handles(Request request) {
        return (request.getBody() != null) && (request.getBody().contains("Error in buffering"));
    }

    @Override
    public Response processRequest(Request request) {
        return this.getResponseBuilder().getInternalErrorResponse();
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return true;
    }
}
