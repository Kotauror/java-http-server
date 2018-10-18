package httpserver.utilities;

public class FileTypeDecoder {

    public FileTypeDecoder(){}

    public FileType getFileType(String fileName) {
        return (hasExtension(fileName)) ? this.getFileTypeWhenOneExists(fileName) : this.getDefaultFileType();
    }

    private boolean hasExtension(String fileName) {
        return fileName.contains(".");
    }

    private FileType getDefaultFileType() {
        return FileType.TXT;
    }

    private FileType getFileTypeWhenOneExists(String fileName) {
        String clientFileExtension = getFileExtension(fileName);
        for (FileType fileType : FileType.getFileTypes()) {
            if (fileType.getExtension().equals(clientFileExtension)) {
                return fileType;
            }
        }
        return this.getDefaultFileType();
    }

    private String getFileExtension(String fileName) {
        return fileName.split("\\.")[1];
    }
}
