package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;

import java.io.File;

public class DeleteHandler extends Handler {

    private final String rootPath;

    public DeleteHandler(String rootPath) {
        this.rootPath = rootPath;
        setType(HandlerType.DELETE_HANDLER);
        addHandledMethod(Method.DELETE);
    }

    @Override
    public Response processRequest(Request request) {
        File file = this.getRequestedFile(request);
        file.delete();
        return this.getResponseForDeletedFile();
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return true;
    }

    private File getRequestedFile(Request request) {
        return new File(this.rootPath + "/" + request.getPath());
    }

    private Response getResponseForDeletedFile() {
        return new Response(ResponseStatus.OK, null, null);
    }
}
