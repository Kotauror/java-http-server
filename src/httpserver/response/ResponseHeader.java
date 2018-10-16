package httpserver.response;

public enum ResponseHeader {

    AUTHENTICATE("WWW-Authenticate"),
    ALLOW("Allow"),
    CONTENT_RANGE("Content-Range"),
    CONTENT_TYPE("Content-Type"),
    LOCATION("Location"),
    SET_COOKIE("Set-Cookie");

    private final String text;

    ResponseHeader(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static ResponseHeader[] getHeaders() {
        return ResponseHeader.class.getEnumConstants();
    }
}
