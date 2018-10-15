package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseHeader;
import httpserver.utilities.Method;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class FormHandler extends Handler {

    private final String rootPath;

    public FormHandler(String rootPath) {
        this.rootPath = rootPath;
        setType(HandlerType.FORM_HANDLER);
        addHandledMethods(Arrays.asList(Method.GET, Method.POST, Method.PUT, Method.DELETE));
    }

    @Override
    public Response processRequest(Request request) throws IOException {
        switch (request.getMethod()) {
            case GET:
                return this.handleGet(request);
            case POST:
                return this.handlePost(request);
            case PUT:
                return this.handlePut(request);
            case DELETE:
                return this.handleDelete(request);
            default:
                return this.getResponseBuilder().getNotFoundResponse();
        }
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return request.getPath().contains("form");
    }

    private Response handleGet(Request request) throws IOException {
        String fileName = this.getFileOperator().removeKeyFromPathIfExists(request.getPath());
        String fullFilePath = this.rootPath + fileName;
        if (this.requestedFileExists(fullFilePath)) {
            String keyFromPath = this.getKeyFromFilePath(request.getPath());
            String contentOfFile = this.getFileContentConverter().getFileContentAsString(fullFilePath);
            if (contentOfFile.contains(keyFromPath)) {
                return this.getResponseBuilder().getOKResponse(contentOfFile.getBytes(), new HashMap<>());
            } else {
                return this.getResponseBuilder().getNotFoundResponse();
            }
        } else {
            return this.getResponseBuilder().getNotFoundResponse();
        }
    }

    private Response handlePost(Request request) {
        PostHandler postHandler = new PostHandler(this.rootPath);
        return postHandler.processRequest(request);
    }

    private Response handlePut(Request request) {
        PutHandler putHandler = new PutHandler(this.rootPath);
        return putHandler.processRequest(request);
    }

    private Response handleDelete(Request request) {
        DeleteHandler deleteHandler = new DeleteHandler(this.rootPath);
        return deleteHandler.processRequest(request);
    }

    private String getKeyFromFilePath(String path) {
        String[] pathsOfPath = path.split("/");
        return pathsOfPath[pathsOfPath.length -1];
    }

    private boolean requestedFileExists(String fullFilePath) {
        return this.getFileOperator().fileExists(fullFilePath);
    }
}
