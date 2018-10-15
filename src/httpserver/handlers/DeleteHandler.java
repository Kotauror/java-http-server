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
        String fileName = this.removeKeyFromPathIfItExists(request.getPath());
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

    private String removeKeyFromPathIfItExists(String fullPath) {
        String[] pathsOfPath = fullPath.split("/");
        if (pathsOfPath.length == 2) {
            return "/" + pathsOfPath[1];
        } else {
            int lengthOfKeyInPath = pathsOfPath[pathsOfPath.length-1].length();
            int lengthOfPathWithoutKey = fullPath.length() - lengthOfKeyInPath;
            return fullPath.substring(0, lengthOfPathWithoutKey-1);
        }
    }
}
