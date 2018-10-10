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
        byte[] body = this.getBodyContent(files);
        HashMap<Header, String> headers = this.getHeaders();
        return new Response(ResponseStatus.OK, body, headers);
    }

    @Override
    public boolean coversPathFromRequest(Request request) {
        return request.getPath().equals("/");
    }

    private byte[] getBodyContent(File[] files) {
        StringBuilder body = new StringBuilder();
        body.append("<html><head></head><body>");
        for (File file : files) {
            String link = this.generateLink(file);
            body.append(link);
        }
        body.append("</body></html>");
        return body.toString().getBytes();
    }

    private String generateLink(File file) {
        return "<a href='/" + file.getName() + "'>" + file.getName() + "</a><br>";
    }

    private HashMap<Header, String> getHeaders(){
        return new HashMap<Header, String>() {{
            put(Header.CONTENT_TYPE, FileType.HTML.value());
        }};
    }
}
