package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;

public class InvalidRequestHandler extends Handler{

    public InvalidRequestHandler(){
        setTypeOfHandler(HandlerType.INVALID_REQUEST_HANDLER);
    }

    @Override
    public boolean handles(Request request) {
        return (request.getBody() != null) && request.getBody().contains("Error in parsing");
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
