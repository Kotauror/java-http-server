package httpserver.handlers;

import httpserver.request.Request;
import httpserver.response.Header;
import httpserver.response.Response;
import httpserver.response.ResponseStatus;
import httpserver.utilities.FileType;
import httpserver.utilities.Method;

import java.io.File;
import java.util.HashMap;

public class DirectoryLinksHandler extends Handler {

    private final String rootPath;

    public DirectoryLinksHandler(String rootPath) {
        this.rootPath = rootPath;
        setType(HandlerType.DIRECTORY_LISTING_HANDLER);
        addHandledMethod(Method.GET);
    }

    @Override
    public Response processRequest(Request request) {
        File[] files = new File(this.rootPath).listFiles();
        StringBuilder body = new StringBuilder();
        body.append("<html><head></head><body>");
        for (File file : files) {
            String link = this.generateLink(file);
            body.append(link);
        }
        body.append("</body></html>");
        byte [] bodyContent = body.toString().getBytes();
        HashMap<Header, String> headers = new HashMap<Header, String>() {{
            put(Header.CONTENT_TYPE, FileType.HTML.value());
        }};
        return new Response(ResponseStatus.OK, bodyContent, headers);
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return request.getPath().equals("/");
    }

    private String generateLink(File file) {
        return "<a href='/" + file.getName() + "'>" + file.getName() + "</a><br>";
    }
}
