package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
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
        return null;
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return true;
    }
}
