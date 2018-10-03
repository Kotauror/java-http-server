package httpserver.response;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileContentConverter {

    public byte[] getFileContent(File file) throws IOException {
        byte[] encodedFile = new byte[(int) file.length()];
        FileInputStream fileInputStream = new FileInputStream(file);
        fileInputStream.read(encodedFile);
        fileInputStream.close();
        return encodedFile;
    }
}
