package httpserver.handlers;

import httpserver.utilities.Method;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GetHandler extends Handler{

    private final String rootPath;

    public GetHandler(String rootPath) {
        this.rootPath = rootPath;
        setType("getHandler");
        addHandledMethod(Method.GET);
    }

    @Override
    public Response getResponse(Request request) throws IOException {
        return (pathExists(request)) ? this.getFullResponse(request) : this.getNotFoundResponse();
    }

    private boolean pathExists(Request request) {
        return Files.exists(Paths.get(rootPath + request.getPath()));
    }

    private Response getNotFoundResponse() {
        Response response = new Response();
        response.setStatus(ResponseStatus.NOT_FOUND);
        return response;
    }

    private Response getFullResponse(Request request) throws IOException {
        File file = new File(rootPath + request.getPath());
        byte[] fileContentInBytes = this.getFileContentConverter().getFileContent(file);
        String fileType = this.getFileTypeDecoder().getFileType(file.getName());

        Response response = new Response();
        response.setStatus(ResponseStatus.OK);
        response.setBodyContent(fileContentInBytes);
        response.setContentTypeHeader(fileType);
        return response;
    }
}
