package httpserver.handlers;

import httpserver.response.ResponseHeader;
import httpserver.utilities.FileType;
import httpserver.request.Method;
import httpserver.request.Request;
import httpserver.response.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class PostHandler extends Handler {

    private final String rootPath;

    public PostHandler(String rootPath) {
        this.rootPath = rootPath;
        setTypeOfHandler(HandlerType.POST_HANDLER);
        addHandledMethod(Method.POST);
    }

    @Override
    public Response processRequest(Request request) {
        File file = this.getFileOperator().getRequestedFile(this.rootPath + request.getPath());
        if (this.getFileOperator().fileExists(this.rootPath + request.getPath())) {
            return this.getResponseBuilder().getNotAllowedResponse();
        } else {
            try {
                this.getFileOperator().writeToFile(file, request.getBody());
                return this.getResponseForCreatingFile(file, request);
            } catch (IOException e) {
                return this.getResponseBuilder().getInternalErrorResponse();
            }
        }
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return true;
    }

    private Response getResponseForCreatingFile(File file, Request request) {
        try {
            byte[] body = Files.readAllBytes(Paths.get(file.getPath()));
            HashMap<ResponseHeader, String> headers = this.getHeaders(file, request);
            return this.getResponseBuilder().getCreatedResponse(body, headers);
        } catch (IOException e) {
            return this.getResponseBuilder().getInternalErrorResponse();
        }

    }

    private HashMap<ResponseHeader, String> getHeaders(File file, Request request) {
        FileType fileType = this.getFileTypeDecoder().getFileType(file.getName());
        String locationString = this.getLocationString(request);
        return new HashMap<ResponseHeader, String>() {{
            put(ResponseHeader.CONTENT_TYPE, fileType.getType());
            put(ResponseHeader.LOCATION, locationString);
        }};
    }

    private String getLocationString(Request request) {
        String[] partsOfFile = request.getBody().split("=");
        return request.getPath() + "/" + partsOfFile[0];
    }
}
