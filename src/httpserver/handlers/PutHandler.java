package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class PutHandler extends Handler{

    private final String rootPath;

    public PutHandler(String rootPath) {
        this.rootPath = rootPath;
        setType(HandlerType.PUT_HANDLER);
        addHandledMethod(Method.PUT);
    }

    @Override
    public Response processRequest(Request request) {
        File file = this.getFileOperator().getRequestedFile(request, this.rootPath);
        try {
            if (this.getFileOperator().fileExistsOnPath(request, this.rootPath)) {
                this.getFileOperator().writeToFile(file, request);
                return this.getResponseForUpdatedFile(file);
            } else {
                this.getFileOperator().writeToFile(file, request);
                return this.getResponseForCreatedFile(file);
            }
        } catch (IOException e) {
            return this.getResponseForInternalError();
        }
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

    private Response getResponseForCreatedFile(File file) throws IOException {
        byte[] body =  Files.readAllBytes(Paths.get(file.getPath()));
        return new Response(ResponseStatus.CREATED, body, new HashMap<>());
    }

    private Response getResponseForUpdatedFile(File file) throws IOException {
        byte[] body =  Files.readAllBytes(Paths.get(file.getPath()));
        return new Response(ResponseStatus.OK, body, new HashMap<>());
    }

    private Response getResponseForInternalError() {
        return new Response(ResponseStatus.INTERNAL_SERVER_ERROR, null, new HashMap<>());
    }
}
