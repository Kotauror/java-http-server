package httpserver.handlers;

import httpserver.response.ResponseHeader;
import httpserver.response.RangeRequestResponder;
import httpserver.utilities.FileType;
import httpserver.utilities.Method;
import httpserver.request.Request;
import httpserver.response.Response;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class GetHandler extends Handler{

    private final String rootPath;
    private final RangeRequestResponder rangeRequestResponder;

    public GetHandler(String rootPath) {
        this.rootPath = rootPath;
        this.rangeRequestResponder = new RangeRequestResponder(this.rootPath, this.getFileOperator(), this.getFileContentConverter(), this.getFileTypeDecoder());
        setType(HandlerType.GET_HANDLER);
        addHandledMethod(Method.GET);
    }

    @Override
    public Response processRequest(Request request) {
        if (this.getFileOperator().fileExists(this.rootPath + request.getPath())) {
            try {
                return this.getResponse(request);
            } catch (IOException e) {
                return this.getResponseBuilder().getInternalErrorResponse();
            }
        } else {
            return this.getResponseBuilder().getNotFoundResponse();
        }
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
       return true;
    }

    private Response getResponse(Request request) throws IOException {
        if (isRangeRequest(request)) {
            return this.rangeRequestResponder.getRangeResponse(request);
        } else {
            return getFullResponse(request);
        }
    }

    private Response getFullResponse(Request request) throws IOException {
        File file = this.getFileOperator().getRequestedFile(request.getPath(), this.rootPath);
        byte[] body = this.getFileContentConverter().getFileContentFromFile(file);
        FileType fileType = this.getFileTypeDecoder().getFileType(file.getName());
        HashMap<ResponseHeader, String> headers = new HashMap<ResponseHeader, String>() {{
            put(ResponseHeader.CONTENT_TYPE, fileType.value());
        }};
        return this.getResponseBuilder().getOKResponse(body, headers);
    }

    private boolean isRangeRequest(Request request) {
        return (request.getHeaders().containsKey("Range"));
    }
}
