package httpserver.handlers;

import httpserver.response.ResponseBuilder;
import httpserver.utilities.*;
import httpserver.request.Request;
import httpserver.response.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Handler {

    private static final FileContentConverter fileContentConverter = new FileContentConverter();
    private static final FileTypeDecoder fileTypeDecoder = new FileTypeDecoder();
    private static final FileOperator fileOperator = new FileOperator();
    private static final ResponseBuilder responseBuilder = new ResponseBuilder();
    private static final Encoder encoder = new Encoder();
    private final ArrayList<Method> handledMethods = new ArrayList<>();
    private HandlerType typeOfHandler = null;

    public abstract Response processRequest(Request request) throws IOException;

    public abstract boolean coversPathFromRequest(Request request);

    public HandlerType getType() {
        return typeOfHandler;
    }

    public void setTypeOfHandler(HandlerType handlerType) {
        typeOfHandler = handlerType;
    }

    public void addHandledMethod(Method method) {
        handledMethods.add(method);
    }

    public void addHandledMethods(List<Method> methods) { handledMethods.addAll(methods); }

    public FileContentConverter getFileContentConverter() {
        return fileContentConverter;
    }

    public FileTypeDecoder getFileTypeDecoder() {
        return fileTypeDecoder;
    }

    public FileOperator getFileOperator() {
        return fileOperator;
    }

    public ResponseBuilder getResponseBuilder() { return responseBuilder; }

    public Encoder getEncoder() { return encoder; }

    public boolean handles(Request request) {
        return (this.handledMethods.contains(request.getMethod()) && this.coversPathFromRequest(request));
    }

    public boolean requestHasAllowedMethod(Request request) {
        return this.handledMethods.contains(request.getMethod());
    }
}
