package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;

public class PutHandler extends Handler{

    private final String rootPath;

    public PutHandler(String rootPath) {
        this.rootPath = rootPath;
        setType(HandlerType.PUT_HANDLER);
        addHandledMethod(Method.PUT);
    }

    @Override
    public Response getResponse(Request request) {
        return new Response(ResponseStatus.CREATED, null, null);
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        String pathFromRequest = request.getPath();
        if (null != pathFromRequest && pathFromRequest.length() > 0 ) {
            int indexOfLastSlash = pathFromRequest.lastIndexOf("/");
            if (indexOfLastSlash != -1) {
                String directoryPath = pathFromRequest.substring(0, indexOfLastSlash);
                return this.rootPath.contains(directoryPath);
            }
        }
        return false;
    }
}
