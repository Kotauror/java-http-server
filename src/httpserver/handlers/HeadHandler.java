package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;

public class HeadHandler extends Handler {

    private final String rootPath;

    public HeadHandler(String rootPath) {
        this.rootPath = rootPath;
        setType("headHandler");
        addHandledMethod(Method.HEAD);
    }

    @Override
    public Response getResponse(Request request) {
        return (fileExistsOnPath(request, this.rootPath)) ? this.getFullResponse(request) : this.getNotFoundResponse();
    }

    @Override
    public boolean coversPath(Request request) {
        return true;
    }

    private Response getFullResponse(Request request) {
        Response response = new Response(ResponseStatus.OK, null, null);
        return response;
    }

    private Response getNotFoundResponse() {
        return new Response(ResponseStatus.NOT_FOUND, null, null);
    }
}
