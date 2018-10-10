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
        try {
            byte[] body = this.getPartOfFileContent(rangeLimits, file);
            String fileType = this.fileTypeDecoder.getFileType(request.getPath());
            int fileLength = this.fileOperator.findLengthOfFileContent(request, this.rootPath);
            String contentRangeHeader = this.getContentRangeHeader(fileLength, rangeLimits);
            return new Response(ResponseStatus.RANGE_REQUEST, body, fileType, contentRangeHeader);
        }
        catch(ArrayIndexOutOfBoundsException exception) {
            int fileLength = this.fileOperator.findLengthOfFileContent(request, this.rootPath);
            String contentRangeHeader = "bytes */" + Integer.toString(fileLength);
            return new Response(ResponseStatus.RANGE_NOT_SATISFIABLE, null, null, contentRangeHeader);
        }
    }

    public HashMap<String, Integer> getRangeLimits(Request request) throws IOException {
        HashMap startEndMap = new HashMap<String, Integer>();
        String rangeString = request.getHeaders().get("Range").toString();
        String[] partsOfRangeString = this.getPartsOfRangeString(rangeString);

        Integer startValue = this.getStartValue(partsOfRangeString, request);
        Integer endValue = this.getEndValue(partsOfRangeString, request);

        startEndMap.put("start", startValue);
        startEndMap.put("end", endValue);
        return startEndMap;
    }

    private String[] getPartsOfRangeString(String rangeString) {
        String numbers = rangeString.substring(6, rangeString.length());
        if (numbers.split("-").length == 2) {
            return numbers.split("-");
        } else {
            return new String[]{numbers.split("-")[0], ""};
        }
    }

    private byte[] getPartOfFileContent(HashMap startAndEnd, File file) throws IOException {
        return this.fileContentConverter.getPartOfFile(file, startAndEnd);
    }

    private Integer getStartValue(String[] partsOfRangeString, Request request) throws IOException {
        if (!this.valueIsEmptyString(partsOfRangeString[0])) {
            return Integer.parseInt(partsOfRangeString[0]);
        } else {
            Integer lengthOfFileContent = this.fileOperator.findLengthOfFileContent(request, this.rootPath);
            return lengthOfFileContent - Integer.parseInt(partsOfRangeString[1]);
        }
    }

    private Integer getEndValue(String[] partsOfRangeString, Request request) throws IOException {
        if (this.valueIsEmptyString(partsOfRangeString[1])) {
            Integer lengthOfFileContent = this.fileOperator.findLengthOfFileContent(request, this.rootPath);
            return lengthOfFileContent -1;
        } else {
            if (this.valueIsEmptyString(partsOfRangeString[0])) {
                Integer lengthOfFileContent = this.fileOperator.findLengthOfFileContent(request, this.rootPath);
                return lengthOfFileContent -1;
            } else {
                return Integer.parseInt(partsOfRangeString[1]);
            }
        }
    }

    private String getContentRangeHeader(int fileLength, HashMap rangeLimits) {
//        return "bytes 0-4/77";
        return "bytes " + Integer.toString((int) rangeLimits.get("start")) + "-" + Integer.toString((int) rangeLimits.get("end")) + "/" + Integer.toString(fileLength);
    }

    private boolean valueIsEmptyString(String string) {
        return string.equals("");
    }
}
