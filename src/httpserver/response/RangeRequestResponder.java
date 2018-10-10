package httpserver.response;

import httpserver.request.Request;
import httpserver.utilities.FileContentConverter;
import httpserver.utilities.FileOperator;
import httpserver.utilities.FileType;
import httpserver.utilities.FileTypeDecoder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class RangeRequestResponder {

    private final String rootPath;
    private final FileOperator fileOperator;
    private final FileContentConverter fileContentConverter;
    private final FileTypeDecoder fileTypeDecoder;

    public RangeRequestResponder(String rootPath, FileOperator fileOperator, FileContentConverter fileContentConverter,
                                 FileTypeDecoder fileTypeDecoder) {
        this.rootPath = rootPath;
        this.fileOperator = fileOperator;
        this.fileContentConverter = fileContentConverter;
        this.fileTypeDecoder = fileTypeDecoder;
    }

    public Response getRangeResponse(Request request) throws IOException {
        int lengthOfRequestedFile = this.fileOperator.getLengthOfFileContent(request, this.rootPath);
        String requestedRangeString = request.getHeaders().get("Range").toString();
        HashMap<String, String> rangeLimits = this.getRangeLimits(requestedRangeString, lengthOfRequestedFile);

        if (this.isRangeWithinFileLength(rangeLimits, lengthOfRequestedFile)) {
            return this.getSuccessfulRangeResponse(request, rangeLimits, lengthOfRequestedFile);
        } else {
            return this.getUnsuccessfulRangeResponse(lengthOfRequestedFile);
        }
    }

    private HashMap<String, String> getRangeLimits(String rangeString, int lengthOfRequestedFile) {
        String[] partsOfRangeString = this.getPartsOfRangeString(rangeString);

        String startValue = this.getStartValue(partsOfRangeString, lengthOfRequestedFile);
        String endValue = this.getEndValue(partsOfRangeString, lengthOfRequestedFile);
        return getRangeLimitsMap(startValue, endValue);
    }

    private String[] getPartsOfRangeString(String rangeString) {
        String limits = rangeString.substring(6, rangeString.length());
        return (this.hasBothValues(limits)) ? limits.split("-") : new String[]{limits.split("-")[0], ""};
    }

    private boolean hasBothValues(String limits) {
        return limits.split("-").length == 2;
    }

    private String getStartValue(String[] partsOfRangeString, Integer lengthOfFileContent) {
        String startValue = partsOfRangeString[0];
        String endValue = partsOfRangeString[1];
        return (!startValue.equals("")) ? startValue : Integer.toString(lengthOfFileContent - Integer.parseInt(endValue));
    }

    private String getEndValue(String[] partsOfRangeString, Integer lengthOfFileContent) {
        String startValue = partsOfRangeString[0];
        String endValue = partsOfRangeString[1];
        if (!endValue.equals("")) {
            return (!startValue.equals("")) ? endValue : Integer.toString(lengthOfFileContent - 1);
        } else {
            return Integer.toString(lengthOfFileContent - 1);
        }
    }

    private HashMap<String, String> getRangeLimitsMap(String startValue, String endValue) {
        return new HashMap<String, String>(){{
            put("start", startValue);
            put("end", endValue);
        }};
    }

    private boolean isRangeWithinFileLength(HashMap<String, String> rangeLimits, int fileLength) {
        return Integer.parseInt(rangeLimits.get("end")) <= fileLength;
    }

    private Response getSuccessfulRangeResponse(Request request, HashMap<String, String> rangeLimits, int lengthOfRequestedFile) throws IOException {
        File file = this.fileOperator.getRequestedFile(request, this.rootPath);
        byte[] body = this.getPartOfFileContent(rangeLimits, file);
        FileType fileType = this.fileTypeDecoder.getFileType(request.getPath());
        String contentRangeHeader = this.getContentRangeHeader(lengthOfRequestedFile, rangeLimits);
        HashMap<Header, String> headers = this.getHeaders(fileType, contentRangeHeader);
        return new Response(ResponseStatus.RANGE_REQUEST, body, headers);
    }

    private byte[] getPartOfFileContent(HashMap startAndEnd, File file) throws IOException {
        return this.fileContentConverter.getPartOfFile(file, startAndEnd);
    }

    private String getContentRangeHeader(int fileLength, HashMap rangeLimits) {
        return "bytes " + rangeLimits.get("start") + "-" + rangeLimits.get("end") + "/" + Integer.toString(fileLength);
    }

    private HashMap<Header, String> getHeaders(FileType fileType, String contentRangeHeader) {
        return new HashMap<Header, String>() {{
            put(Header.CONTENT_TYPE, fileType.value());
            put(Header.CONTENT_RANGE, contentRangeHeader);
        }};
    }

    private Response getUnsuccessfulRangeResponse(int lengthOfRequestedFile) {
        String contentRangeHeader = "bytes */" + Integer.toString(lengthOfRequestedFile);
        HashMap<Header, String> headers = new HashMap<Header, String>() {{
            put(Header.CONTENT_RANGE, contentRangeHeader);
        }};
        return new Response(ResponseStatus.RANGE_NOT_SATISFIABLE, null, headers);
    }
}


