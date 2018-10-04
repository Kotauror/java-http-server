package httpserver.response;

import java.util.HashMap;

public class Response {

    private final HashMap<String, String> headers;
    private byte[] bodyContent;
    private ResponseStatus responseStatus;
    private String httpVersion;

    public Response() {
        this.httpVersion = "HTTP/1.1";
        this.responseStatus = ResponseStatus.INTERNAL_SERVER_ERROR;
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

    public void setStatus(ResponseStatus status) {
        this.responseStatus = status;
    }

    public String getFullResponse() {
        return this.httpVersion + " " + this.responseStatus.getStatus();
    }

    public void setBodyContent(byte[] bodyContent) {
        this.bodyContent = bodyContent;
    }

    public void setContentTypeHeader(String fileName) {
        FileTypeDecoder fileTypeDecoder = new FileTypeDecoder();
        String fileType = fileTypeDecoder.getFileType(fileName);
        headers.put("Content-Type", fileType);
    }
}
