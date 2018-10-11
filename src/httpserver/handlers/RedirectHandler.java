package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseHeader;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;

import java.util.HashMap;

public class RedirectHandler extends Handler {

    public RedirectHandler() {
        setType(HandlerType.REDIRECT_HANDLER);
        addHandledMethod(Method.GET);
    }

    @Override
    public Response processRequest(Request request) {
        HashMap<ResponseHeader, String> Location = new HashMap<ResponseHeader, String>() {{
            put(ResponseHeader.LOCATION, "/");
        }};
        return new Response(ResponseStatus.FOUND, null, Location);
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return request.getPath().equals("/redirect");
    }
}
