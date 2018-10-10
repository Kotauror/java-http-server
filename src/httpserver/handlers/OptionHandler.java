package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;

import java.util.ArrayList;
import java.util.Arrays;

public class OptionHandler extends Handler{


    public OptionHandler() {
        setType(HandlerType.OPTION_HANDLER);
        addHandledMethod(Method.OPTIONS);
    }

    @Override
    public Response processRequest(Request request) {
        ArrayList<String> allowedMethods = new ArrayList<>(Arrays.asList(Method.GET.toString(), Method.HEAD.toString(), Method.OPTIONS.toString()));
        if (!(this.requestLogs(request))) {
            allowedMethods.add(Method.PUT.toString());
            allowedMethods.add(Method.DELETE.toString());
        }
        String allowedMethodsString = this.turnArrayListIntoString(allowedMethods);
        return new Response(ResponseStatus.OK, allowedMethodsString);
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return true;
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
