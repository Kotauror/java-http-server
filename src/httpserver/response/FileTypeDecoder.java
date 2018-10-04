package httpserver.response;

import java.io.File;

public class FileTypeDecoder {

    public String getFileType(String fileName) {
        if (fileName.contains("txt")){
            return "text/plain";
        }
        return "";
    }
}
