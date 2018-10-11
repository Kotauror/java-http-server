package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseHeader;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;

import java.util.HashMap;

public class CookieHandler extends Handler {

    public CookieHandler() {
        setType(HandlerType.COOKIE_HANDLER);
        addHandledMethod(Method.GET);
    }

    @Override
    public Response processRequest(Request request) {
        if (this.requestHasCookieHeader(request)) {
          return this.getResponseForUsingCookie(request);
        }
        if (this.requestSetsCookieValue(request)) {
            return this.getResponseForRequestSettingCookie(request);
        }
        return new Response(ResponseStatus.NOT_FOUND, null, new HashMap<>());
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return (request.getPath().toLowerCase().contains("cookie"));
    }

    private boolean requestHasCookieHeader(Request request) {
        return request.getHeaders().get("Cookie") != null;
    }

    private Response getResponseForUsingCookie(Request request) {
        byte[] body = ("mmmm " + this.getValueOfCookieHeader(request)).getBytes();
        return new Response(ResponseStatus.OK, body, new HashMap<>());
    }

    private String getValueOfCookieHeader(Request request) {
        return request.getHeaders().get("Cookie").toString();
    }

    private Response getResponseForRequestSettingCookie(Request request) {
        String cookieTaste = getCookieTaste(request);
        HashMap<ResponseHeader, String> headers = this.getHeadersForCookieSettingRequest(cookieTaste);
        byte[] body = "Eat".getBytes();
        return new Response(ResponseStatus.OK, body, headers);
    }

    private boolean requestSetsCookieValue(Request request) {
        return (request.getPath().toLowerCase().contains("cookie?type="));
    }


    private String getCookieTaste(Request request) {
        return request.getPath().split("=")[1];
    }

    private HashMap<ResponseHeader, String> getHeadersForCookieSettingRequest(String cookieTaste) {
        return new HashMap<ResponseHeader, String>() {{
            put(ResponseHeader.COOKIE, cookieTaste);
        }};
    }
}