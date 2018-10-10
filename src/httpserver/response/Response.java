package httpserver.response;

import java.util.HashMap;
import java.util.Map;

public class Response {

    private HashMap<String, String> headers = new HashMap<>();
    private byte[] bodyContent;
    private ResponseStatus responseStatus;
    private String httpVersion;

    public Response(ResponseStatus responseStatus, byte[] body, HashMap<Header, String> headers) {
        this.httpVersion = "HTTP/1.1";
        this.responseStatus = responseStatus;
        addBodyContent(body);
        setHeaders(headers);
    }

    public ResponseStatus getStatus() {
        return this.responseStatus;
    }

    public String getHttpVersion() {
        return this.httpVersion;
    }

    public byte[] getBodyContent() {
        return this.bodyContent;
    }

    public HashMap getHeaders() {
        return this.headers;
    }

    private void addBodyContent(byte[] fileContentInBytes) {
        if (fileContentInBytes != null) {
            this.bodyContent = fileContentInBytes;
        }
    }

    private void setHeaders(HashMap<Header, String> headers) {
        for (Map.Entry<Header, String> entry : headers.entrySet()) {
            this.headers.put(entry.getKey().toString(), entry.getValue());
        }
    }
}
