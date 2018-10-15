package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.utilities.Method;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

public class ParametersHandler extends Handler {

    public ParametersHandler() {
        setType(HandlerType.PARAMETERS_HANDER);
        addHandledMethod(Method.GET);
    }

    @Override
    public Response processRequest(Request request) throws UnsupportedEncodingException {
        String bodyOfResponse = this.decodeRequestPath(request.getPath());
        return this.getResponseBuilder().getOKResponse(bodyOfResponse.getBytes(), new HashMap<>());
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return request.getPath().length() > 12 && request.getPath().substring(0, 12).equals("/parameters?");
    }

    private String decodeRequestPath(String requestPath) throws UnsupportedEncodingException {
        String pathWithoutParametersKey = requestPath.substring(12, requestPath.length());
        StringBuilder bodyContent = new StringBuilder();
        String[] variableAndCode = pathWithoutParametersKey.split("=");
        bodyContent.append(variableAndCode[0]);
        bodyContent.append(" = ");
        bodyContent.append(decode(variableAndCode[1]));
        return new String(bodyContent);
    }

    private String decode(String encodedString) throws UnsupportedEncodingException {
        return URLDecoder.decode(encodedString, "UTF-8");
    }
}
