package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;

import java.nio.file.Files;
import java.nio.file.Paths;

public class HeadHandler extends Handler {

    private final String rootPath;

    public HeadHandler(String rootPath) {
        this.rootPath = rootPath;
        setType("headHandler");
        addHandledMethod(Method.HEAD);
    }

    @Override
    public Response getResponse(Request request) {
        return (pathExists(request)) ? this.getFullResponse(request) : this.getNotFoundResponse();
    }

    private boolean pathExists(Request request) {
        return Files.exists(Paths.get(rootPath + request.getPath()));
    }

    private Response getFullResponse(Request request) {
        Response response = new Response(ResponseStatus.OK, null, null);
        return response;
    }

    private Response getNotFoundResponse() {
        return new Response(ResponseStatus.NOT_FOUND, null, null);
    }
}
