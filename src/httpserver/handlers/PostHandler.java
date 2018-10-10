package httpserver.handlers;

import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;
import httpserver.request.Request;
import httpserver.response.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PostHandler extends Handler {

    private final String rootPath;

    public PostHandler(String rootPath) {
        this.rootPath = rootPath;
        setType(HandlerType.POST_HANDLER);
        addHandledMethod(Method.POST);
    }

    @Override
    public Response processRequest(Request request) throws IOException {
        File file = this.getFileOperator().getRequestedFile(request, this.rootPath);
        if (this.getFileOperator().fileExistsOnPath(request, this.rootPath)) {
            return this.getResponseForNotAllowedMethod();
        } else {
            this.getFileOperator().writeToFile(file, request);
            return this.getResponseForCreatingFile(file);
        }
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return true;
    }

    private Response getResponseForNotAllowedMethod() {
        return new Response(ResponseStatus.NOT_ALLOWED);
    }

    private Response getResponseForCreatingFile(File file) throws IOException {
        String fileType = this.getFileTypeDecoder().getFileType(file.getName());
        return new Response(ResponseStatus.CREATED, Files.readAllBytes(Paths.get(file.getPath())), fileType);
    }
}
