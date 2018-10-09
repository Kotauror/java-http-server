package httpserver.response;

import httpserver.request.Request;

import java.util.HashMap;
import java.util.Map;

public class RangeRequestResponder {

    public Response getRangeResponse(Request request) {
        return new Response(ResponseStatus.RANGE_REQUEST);
    }

    public Map<String, Integer> getRangeLimits(Request request) {
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
}
