package httpserver.handlers;

import httpserver.Method;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;

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
    public Response getResponse(Request request) {
        if (pathExists(request)) {
            Response response = new Response();
            response.setStatus(ResponseStatus.OK);
            return response;
        } else {
            Response response = new Response();
            response.setStatus(ResponseStatus.NOT_FOUND);
            return response;
        }
    };

    public boolean pathExists(Request request) {
        return Files.exists(Paths.get(rootPath + request.getPath()));
    }
}
