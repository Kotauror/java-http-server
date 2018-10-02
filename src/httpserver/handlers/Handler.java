package httpserver.handlers;

import httpserver.Method;
import httpserver.request.Request;
import java.util.ArrayList;

public abstract class Handler {

    private final ArrayList<Method> handledMethods = new ArrayList<>();
    private String typeOfHandler = "";

    public String getType() {
        return typeOfHandler;
    }

    public void setType(String type) {
        typeOfHandler = type;
    }

    public void addHandledMethod(Method method) {
        handledMethods.add(method);
    }

    public boolean handles(Request request) {
        return this.handledMethods.contains(request.getMethod());
    }
}
