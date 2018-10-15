package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseHeader;
import httpserver.response.ResponseStatus;
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
        String fileName = this.removeKeyFromPath(request.getPath());
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
        File file = this.getFileOperator().getRequestedFileByName(request, this.rootPath);
        String fullFilePath = this.rootPath + request.getPath();
        if (this.requestedFileExists(fullFilePath)) {
            return this.getResponseBuilder().getNotAllowedResponse();
        } else {
            try {
                this.getFileOperator().writeToFile(file, request);
                HashMap<ResponseHeader, String> locationHeader = this.getLocationHeader(request.getPath(), request.getBody());
                return this.getResponseBuilder().getCreatedResponse(null, locationHeader);
            } catch (IOException e) {
                return this.getResponseBuilder().getInternalErrorResponse();
            }
        }
    }

    private Response handlePut(Request request) {
        String fileName = this.removeKeyFromPath(request.getPath());
        String fullFilePath = this.rootPath + fileName;
            try {
                File file = this.getFileOperator().getRequestedFileByPath(fullFilePath);
                this.getFileOperator().writeToFile(file, request);
                return this.getResponseBuilder().getOKResponse(null, new HashMap<>());
            } catch (IOException e) {
                return this.getResponseBuilder().getInternalErrorResponse();
            }
    }

    private Response handleDelete(Request request) {
        String fileName = this.removeKeyFromPath(request.getPath());
        String fullFilePath = this.rootPath + fileName;
        if (this.requestedFileExists(fullFilePath)) {
            File file = this.getFileOperator().getRequestedFileByPath(fullFilePath);
            file.delete();
            return this.getResponseBuilder().getOKResponse(null, new HashMap<>());
        } else {
            return this.getResponseBuilder().getNotFoundResponse();
        }
    }

    private String removeKeyFromPath(String fullPath) {
        String[] pathsOfPath = fullPath.split("/");
        int lengthOfKeyInPath = pathsOfPath[pathsOfPath.length-1].length();
        int lengthOfPathWithoutKey = fullPath.length() - lengthOfKeyInPath;
        return fullPath.substring(0, lengthOfPathWithoutKey-1);
    }

    private String getKeyFromFilePath(String path) {
        String[] pathsOfPath = path.split("/");
        return pathsOfPath[pathsOfPath.length -1];
    }

    private boolean requestedFileExists(String fullFilePath) {
        return this.getFileOperator().fileExists(fullFilePath);
    }


    private HashMap<ResponseHeader, String> getLocationHeader(String path, String bodyOfRequest) {
        String[] partsOfFile = bodyOfRequest.split("=");
        String locationString =  path + "/" + partsOfFile[0];
        return new HashMap<ResponseHeader, String>() {{
            put(ResponseHeader.LOCATION, locationString);
        }};
    }
}
