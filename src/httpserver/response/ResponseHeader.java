package httpserver.response;

public enum ResponseHeader {

    AUTHENTICATE("WWW-Authenticate"),
    ALLOW("Allow"),
    CONTENT_RANGE("Content-Range"),
    CONTENT_TYPE("Content-Type"),
    LOCATION("Location"),
    SET_COOKIE("Set-Cookie");

    private final String headerContent;

    ResponseHeader(final String headerContent) {
        this.headerContent = headerContent;
    }

    @Override
    public String toString() {
        return headerContent;
    }

    public static ResponseHeader[] getHeaders() {
        return ResponseHeader.class.getEnumConstants();
    }
}
