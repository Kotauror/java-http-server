package httpserver.response;

import httpserver.request.Request;

public class RangeRequestResponder {

    public Response getRangeResponse(Request request) {
        return new Response(ResponseStatus.RANGE_REQUEST);
    }
}
