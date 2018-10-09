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
        setType(HandlerType.GET_HANDLER);
        addHandledMethod(Method.GET);
    }

    @Override
    public Response processRequest(Request request) throws IOException {
        return (this.getFileOperator().fileExistsOnPath(request, this.rootPath)) ? this.getResponse(request) : this.getNotFoundResponse();
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
       return true;
    }

    private Response getNotFoundResponse() {
        return new Response(ResponseStatus.NOT_FOUND);
    }

    private Response getResponse(Request request) throws IOException {
        if (isRangeRequest(request)) {
            return this.getNotFoundResponse();
        } else {
            return getFullResponse(request);
        }
    }

    private Response getFullResponse(Request request) throws IOException {
        File file = this.getFileOperator().getRequestedFile(request, this.rootPath);
        byte[] fileContentInBytes = this.getFileContentConverter().getFileContent(file);
        String fileType = this.getFileTypeDecoder().getFileType(file.getName());

        return new Response(ResponseStatus.OK, fileContentInBytes, fileType);
    }

    private boolean isRangeRequest(Request request) {
        return (request.getHeaders().containsKey("Range"));
    }
}
