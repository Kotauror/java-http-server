package httpserver.response;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseWriter {

    private final OutputStream outputStream;
    private Response response;

    public ResponseWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void write(Response response) throws IOException {
        this.response = response;
        writeStatusCode();
        writeHeaders();
        writeEmptyLine();
        writeBody();
    }

    private void writeStatusCode() throws IOException {
        String statusLine = this.response.getHttpVersion() + " " + this.response.getStatus().getStatusCode() + "\n";
        write(statusLine.getBytes());
    }

    private void writeHeaders() throws IOException {
        if (this.headersAreNotEmpty()) {
            String contentType = "Content-Type" + ": " + this.response.getHeaders().get("Content-Type") + "\r\n";
            write(contentType.getBytes());
        }
    }

    private void writeEmptyLine() throws IOException {
        write(("\r\n").getBytes());
    }

    private void writeBody() throws IOException {
        write(response.getBodyContent());
    }

    private void write (byte[] bytes) throws IOException {
        this.outputStream.write(bytes);
    }

    private boolean headersAreNotEmpty() {
        return !(response.getHeaders().isEmpty());
    }
}
