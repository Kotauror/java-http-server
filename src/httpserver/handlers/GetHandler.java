package httpserver.handlers;

import httpserver.Method;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;

public class GetHandler extends Handler{

    public GetHandler() {
        setType("getHandler");
        addHandledMethod(Method.GET);
    }

    @Override
    public Response getResponse(Request request) {
        Response response = new Response();
        response.setStatus(ResponseStatus.OK);
        return response;
    };
}
