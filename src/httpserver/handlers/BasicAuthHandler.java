package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Header;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;

import java.util.HashMap;

public class BasicAuthHandler extends Handler {

    public BasicAuthHandler() {
        setType(HandlerType.BASIC_AUTH_HANDLER);
        addHandledMethod(Method.GET);
    }

    @Override
    public Response processRequest(Request request) {
        if (this.isAuthorisedRequest(request)) {
            if (this.requestHasAllowedMethod(request)) {
                return new Response(ResponseStatus.OK, null, new HashMap<>());
            } else {
                return this.getResponseForUnallowedMethod();
            }
        } else {
            return this.getResponseForUnauthorizedRequest();
        }
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return (request.getPath().equals("/logs"));
    }

    private boolean isAuthorisedRequest(Request request) {
        String value = (String) request.getHeaders().get("Authorization");
        return (value != null);
    }

    private Response getResponseForUnallowedMethod() {
        return new Response(ResponseStatus.NOT_ALLOWED, null, new HashMap<>());
    }

    private Response getResponseForUnauthorizedRequest() {
        HashMap<Header, String> header = this.getHeaderForUnauthorizedRequest();
        return new Response(ResponseStatus.UNAUTHORIZED, null, header);
    }

    private HashMap<Header, String> getHeaderForUnauthorizedRequest() {
        return new HashMap<Header, String>() {{
            put(Header.AUTHENTICATE, "Basic realm=\"Access to staging site\"");
        }};
    }
}
