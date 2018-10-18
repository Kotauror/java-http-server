package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseHeader;
import httpserver.utilities.Method;

import java.util.HashMap;

public class CookieHandler extends Handler {

    public CookieHandler() {
        setTypeOfHandler(HandlerType.COOKIE_HANDLER);
        addHandledMethod(Method.GET);
    }

    @Override
    public Response processRequest(Request request) {
        if (this.requestHasCookieHeader(request)) {
          return this.getResponseForUsingCookie(request);
        } else if (this.requestSetsCookieValue(request)) {
            return this.getResponseForRequestSettingCookie(request);
        } else {
            return this.getResponseBuilder().getNotFoundResponse();
        }
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return this.requestHasCookieHeader(request) || this.requestSetsCookieValue(request);
    }

    private boolean requestHasCookieHeader(Request request) {
        try {
            return request.getHeaders().get("Cookie") != null;
        } catch (NullPointerException e) {
            return false;
        }
    }

    private Response getResponseForUsingCookie(Request request) {
        byte[] body = ("mmmm " + this.getValueOfCookieHeader(request)).getBytes();
        return this.getResponseBuilder().getOKResponse(body, new HashMap<>());
    }

    private String getValueOfCookieHeader(Request request) {
        return request.getHeaders().get("Cookie").toString();
    }

    private Response getResponseForRequestSettingCookie(Request request) {
        String cookieTaste = getCookieTaste(request);
        HashMap<ResponseHeader, String> headers = this.getHeadersForCookieSettingRequest(cookieTaste);
        byte[] body = "Eat".getBytes();
        return this.getResponseBuilder().getOKResponse(body, headers);
    }

    private boolean requestSetsCookieValue(Request request) {
        try {
            return (request.getPath().toLowerCase().substring(0, 13).equals("/cookie?type="));
        } catch (StringIndexOutOfBoundsException e) {
            return false;
        }
    }

    private String getCookieTaste(Request request) {
        return request.getPath().split("=")[1];
    }

    private HashMap<ResponseHeader, String> getHeadersForCookieSettingRequest(String cookieTaste) {
        return new HashMap<ResponseHeader, String>() {{
            put(ResponseHeader.SET_COOKIE, cookieTaste);
        }};
    }
}
