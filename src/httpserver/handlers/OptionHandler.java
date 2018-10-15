package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.ResponseHeader;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class OptionHandler extends Handler{


    public OptionHandler() {
        setType(HandlerType.OPTION_HANDLER);
        addHandledMethod(Method.OPTIONS);
    }

    @Override
    public Response processRequest(Request request) {
        ArrayList<String> allowedMethods = this.getAllowedMethods(request);
        String allowedMethodsString = this.turnArrayListIntoString(allowedMethods);
        HashMap<ResponseHeader, String> headers = new HashMap<ResponseHeader, String>() {{
            put(ResponseHeader.ALLOW, allowedMethodsString);
        }};
        return this.getResponseBuilder().getOKResponse(null, headers);
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return true;
    }

    private ArrayList<String> getAllowedMethods(Request request) {
        ArrayList<String> allowedMethods = new ArrayList<>(Arrays.asList(Method.GET.toString(), Method.HEAD.toString(), Method.OPTIONS.toString()));
        if (!(this.requestLogs(request))) {
            allowedMethods.add(Method.PUT.toString());
            allowedMethods.add(Method.DELETE.toString());
        }
        return allowedMethods;
    }

    private boolean requestLogs(Request request) {
        return (request.getPath().equals("/logs"));
    }

    private String turnArrayListIntoString(ArrayList<String> allowedMethods) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < allowedMethods.size() -1; i++) {
            stringBuilder.append(allowedMethods.get(i) + ",");
        }
        stringBuilder.append(allowedMethods.get(allowedMethods.size()-1));
        return stringBuilder.toString();
    }
}
