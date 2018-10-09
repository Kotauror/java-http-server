package httpserver.response;

import httpserver.request.Request;
import httpserver.utilities.FileContentConverter;
import httpserver.utilities.FileOperator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class RangeRequestResponder {

    private final String rootPath;
    private final FileOperator fileOperator;
    private final FileContentConverter fileContentConverter;

    public RangeRequestResponder(String rootPath, FileOperator fileOperator, FileContentConverter fileContentConverter) {
        this.rootPath = rootPath;
        this.fileOperator = fileOperator;
        this.fileContentConverter = fileContentConverter;
    }

    public Response getRangeResponse(Request request) throws IOException {
        HashMap<String, Integer> rangeLimits = this.getRangeLimits(request);
        byte[] body = this.getPartOfFileContent(rangeLimits, request);

        return new Response(ResponseStatus.RANGE_REQUEST, body, null);
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

    private byte[] getPartOfFileContent(HashMap startAndEnd, Request request) throws IOException {
        File file = this.fileOperator.getRequestedFile(request, this.rootPath);
        return this.fileContentConverter.getPartOfFile(file, startAndEnd);
    }
}
