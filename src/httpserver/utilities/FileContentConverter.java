package httpserver.utilities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class FileContentConverter {

    public byte[] getFileContentFromFile(File file) throws IOException {
        byte[] encodedFile = new byte[(int) file.length()];
        FileInputStream fileInputStream = new FileInputStream(file);
        fileInputStream.read(encodedFile);
        fileInputStream.close();
        return encodedFile;
    }

    public byte[] getPartOfFile(File file, HashMap<String, String> startAndEnd) throws IOException {
        byte[] fullFileContent = this.getFileContentFromFile(file);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int startIndex = Integer.parseInt(startAndEnd.get("start"));
        int endIndex = Integer.parseInt(startAndEnd.get("end"));
        for (int i = startIndex; i <= endIndex; i++) {
            output.write(fullFileContent[i]);
        }
        return output.toByteArray();
    }

    public String getFileContentAsString(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
}
