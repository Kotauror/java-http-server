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

    public byte[] getPartOfFile(File file, HashMap startAndEnd) throws IOException {
        byte[] fullFileContent = this.getFileContent(file);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        for (int i = (int) startAndEnd.get("start"); i <= (int) startAndEnd.get("end"); i++) {
            output.write(fullFileContent[i]);
        }
        return output.toByteArray();
    }
}
