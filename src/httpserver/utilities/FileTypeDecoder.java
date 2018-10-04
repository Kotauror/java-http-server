package httpserver.utilities;

import java.util.HashMap;
import java.util.Map;

public class FileTypeDecoder {

    private HashMap<String, String> fileExtensionsWithTypes;

    public FileTypeDecoder() {
        this.fileExtensionsWithTypes = new HashMap<String, String>() {{
            put("txt", "text/plain");
            put("jpeg", "image/jpeg");
            put("gif", "image/gif");
            put("png", "image/png");
            put("html", "text/html");
        }};
    }

    public String getFileType(String fileName) {
        return (hasExtension(fileName)) ? this.getFileTypeWhenOneExists(fileName) : this.getDefaultFileType();
    }

    private boolean hasExtension(String fileName) {
        return fileName.contains(".");
    }

    private String getFileExtension(String fileName) {
        return fileName.split("\\.")[1];
    }

    private String getDefaultFileType() {
        return "text/plain";
    }

    private String getFileTypeWhenOneExists(String fileName) {
        String clientFileExtension = getFileExtension(fileName);
        for (Map.Entry<String, String> fileExtensionWithType : this.fileExtensionsWithTypes.entrySet()) {
            String currentExtension = fileExtensionWithType.getKey();
            if (currentExtension.equals(clientFileExtension)) {
                return fileExtensionWithType.getValue();
            }
        }
        return this.getDefaultFileType();
    }
}
