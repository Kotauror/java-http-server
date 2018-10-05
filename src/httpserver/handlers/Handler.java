package httpserver.handlers;

import httpserver.utilities.FileTypeDecoder;
import httpserver.utilities.Method;
import httpserver.request.Request;
import httpserver.utilities.FileContentConverter;
import httpserver.response.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public abstract class Handler {

    private final ArrayList<Method> handledMethods = new ArrayList<>();
    private String typeOfHandler = "";

    public abstract Response getResponse(Request request) throws IOException;

    public abstract boolean coversPath(Request request);

    public String getType() {
        return typeOfHandler;
    }

    public void setType(String type) {
        typeOfHandler = type;
    }

    public void addHandledMethod(Method method) {
        handledMethods.add(method);
    }

    public FileContentConverter getFileContentConverter() {
        return new FileContentConverter();
    }

    public FileTypeDecoder getFileTypeDecoder() {
        return new FileTypeDecoder();
    }

    public boolean handles(Request request) {
        return (this.handledMethods.contains(request.getMethod()) && this.coversPath(request));
    }

    public boolean fileExistsOnPath(Request request, String rootPath) {
        return Files.exists(Paths.get(rootPath + request.getPath()));
    }
}
