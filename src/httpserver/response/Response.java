package httpserver.response;

import java.util.HashMap;

public class Response {

    private final HashMap<String, String> headers;
    private byte[] bodyContent;
    private ResponseStatus responseStatus;
    private String httpVersion;

    public Response(ResponseStatus responseStatus) {
        this.httpVersion = "HTTP/1.1";
        this.responseStatus = responseStatus;
        this.bodyContent = new byte[0];
        this.headers = new HashMap<>();
    }

    public ResponseStatus getStatus() {
        return this.responseStatus;
    }

    public String getHttpVersion() {
        return this.httpVersion;
    }

    public byte[] getBodyContent() {
        return this.bodyContent;
    }

    public HashMap getHeaders() {
        return this.headers;
    }

    public void setBodyContent(byte[] bodyContent) {
        this.bodyContent = bodyContent;
    }

    public void setContentTypeHeader(String fileType) {
        this.headers.put("Content-Type", fileType);
    }
}
