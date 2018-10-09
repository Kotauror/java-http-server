package httpserver.response;

public enum ResponseStatus {

    INTERNAL_SERVER_ERROR("500"),
    OK("200"),
    CREATED("201"),
    NOT_ALLOWED("405"),
    NOT_FOUND("404");

    private String status;

    ResponseStatus(String status) {
        this.status = status;
    }

    public String getStatusCode() {
        return this.status;
    }
}
