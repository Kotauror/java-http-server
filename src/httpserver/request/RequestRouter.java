package httpserver.request;

import httpserver.handlers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RequestRouter {

    private final ArrayList<Handler> handlers = new ArrayList<Handler>();

    public RequestRouter(String rootPath) {
        addHandlers(Arrays.asList(
                new DirectoryListingHandler(rootPath),
                new GetHandler(rootPath),
                new PostHandler(),
                new HeadHandler(rootPath)));
    }

    public Handler findHandler(Request request) {
        for (Handler handler : handlers) {
            if (handler.handles(request)) {
                return handler;
            }
        }
        return new InternalErrorHandler();
    }

    private void addHandlers(List<Handler> handlersToAdd) {
        handlers.addAll(handlersToAdd);
    }
}
