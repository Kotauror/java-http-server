package httpserver.handlers;

import httpserver.utilities.Method;
import httpserver.request.Request;
import httpserver.response.Response;

public class PostHandler extends Handler {

    public PostHandler() {
        setType("postHandler");
        addHandledMethod(Method.POST);
    }

    @Override
    public Response getResponse(Request request) {
        return null;
    }
}
