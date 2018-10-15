package httpserver.handlers;

import httpserver.response.ResponseHeader;
import httpserver.response.ResponseStatus;
import httpserver.utilities.FileType;
import httpserver.utilities.Method;
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
        setType(HandlerType.POST_HANDLER);
        addHandledMethod(Method.POST);
    }

    @Override
    public Response processRequest(Request request) throws IOException {
        File file = this.getFileOperator().getRequestedFileByName(request, this.rootPath);
        if (this.getFileOperator().fileExistsOnPath(request, this.rootPath)) {
            return this.getResponseForUnProcessable();
        } else {
            this.getFileOperator().writeToFile(file, request);
            return this.getResponseForCreatingFile(file);
        }
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return true;
    }

    private Response getResponseForUnProcessable() {
        return new Response(ResponseStatus.UNPROCESSABLE, null, new HashMap<>());
    }

    private Response getResponseForCreatingFile(File file) throws IOException {
        FileType fileType = this.getFileTypeDecoder().getFileType(file.getName());
        byte[] body = Files.readAllBytes(Paths.get(file.getPath()));
        HashMap<ResponseHeader, String> headers = new HashMap<ResponseHeader, String>() {{
            put(ResponseHeader.CONTENT_TYPE, fileType.value());
        }};
        return new Response(ResponseStatus.CREATED, body, headers);
    }
}
