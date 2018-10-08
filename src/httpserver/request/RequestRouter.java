package httpserver.request;

import httpserver.handlers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RequestRouter {

    private final ArrayList<Handler> handlers = new ArrayList<Handler>();
    private final String rootPath;

    public RequestRouter(String rootPath) {
        this.rootPath = rootPath;
        addHandlers(Arrays.asList(
                new DirectoryLinksHandler(rootPath),
                new PutHandler(rootPath),
                new GetHandler(rootPath),
                new PostHandler(rootPath),
                new MethodNotAllowedHandler(rootPath),
                new DeleteHandler(rootPath),
                new HeadHandler(rootPath)));
    }

    public Handler findHandler(Request request) {
        for (Handler handler : handlers) {
            if (handler.handles(request)) {
                return handler;
            }
        }
        return new GetHandler(this.rootPath);
    }

    private void addHandlers(List<Handler> handlersToAdd) {
        handlers.addAll(handlersToAdd);
    }
}
