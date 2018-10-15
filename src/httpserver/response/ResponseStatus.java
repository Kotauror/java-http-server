package httpserver.response;

public enum ResponseStatus {

    INTERNAL_SERVER_ERROR("500"),
    OK("200"),
    CREATED("201"),
    RANGE_REQUEST("206"),
    FOUND("302"),
    UNAUTHORIZED("401"),
    NOT_ALLOWED("405"),
    RANGE_NOT_SATISFIABLE("416"),
    TEAPOT("418"),
    UNPROCESSABLE("422"),
    NOT_FOUND("404");

    private String status;

    ResponseStatus(String status) {
        this.status = status;
    }

    public String getStatusCode() {
        return this.status;
    }
}
