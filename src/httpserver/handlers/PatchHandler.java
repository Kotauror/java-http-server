package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.utilities.Method;

import java.io.File;
import java.io.IOException;

public class PatchHandler extends Handler {

    private final String rootPath;

    public PatchHandler(String rootPath) {
        this.rootPath = rootPath;
        setType(HandlerType.PATCH_HANDLER);
        addHandledMethod(Method.PATCH);
    }

    @Override
    public Response processRequest(Request request) {
        File file;
        byte[] fileContent;
        try {
            file = this.getFileOperator().getRequestedFile(this.rootPath + request.getPath());
            fileContent = this.getFileContentConverter().getFileContentFromFile(file);
        } catch (IOException e) {
            return this.getResponseBuilder().getNotFoundResponse();
        }
        String actualShaOfRequestedFile = this.getEncoder().getHash(fileContent, "SHA-1");
        if (this.isValidPatchRequest(request, actualShaOfRequestedFile)) {
            return this.processValidPatchRequest(request, file);
        } else {
            return this.getResponseBuilder().getPreconditionFailedResponse();
        }
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return true;
    }

    private boolean isValidPatchRequest(Request request, String actualShaOfRequestedFile) {
        return (request.getHeaders().containsKey("If-Match") && request.getHeaders().get("If-Match").equals(actualShaOfRequestedFile));
    }

    private Response processValidPatchRequest(Request request, File file) {
        try {
            this.getFileOperator().writeToFile(file, request);
            return this.getResponseBuilder().getNoContentResponse(request.getBody().getBytes());
        } catch (IOException e) {
            return this.getResponseBuilder().getInternalErrorResponse();
        }
    }
}
