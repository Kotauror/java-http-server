package httpserver.utilities;

import httpserver.request.Request;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileOperator {

    public File getRequestedFile(String path) {
        return new File(path);
    }

    public boolean fileExists(String path) {
        return Files.exists(Paths.get(path));
    }

    public void writeToFile(File file, Request request) throws IOException {
        Files.write(Paths.get(file.getPath()), request.getBody().getBytes());
    }

    public int getLengthOfFileContent(String filePath, String rootPath) throws IOException {
        File file = new File(rootPath + "/" + filePath);
        byte[] fileContent = Files.readAllBytes(file.toPath());
        return fileContent.length;
    }

    public void deleteFile(String fileName, String rootPath) {
        File file = new File(rootPath + "/" + fileName);
        file.delete();
    }

    public String removeKeyFromPathIfExists(String fullPath) {
        String[] partsOfPath = fullPath.split("/");
        if (this.keyDoesntExist(partsOfPath)) {
            return "/" + partsOfPath[1];
        } else {
            int lengthOfKeyInPath = partsOfPath[partsOfPath.length-1].length();
            int lengthOfPathWithoutKey = fullPath.length() - lengthOfKeyInPath;
            return fullPath.substring(0, lengthOfPathWithoutKey-1);
        }
    }

    private boolean keyDoesntExist(String[] partsOfPath) {
        return partsOfPath.length == 2;
    }
}
