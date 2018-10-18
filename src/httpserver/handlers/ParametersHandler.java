package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.request.Method;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

public class ParametersHandler extends Handler {

    public ParametersHandler() {
        setTypeOfHandler(HandlerType.PARAMETERS_HANDLER);
        addHandledMethod(Method.GET);
    }

    @Override
    public Response processRequest(Request request) {
        try {
            String bodyOfResponse = this.decodeRequestPath(request.getPath());
            return this.getResponseBuilder().getOKResponse(bodyOfResponse.getBytes(), new HashMap<>());
        } catch (UnsupportedEncodingException e) {
            return this.getResponseBuilder().getNotFoundResponse();
        }
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return request.getPath().length() > 12 && request.getPath().substring(0, 12).equals("/parameters?");
    }

    private String decodeRequestPath(String requestPath) throws UnsupportedEncodingException {
        String pathWithoutParametersKey = requestPath.substring(12, requestPath.length());
        String[] keyValuePairs = pathWithoutParametersKey.split("&");
        StringBuilder bodyContent = new StringBuilder();
        for (String keyValuePair : keyValuePairs) {
            String[] keyAndValue = keyValuePair.split("=");
            bodyContent.append(keyAndValue[0]);
            bodyContent.append(" = ");
            bodyContent.append(decode(keyAndValue[1]) + "\n");
        }
        return new String(bodyContent);
    }

    private String decode(String encodedString) throws UnsupportedEncodingException {
        return URLDecoder.decode(encodedString, "UTF-8");
    }
}
