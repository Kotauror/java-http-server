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
        if (isPutRequest(request)) {
            System.out.println(request.getMethod());
            System.out.println(request.getPath());
            System.out.println(request.getHeaders());
            System.out.println(request.getBody());
            return this.getResponseForPutRequest(request);
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

    private boolean isPutRequest(Request request) {
        return request.getMethod().equals(Method.PUT);
    }

    private Response getResponseForGetRequest(Request request) throws IOException {
        String fileName = this.removeKeyFromPath(request.getPath());
        String fullFilePath = this.rootPath + fileName;
        if (this.requestedFileExists(fullFilePath)) {
            String keyFromPath = this.getKeyFromFilePath(request.getPath());
            String contentOfFile = this.getFileContentConverter().getFileContentAsString(fullFilePath);
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
                String fullFilePath = this.rootPath + request.getPath();
                File file = this.getFileOperator().getRequestedFileByPath(fullFilePath);
                this.getFileOperator().writeToFile(file, request);
                HashMap<ResponseHeader, String> locationHeader = this.getLocationHeader(request.getPath(), request.getBody());
                return new Response(ResponseStatus.CREATED, null, locationHeader);
            } catch (IOException e) {
                return this.getInternalErrorResponse();
            }
        }
        return this.getNotFoundResponse();
    }

    private Response getResponseForPutRequest(Request request) {
        String fileName = this.removeKeyFromPath(request.getPath());
        String fullFilePath = this.rootPath + fileName;
        if (this.requestedFileExists(fullFilePath)) {
            try {
                File file = this.getFileOperator().getRequestedFileByPath(fullFilePath);
                this.getFileOperator().writeToFile(file, request);
                return new Response(ResponseStatus.OK, null, new HashMap<>());
            } catch (IOException e) {
                return this.getInternalErrorResponse();
            }
        } else {
            return this.getNotFoundResponse();
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

    private Response getNotFoundResponse() {
        return new Response(ResponseStatus.NOT_FOUND, null, new HashMap<>());
    }

    private HashMap<ResponseHeader, String> getLocationHeader(String path, String bodyOfRequest) {
        String[] partsOfFile = bodyOfRequest.split("=");
        String locationString =  path + "/" + partsOfFile[0];
        return new HashMap<ResponseHeader, String>() {{
            put(ResponseHeader.LOCATION, locationString);
        }};
    }

    private Response getInternalErrorResponse() {
        return new Response(ResponseStatus.INTERNAL_SERVER_ERROR, null, new HashMap<>());
    }
}
