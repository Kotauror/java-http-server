package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;

public class HeadHandler extends Handler {

    private final String rootPath;

    public HeadHandler(String rootPath) {
        this.rootPath = rootPath;
        setType(HandlerType.HEAD_HANDLER);
        addHandledMethod(Method.HEAD);
    }

    @Override
    public Response processRequest(Request request) {
        return (this.getFileOperator().fileExistsOnPath(request, this.rootPath)) ? this.getFullResponse(request) : this.getNotFoundResponse();
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return true;
    }

    private Response getFullResponse(Request request) {
        return new Response(ResponseStatus.OK, null, null);
    }

    private Response getNotFoundResponse() {
        return new Response(ResponseStatus.NOT_FOUND, null, null);
    }
}
