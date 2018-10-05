package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.utilities.Method;

public class DirectoryListingHandler extends Handler {

    public DirectoryListingHandler() {
        setType("directoryListingHandler");
        addHandledMethod(Method.GET);
    }

    @Override
    public Response getResponse(Request request) {
        return null;
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return request.getPath().equals("/");
    }
}
