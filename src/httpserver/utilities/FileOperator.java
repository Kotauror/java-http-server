package httpserver.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileOperator {

    public File getRequestedFile(String fullFilePath) {
        return new File(fullFilePath);
    }

    public boolean fileExists(String fullFilePath) {
        return Files.exists(Paths.get(fullFilePath));
    }

    public void writeToFile(File file, String content) throws IOException {
        Files.write(Paths.get(file.getPath()),content.getBytes());
    }

    public int getLengthOfFileContent(String fullFilePath) throws IOException {
        File file = new File(fullFilePath);
        byte[] fileContent = Files.readAllBytes(file.toPath());
        return fileContent.length;
    }

    public void deleteFile(String fullFilePath) {
        File file = new File(fullFilePath);
        file.delete();
    }

    public String removeKeyFromPathIfExists(String fullPath) {
        String[] partsOfPath = fullPath.split("/");
        if (this.keyDoesNotExist(partsOfPath)) {
            return "/" + partsOfPath[1];
        } else {
            int lengthOfKeyInPath = partsOfPath[partsOfPath.length-1].length();
            int lengthOfPathWithoutKey = fullPath.length() - lengthOfKeyInPath;
            return fullPath.substring(0, lengthOfPathWithoutKey-1);
        }
    }

    public boolean filePathRefersFileContent(String root, String filePath) {
        String pathWithoutEventualKey = this.removeKeyFromPathIfExists(filePath);
        return (!this.fileExists(root + filePath)) && (this.fileExists(root + pathWithoutEventualKey));
    }

    public File[] getFilesFromDirectory(String directoryPath) {
        return new File(directoryPath).listFiles();
    }

    private boolean keyDoesNotExist(String[] partsOfPath) {
        return partsOfPath.length == 2;
    }
}
