package httpserver.response;

public enum ResponseHeader {

    CONTENT_TYPE("Content-Type"),
    CONTENT_RANGE("Content-Range"),
    AUTHENTICATE("WWW-Authenticate"),
    SET_COOKIE("Set-Cookie"),
    ALLOW("Allow");

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
