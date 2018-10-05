package httpserver.utilities;

public enum FileType {

    TXT("text/plain"),
    JPEG("image/jpeg"),
    GIF("image/gif"),
    PNG("image/png"),
    HTML("text/html");

    private final String value;

    FileType(String fileType) {
        this.value = fileType;
    }

    public String value() {
        return value;
    }
}
