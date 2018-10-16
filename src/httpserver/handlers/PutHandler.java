package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
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
        String fileName = this.getFileOperator().removeKeyFromPathIfExists(request.getPath());
        File file = this.getFileOperator().getRequestedFile(this.rootPath + fileName);
        try {
            if (this.getFileOperator().fileExists(this.rootPath + fileName)) {
                this.getFileOperator().writeToFile(file, request);
                return this.getResponseForUpdatedFile(file);
            } else {
                this.getFileOperator().writeToFile(file, request);
                return this.getResponseForCreatedFile(file);
            }
        } catch (IOException e) {
            return this.getResponseBuilder().getInternalErrorResponse();
        }
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return true;
    }

    private Response getResponseForCreatedFile(File file) throws IOException {
        byte[] body =  Files.readAllBytes(Paths.get(file.getPath()));
        return this.getResponseBuilder().getCreatedResponse(body, new HashMap<>());
    }

    private Response getResponseForUpdatedFile(File file) throws IOException {
        byte[] body =  Files.readAllBytes(Paths.get(file.getPath()));
        return this.getResponseBuilder().getOKResponse(body, new HashMap<>());
    }
}
