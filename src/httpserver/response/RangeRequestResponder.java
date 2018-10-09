package httpserver.response;

import httpserver.request.Request;
import httpserver.utilities.FileContentConverter;
import httpserver.utilities.FileOperator;
import httpserver.utilities.FileTypeDecoder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class RangeRequestResponder {

    private final String rootPath;
    private final FileOperator fileOperator;
    private final FileContentConverter fileContentConverter;
    private final FileTypeDecoder fileTypeDecoder;

    public RangeRequestResponder(String rootPath, FileOperator fileOperator, FileContentConverter fileContentConverter, FileTypeDecoder fileTypeDecoder) {
        this.rootPath = rootPath;
        this.fileOperator = fileOperator;
        this.fileContentConverter = fileContentConverter;
        this.fileTypeDecoder = fileTypeDecoder;
    }

    public Response getRangeResponse(Request request) throws IOException {
        HashMap<String, Integer> rangeLimits = this.getRangeLimits(request);
        File file = this.fileOperator.getRequestedFile(request, this.rootPath);
        byte[] body = this.getPartOfFileContent(rangeLimits, file);
        String fileType = this.fileTypeDecoder.getFileType(request.getPath());

        return new Response(ResponseStatus.RANGE_REQUEST, body, fileType);
    }

    private HashMap<String, Integer> getRangeLimits(Request request) {
        HashMap startEndMap = new HashMap<String, Integer>();
        String rangeString = request.getHeaders().get("Range").toString();
        String[] partsOfRangeString = this.getPartsOfRangeString(rangeString);
        startEndMap.put("start", Integer.parseInt(partsOfRangeString[0]));
        startEndMap.put("end", Integer.parseInt(partsOfRangeString[1]));
        return startEndMap;
    }

    private String[] getPartsOfRangeString(String rangeString) {
        String numbers = rangeString.substring(6, rangeString.length());
        return numbers.split("-");
    }

    private byte[] getPartOfFileContent(HashMap startAndEnd, File file) throws IOException {
        return this.fileContentConverter.getPartOfFile(file, startAndEnd);
    }
}
