package httpserver.request;

import httpserver.handlers.*;
import httpserver.server.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RequestRouter {

    private final ArrayList<Handler> handlers = new ArrayList<Handler>();
    private final String rootPath;

    public RequestRouter(String rootPath, Logger logger) {
        this.rootPath = rootPath;
        addHandlers(Arrays.asList(
                new PatchHandler(rootPath),
                new InvalidRequestHandler(),
                new InternalErrorHandler(),
                new ParametersHandler(),
                new FormHandler(rootPath),
                new RedirectHandler(),
                new CookieHandler(),
                new TeapotHandler(),
                new BasicAuthHandler(),
                new DirectoryLinksHandler(rootPath),
                new PutHandler(rootPath),
                new GetHandler(rootPath),
                new PostHandler(rootPath),
                new MethodNotAllowedHandler(),
                new DeleteHandler(rootPath),
                new OptionHandler(),
                new HeadHandler(rootPath)));
    }

    public Handler findHandler(Request request) {
        for (Handler handler : handlers) {
            if (handler.handles(request)) {
                return handler;
            }
        }
        return new MethodNotAllowedHandler();
    }

    private void addHandlers(List<Handler> handlersToAdd) {
        handlers.addAll(handlersToAdd);
    }
}
