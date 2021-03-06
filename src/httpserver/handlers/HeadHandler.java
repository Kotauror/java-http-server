package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.request.Method;

import java.util.HashMap;

public class HeadHandler extends Handler {

    private final String rootPath;

    public HeadHandler(String rootPath) {
        this.rootPath = rootPath;
        setTypeOfHandler(HandlerType.HEAD_HANDLER);
        addHandledMethod(Method.HEAD);
    }

    @Override
    public Response processRequest(Request request) {
        return (this.getFileOperator().fileExists(this.rootPath + request.getPath())) ? this.getFullResponse() : this.getNotFoundResponse();
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return true;
    }

    private Response getFullResponse() {
        return this.getResponseBuilder().getOKResponse(null, new HashMap<>());
    }

    private Response getNotFoundResponse() {
        return this.getResponseBuilder().getNotFoundResponse();
    }
}
