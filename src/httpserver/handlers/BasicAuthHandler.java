package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Header;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;

public class BasicAuthHandler extends Handler {

    private final String authorisedLogin;
    private final String authorisedPassword;

    public BasicAuthHandler() {
        setType(HandlerType.BASIC_AUTH_HANDLER);
        addHandledMethod(Method.GET);
        authorisedLogin = "admin";
        authorisedPassword = "hunter2";
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
        return (this.hasAuthorizationHeader(request) && this.hasValidCredentials(request));
    }

    private boolean hasAuthorizationHeader(Request request) {
        String value = (String) request.getHeaders().get("Authorization");
        return (value != null);
    }

    private boolean hasValidCredentials(Request request) {
        String[] loginValues = this.getLoginValues(request);
        return (loginValues[0].equals(this.authorisedLogin) && loginValues[1].equals(this.authorisedPassword));
    }

    private String[] getLoginValues(Request request) {
        final String authorization = (String) request.getHeaders().get("Authorization");
        String base64Credentials = authorization.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        return credentials.split(":", 2);
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
