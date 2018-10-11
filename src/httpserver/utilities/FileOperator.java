package httpserver.utilities;

import httpserver.request.Request;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileOperator {

    public File getRequestedFile(Request request, String rootPath) {
        return new File(rootPath + "/" + request.getPath());
    }

    public boolean fileExistsOnPath(Request request, String rootPath) {
        return Files.exists(Paths.get(rootPath + request.getPath()));
    }

    public void writeToFile(File file, Request request) throws IOException {
        Files.write(Paths.get(file.getPath()), request.getBody().getBytes());
    }

    public int getLengthOfFileContent(Request request, String rootPath) throws IOException {
        File file = new File(rootPath + "/" + request.getPath());
        byte[] fileContent = Files.readAllBytes(file.toPath());
        return fileContent.length;
    }

    public void deleteFile(Request request, String rootPath) {
        this.getRequestedFile(request, rootPath).delete();
    }
}
