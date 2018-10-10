package httpserver.response;

import java.util.HashMap;

public class Response {

    private HashMap<String, String> headers = new HashMap<>();
    private byte[] bodyContent;
    private ResponseStatus responseStatus;
    private String httpVersion;

    public Response(ResponseStatus responseStatus) {
        this.httpVersion = "HTTP/1.1";
        this.responseStatus = responseStatus;
    }

    public Response(ResponseStatus responseStatus, byte[] fileContentInBytes, String fileType) {
        this.httpVersion = "HTTP/1.1";
        this.responseStatus = responseStatus;
        addBodyContent(fileContentInBytes);
        setContentTypeHeader(fileType);
    }

    public Response(ResponseStatus responseStatus, byte[] fileContentInBytes, String fileType, String contentRangeHeader) {
        this.httpVersion = "HTTP/1.1";
        this.responseStatus = responseStatus;
        addBodyContent(fileContentInBytes);
        setContentTypeHeader(fileType);
        setContentRangeHeader(contentRangeHeader);
    }

    public Response(ResponseStatus responseStatus, String[] allowedMethods) {
        this.httpVersion = "HTTP/1.1";
        this.responseStatus = responseStatus;
        setAllowedMethodsHeader(allowedMethods);
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
            this.headers.put(Header.CONTENT_TYPE.toString(), fileType);
        }
    }

    private void setAllowedMethodsHeader(String[] allowedMethods) {
        StringBuilder stringBuilder = new StringBuilder();
        if (allowedMethods.length > 0) {
            for (int i = 0; i < allowedMethods.length -1; i++) {
                stringBuilder.append(allowedMethods[i] + ",");
            }
            stringBuilder.append(allowedMethods[allowedMethods.length-1]);
            this.headers.put(Header.ALLOW.toString(), stringBuilder.toString());
        }
    }

    private void setContentRangeHeader(String contentRangeHeader) {
        this.headers.put(Header.CONTENT_RANGE.toString(), contentRangeHeader);
    }

    private void addBodyContent(byte[] fileContentInBytes) {
        if (fileContentInBytes != null) {
            this.bodyContent = fileContentInBytes;
        }
    }
}
