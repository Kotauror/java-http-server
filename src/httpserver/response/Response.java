package httpserver.response;

public class Response {

    private ResponseStatus responseStatus;
    private String httpVersion;

    public Response() {
        this.httpVersion = "HTTP/1.1";
        this.responseStatus = ResponseStatus.INTERNAL_SERVER_ERROR;
    }

    public ResponseStatus getStatus() {
        return this.responseStatus;
    }

    public String getHttpVersion() {
        return this.httpVersion;
    }

    public void setStatus(ResponseStatus status) {
        this.responseStatus = status;
    }

    public String getFullResponse() {
        return this.httpVersion + " " + this.responseStatus.getStatus();
    }
}
