package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.server.Logger;
import httpserver.server.LoggerHeader;
import httpserver.utilities.AuthenticationCredentials;
import httpserver.request.Method;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class BasicAuthHandler extends Handler {

    private final Logger logger;

    public BasicAuthHandler(Logger logger) {
        this.logger = logger;
        setTypeOfHandler(HandlerType.BASIC_AUTH_HANDLER);
        addHandledMethod(Method.GET);
    }

    @Override
    public Response processRequest(Request request) {
        if (this.isAuthorisedRequest(request)) {
            if (this.requestHasAllowedMethod(request)) {
                return this.getResponseForSuccessfulBasicAuth();
            } else {
                return this.getResponseBuilder().getNotAllowedResponse();
            }
        } else {
            return this.getResponseBuilder().getUnauthorizedResponse();
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
        return (loginValues[0].equals(AuthenticationCredentials.LOGIN.getValue()) &&
                loginValues[1].equals(AuthenticationCredentials.PASSWORD.getValue()));
    }

    private String[] getLoginValues(Request request) {
        final String authorization = (String) request.getHeaders().get("Authorization");
        String base64Credentials = authorization.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        return credentials.split(":", 2);
    }

    private Response getResponseForSuccessfulBasicAuth() {
        StringBuilder bodyOfResponse = new StringBuilder();
        bodyOfResponse.append("GET /logs HTTP/1.1 PUT /these HTTP/1.1 HEAD /requests HTTP/1.1\n");
        for (Map.Entry<LoggerHeader, String> log : this.logger.getLogs().entrySet()) {
            bodyOfResponse.append(log.getKey().toString() + ": " + log.getValue() + "\n");
        }
        return this.getResponseBuilder().getOKResponse(bodyOfResponse.toString().getBytes(), new HashMap<>());
    }
}
