package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;

import java.util.HashMap;

public class DeleteHandler extends Handler {

    private final String rootPath;

    public DeleteHandler(String rootPath) {
        this.rootPath = rootPath;
        setType(HandlerType.DELETE_HANDLER);
        addHandledMethod(Method.DELETE);
    }

    @Override
    public Response processRequest(Request request) {
        this.getFileOperator().deleteFile(request, this.rootPath);
        return this.getResponseForDeletedFile();
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return true;
    }

    private Response getResponseForDeletedFile() {
        return new Response(ResponseStatus.OK, null, new HashMap<>());
    }
}
