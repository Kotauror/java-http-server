package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;

import java.io.IOException;

public class OptionHandler extends Handler{

    private final String rootPath;

    public OptionHandler(String rootPath) {
        this.rootPath = rootPath;
        setType(HandlerType.OPTION_HANDLER);
        addHandledMethod(Method.OPTIONS);
    }

    @Override
    public Response processRequest(Request request) throws IOException {
        if (this.requestLogs(request)) {
            String[] allowedMethods = new String[] {
                    "GET", "HEAD", "OPTIONS"
            };
            return new Response(ResponseStatus.OK, allowedMethods);
        } else {
            String[] allowedMethods = new String[] {
                    "GET", "HEAD", "OPTIONS", "PUT", "DELETE"
            };
            return new Response(ResponseStatus.OK, allowedMethods);
        }
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return true;
    }

    private boolean requestLogs(Request request) {
        return (request.getPath().equals("/logs"));
    }
}
