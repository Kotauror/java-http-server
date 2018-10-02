package httpserver.response;

public enum ResponseStatus {

    INTERNAL_SERVER_ERROR("500 Internal Server Error"),
    OK("200 OK");

    private final String status;

    ResponseStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
