package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.Method;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PutHandler extends Handler{

    private final String rootPath;

    public PutHandler(String rootPath) {
        this.rootPath = rootPath;
        setType(HandlerType.PUT_HANDLER);
        addHandledMethod(Method.PUT);
    }

    @Override
    public Response processRequest(Request request) {
        String fileName = request.getPath();
        try {
            // check if file with this name exists, if yes, then write... no - create new
            File file = new File(this.rootPath + "/" + fileName);
            file.createNewFile();
            Files.write(Paths.get(file.getPath()), request.getBody().getBytes());
            return new Response(ResponseStatus.CREATED, Files.readAllBytes(Paths.get(file.getPath())), null);
        } catch (IOException e) {
            return new Response(ResponseStatus.INTERNAL_SERVER_ERROR, null ,null);
        }
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        String pathFromRequest = request.getPath();
        if (null != pathFromRequest && pathFromRequest.length() > 0 ) {
            int indexOfLastSlash = pathFromRequest.lastIndexOf("/");
            if (indexOfLastSlash != -1) {
                String directoryPath = pathFromRequest.substring(0, indexOfLastSlash);
                return this.rootPath.contains(directoryPath);
            }
        }
        return false;
    }
}
