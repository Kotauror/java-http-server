package httpserver.handlers;

import httpserver.utilities.FileOperator;
import httpserver.utilities.FileTypeDecoder;
import httpserver.utilities.Method;
import httpserver.request.Request;
import httpserver.utilities.FileContentConverter;
import httpserver.response.Response;

import java.io.IOException;
import java.util.ArrayList;

public abstract class Handler {

    private static final FileContentConverter fileContentConverter = new FileContentConverter();
    private static final FileTypeDecoder fileTypeDecoder = new FileTypeDecoder();
    private static final FileOperator fileOperator = new FileOperator();
    private final ArrayList<Method> handledMethods = new ArrayList<>();
    private HandlerType typeOfHandler = null;

    public abstract Response processRequest(Request request) throws IOException;

    public abstract boolean coversPathFromRequest(Request request);

    public HandlerType getType() {
        return typeOfHandler;
    }

    public void setType(HandlerType handlerType) {
        typeOfHandler = handlerType;
    }

    public void addHandledMethod(Method method) {
        handledMethods.add(method);
    }

    public FileContentConverter getFileContentConverter() {
        return fileContentConverter;
    }

    public FileTypeDecoder getFileTypeDecoder() {
        return fileTypeDecoder;
    }

    public FileOperator getFileOperator() {
        return fileOperator;
    }

    public boolean handles(Request request) {
        return (this.handledMethods.contains(request.getMethod()) && this.coversPathFromRequest(request));
    }

    public boolean requestHasAllowedMethod(Request request) {
        return this.handledMethods.contains(request.getMethod());
    }
}
