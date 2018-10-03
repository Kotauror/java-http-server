package httpserver.response;

public class Response {

    private byte[] bodyContent;
    private ResponseStatus responseStatus;
    private String httpVersion;

    public Response() {
        this.httpVersion = "HTTP/1.1";
        this.responseStatus = ResponseStatus.INTERNAL_SERVER_ERROR;
        this.bodyContent = new byte[0];
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

    public void setStatus(ResponseStatus status) {
        this.responseStatus = status;
    }

    public String getFullResponse() {
        return this.httpVersion + " " + this.responseStatus.getStatus();
    }

    public void setBodyContent(byte[] bodyContent) {
        this.bodyContent = bodyContent;
    }
}
