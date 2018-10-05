package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.FileType;
import httpserver.utilities.Method;

import java.io.File;

public class DirectoryListingHandler extends Handler {

    private final String rootPath;

    public DirectoryListingHandler(String rootPath) {
        this.rootPath = rootPath;
        setType(HandlerType.DIRECTORY_LISTING_HANDLER);
        addHandledMethod(Method.GET);
    }

    @Override
    public Response getResponse(Request request) {
        File[] files = new File(this.rootPath).listFiles();
        StringBuilder body = new StringBuilder();
        body.append("<html><head></head><body>");
        for (File file : files) {
            String link = this.generateLink(file);
            body.append(link);
        }
        body.append("</body></html>");
        byte [] bodyContent = body.toString().getBytes();
        return new Response(ResponseStatus.OK, bodyContent, FileType.HTML.value());
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return request.getPath().equals("/");
    }

    private String generateLink(File file) {
        return "<a href='/" + file.getName() + "'>" + file.getName() + "</a><br>";
    }
}
