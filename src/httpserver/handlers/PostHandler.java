package httpserver.handlers;

import httpserver.Method;

public class PostHandler extends Handler {

    public PostHandler() {
        setType("postHandler");
        addHandledMethod(Method.POST);
    }
}
