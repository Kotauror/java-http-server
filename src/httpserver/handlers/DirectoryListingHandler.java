package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;

import java.io.File;

public class DirectoryListingHandler extends Handler {

    private final String rootPath;

    public DirectoryListingHandler(String rootPath) {
        this.rootPath = rootPath;
        setType("directoryListingHandler");
        addHandledMethod(Method.GET);
    }

    @Override
    public Response getResponse(Request request) {
        File[] files = new File(this.rootPath).listFiles();
        StringBuilder body = new StringBuilder();
        for (File file : files) {
            body.append(file.getName());
        }
        byte [] bodyContent = body.toString().getBytes();
        return new Response(ResponseStatus.OK, bodyContent, "text/plain");
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return request.getPath().equals("/");
    }
}
