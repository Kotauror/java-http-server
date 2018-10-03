package httpserver.handlers;

import httpserver.request.Request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RequestRouter {

    private final ArrayList<Handler> handlers = new ArrayList<Handler>();

    public RequestRouter() {
        addHandlers(Arrays.asList(new GetHandler(),
                new PostHandler()));
    }

    public Handler findHandler(Request request) {
        for (Handler handler : handlers) {
            if (handler.handles(request)) {
                return handler;
            }
        }
        return null;
    }

    private void addHandlers(List<Handler> handlersToAdd) {
        handlers.addAll(handlersToAdd);
    }
}
