package httpserver.handlers;

import httpserver.Method;

public class GetHandler extends Handler{

    public GetHandler() {
        setType("getHandler");
        addHandledMethod(Method.GET);
    }
}
