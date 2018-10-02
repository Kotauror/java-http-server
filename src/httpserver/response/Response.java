package httpserver.response;

public class Response {

    private final ResponseStatus responseStatus;
    private String httpVersion;

    public Response() {
        this.httpVersion = "HTTP/1.1";
        this.responseStatus = ResponseStatus.INTERNAL_SERVER_ERROR;
    }

    public ResponseStatus getStatus() {
        return this.responseStatus;
    }
}
