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
        if (isGetRequest(request)) {
          return this.getResponseForGetRequest(request);
        }
        if (isPostRequest(request)) {
            return this.getResponseForPostRequest(request);
        }
        return new Response(ResponseStatus.OK, null, new HashMap<>());
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return request.getPath().contains("form");
    }

    private boolean isGetRequest(Request request) {
        return request.getMethod().equals(Method.GET);
    }

    private boolean isPostRequest(Request request) {
        return request.getMethod().equals(Method.POST);
    }

    private Response getResponseForGetRequest(Request request) throws IOException {
        String pathWithoutKey = this.removeKeyFromPath(request.getPath());
        if (this.requestedFileExists(pathWithoutKey)) {
            String keyFromPath = this.getKeyFromFilePath(request.getPath());
            String contentOfFile = this.getFileContentConverter().getFileContentAsString(pathWithoutKey);
            if (contentOfFile.contains(keyFromPath)) {
                return new Response(ResponseStatus.OK, contentOfFile.getBytes(), new HashMap<>());
            } else {
                return this.getNotFoundResponse();
            }
        } else {
            return this.getNotFoundResponse();
        }
    }

    private Response getResponseForPostRequest(Request request) {
        if (this.getFileOperator().fileExistsOnPath(request, this.rootPath)) {
            try {
                File file = this.getFileOperator().getRequestedFileByName(request, this.rootPath);
                this.getFileOperator().writeToFile(file, request);
                String contentOfFile = new String(this.getFileContentConverter().getFileContentFromFile(file));
                String[] partsOfFile = contentOfFile.split("=");
                HashMap<ResponseHeader, String> location = new HashMap<ResponseHeader, String>() {{
                    put(ResponseHeader.LOCATION, request.getPath() + "/" + partsOfFile[0]);
                }};
                return new Response(ResponseStatus.CREATED, null, location);
            } catch (IOException e) {
                return new Response(ResponseStatus.INTERNAL_SERVER_ERROR, null, new HashMap<>());
            }
        }
        return this.getNotFoundResponse();
    }

    private String removeKeyFromPath(String fullPath) {
        String[] pathsOfPath = fullPath.split("/");
        int lengthOfContentReference = pathsOfPath[pathsOfPath.length-1].length();
        int lengthOfPathWithoutContentReference = fullPath.length() - lengthOfContentReference;
        return fullPath.substring(0, lengthOfPathWithoutContentReference-1);
    }

    private String getKeyFromFilePath(String path) {
        String[] pathsOfPath = path.split("/");
        return pathsOfPath[pathsOfPath.length -1];
    }

    private boolean requestedFileExists(String filePathWithoutContentReference) {
        return this.getFileOperator().fileExists(filePathWithoutContentReference);
    }

    private Response getNotFoundResponse() {
        return new Response(ResponseStatus.NOT_FOUND, null, new HashMap<>());
    }
}
