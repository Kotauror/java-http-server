package httpserver.utilities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

public class FileContentConverter {

    public byte[] getFileContent(File file) throws IOException {
        byte[] encodedFile = new byte[(int) file.length()];
        FileInputStream fileInputStream = new FileInputStream(file);
        fileInputStream.read(encodedFile);
        fileInputStream.close();
        return encodedFile;
    }

    public byte[] getPartOfFile(File file, HashMap<String, String> startAndEnd) throws IOException {
        byte[] fullFileContent = this.getFileContent(file);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int startIndex = Integer.parseInt(startAndEnd.get("start"));
        int endIndex = Integer.parseInt(startAndEnd.get("end"));
        for (int i = startIndex; i <= endIndex; i++) {
            output.write(fullFileContent[i]);
        }
        return output.toByteArray();
    }
}
