package httpserver.response;

import java.util.HashMap;

public class Response {

    private HashMap<String, String> headers = new HashMap<>();
    private byte[] bodyContent;
    private ResponseStatus responseStatus;
    private String httpVersion;

    public Response(ResponseStatus responseStatus, byte[] fileContentInBytes, String fileType) {
        this.httpVersion = "HTTP/1.1";
        this.responseStatus = responseStatus;
        addBodyContent(fileContentInBytes);
        setContentTypeHeader(fileType);
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

    private void setContentTypeHeader(String fileType) {
        if (fileType != null) {
            this.headers.put("Content-Type", fileType);
        }
    }

    private void addBodyContent(byte[] fileContentInBytes) {
        if (fileContentInBytes != null) {
            this.bodyContent = fileContentInBytes;
        }
    }
}
