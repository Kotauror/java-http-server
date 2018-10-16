package httpserver.utilities;

public enum FileType {

    TXT("text/plain", "txt"),
    JPEG("image/jpeg", "jpeg"),
    GIF("image/gif", "gif"),
    PNG("image/png", "png"),
    HTML("text/html", "html");

    private final String fileType;
    private final String extension;

    FileType(String fileType, String extension) {
        this.fileType = fileType;
        this.extension = extension;
    }

    public String getType() {
        return fileType;
    }

    public String getExtension() {
        return extension;
    }

    public static FileType[] getFileTypes() {
        return FileType.class.getEnumConstants();
    }
}
