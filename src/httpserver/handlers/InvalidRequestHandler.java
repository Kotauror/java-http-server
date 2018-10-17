package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;

public class InvalidRequestHandler extends Handler{

    public InvalidRequestHandler(){
        setType(HandlerType.INVALID_REQUEST_HANDLER);
    }

    @Override
    public boolean handles(Request request) {
        if (request.getBody() != null) {
            return (request.getBody().contains("Error in parsing"));
        }
        return false;
    }

    @Override
    public Response processRequest(Request request) {
        return this.getResponseBuilder().getBadRequestResponse();
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return true;
    }
}
