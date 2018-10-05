package httpserver.utilities;

import java.util.HashMap;
import java.util.Map;

public class FileTypeDecoder {

    private HashMap<String, FileType> fileExtensionsWithTypes;

    public FileTypeDecoder() {
        this.fileExtensionsWithTypes = new HashMap<String, FileType>() {{
            put("txt", FileType.TXT);
            put("jpeg",FileType.JPEG);
            put("gif", FileType.GIF);
            put("png", FileType.PNG);
            put("html", FileType.HTML);
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
        return FileType.TXT.value();
    }

    private String getFileTypeWhenOneExists(String fileName) {
        String clientFileExtension = getFileExtension(fileName);
        for (Map.Entry<String, FileType> fileExtensionWithType : this.fileExtensionsWithTypes.entrySet()) {
            String currentExtension = fileExtensionWithType.getKey();
            if (currentExtension.equals(clientFileExtension)) {
                return fileExtensionWithType.getValue().value();
            }
        }
        return this.getDefaultFileType();
    }
}
