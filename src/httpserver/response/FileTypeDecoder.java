package httpserver.response;

import java.util.HashMap;
import java.util.Map;

public class FileTypeDecoder {

    private HashMap<String, String> fileExtensionsWithTypes;

    public FileTypeDecoder() {
        this.fileExtensionsWithTypes = new HashMap();
        fillMapWithFileTypes();
    }

    public String getFileType(String fileName) {
        if (hasExtension(fileName)) {
            String clientFileExtension = getFileExtension(fileName);
            for (Map.Entry<String, String> fileExtensionWithType : this.fileExtensionsWithTypes.entrySet()) {
                String currentExtension = fileExtensionWithType.getKey();
                if (currentExtension.equals(clientFileExtension)) {
                    return fileExtensionWithType.getValue();
                }
            }
            return this.getDefaultFileType();
        } else {
            return this.getDefaultFileType();
        }
    }

    private void fillMapWithFileTypes() {
        fileExtensionsWithTypes.put("txt", "text/plain");
        fileExtensionsWithTypes.put("jpeg", "image/jpeg");
        fileExtensionsWithTypes.put("gif", "image/gif");
        fileExtensionsWithTypes.put("png", "image/png");
        fileExtensionsWithTypes.put("html", "text/html");
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
}
