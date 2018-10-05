package httpserver.handlers;

import httpserver.utilities.Method;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;

import java.io.File;
import java.io.IOException;

public class GetHandler extends Handler{

    private final String rootPath;

    public GetHandler(String rootPath) {
        this.rootPath = rootPath;
        setType("getHandler");
        addHandledMethod(Method.GET);
    }

    @Override
    public Response getResponse(Request request) throws IOException {
        return (pathExists(request, this.rootPath)) ? this.getFullResponse(request) : this.getNotFoundResponse();
    }

    private Response getNotFoundResponse() {
        return new Response(ResponseStatus.NOT_FOUND, null, null);
    }

    private Response getFullResponse(Request request) throws IOException {
        File file = new File(rootPath + request.getPath());
        byte[] fileContentInBytes = this.getFileContentConverter().getFileContent(file);
        String fileType = this.getFileTypeDecoder().getFileType(file.getName());

        return new Response(ResponseStatus.OK, fileContentInBytes, fileType);
    }
}
