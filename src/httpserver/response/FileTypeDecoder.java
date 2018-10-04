package httpserver.response;

public class FileTypeDecoder {

    public String getFileType(String fileName) {
        if (fileName.contains("txt")){
            return "text/plain";
        }
        return "text/plain";
    }
}
