package httpserver.response;

public enum Header {

    CONTENT_TYPE("Content-Type"),
    CONTENT_RANGE("Content-Range"),
    ALLOW("Allow");

    private final String text;

    Header(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static Header[] getHeaders() {
        return Header.class.getEnumConstants();
    }
}
