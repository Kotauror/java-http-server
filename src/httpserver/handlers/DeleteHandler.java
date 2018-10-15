package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
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
        String fileName = this.getFileOperator().removeKeyFromPathIfExists(request.getPath());
        String fullFilePath = this.rootPath + fileName;
        if (this.getFileOperator().fileExists(fullFilePath)) {
            this.getFileOperator().deleteFile(fileName, this.rootPath);
            return this.getResponseBuilder().getOKResponse(null, new HashMap<>());
        } else {
            return this.getResponseBuilder().getNotFoundResponse();
        }
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return true;
    }
}
