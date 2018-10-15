package httpserver.response;

public enum ResponseStatus {

    OK("200"),
    CREATED("201"),
    NO_CONTENT("204"),
    RANGE_REQUEST("206"),
    FOUND("302"),
    UNAUTHORIZED("401"),
    NOT_FOUND("404"),
    NOT_ALLOWED("405"),
    PRECONDITION_FAILED("412"),
    RANGE_NOT_SATISFIABLE("416"),
    TEAPOT("418"),
    INTERNAL_SERVER_ERROR("500");

    private String status;

    ResponseStatus(String status) {
        this.status = status;
    }

    public String getStatusCode() {
        return this.status;
    }
}
