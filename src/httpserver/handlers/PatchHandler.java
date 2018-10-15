package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.utilities.Method;

import java.util.Arrays;

public class PatchHandler extends Handler {

    private final String rootPath;

    public PatchHandler(String rootPath) {
        this.rootPath = rootPath;
        setType(HandlerType.PATCH_HANDLER);
        addHandledMethods(Arrays.asList(Method.PATCH));
    }

    @Override
    public Response processRequest(Request request) {
        return this.getResponseBuilder().getPatchResponse();
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return true;
    }
}
