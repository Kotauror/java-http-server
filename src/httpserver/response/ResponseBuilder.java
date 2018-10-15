package httpserver.response;

import java.util.HashMap;

public class ResponseBuilder {

    public Response getOKResponse(byte[] body, HashMap<ResponseHeader, String> headers) {
        return new Response(ResponseStatus.OK, body, headers);
    }

    public Response getNotAllowedResponse() {
        return new Response(ResponseStatus.NOT_ALLOWED, null, new HashMap<>());
    }

    public Response getUnauthorizedResponse() {
        HashMap<ResponseHeader, String> header = new HashMap<ResponseHeader, String>() {{
            put(ResponseHeader.AUTHENTICATE, "Basic realm=\"Access to staging site\"");
        }};
        return new Response(ResponseStatus.UNAUTHORIZED, null, header);
    }
}
