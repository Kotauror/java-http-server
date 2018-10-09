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
        writeBody();
    }

    private void writeStatusCode() throws IOException {
        String statusLine = this.response.getHttpVersion() + " " + this.response.getStatus().getStatusCode() + "\n";
        write(statusLine.getBytes());
    }

    private void writeHeaders() throws IOException {
        for (Header header : Header.getHeaders()) {
            if (this.hasHeader(header.toString())) {
                String contentType = header.toString() + ": " + this.response.getHeaders().get(header.toString()) + "\n";
                write(contentType.getBytes());
            }
        }
    }

    private void writeBody() throws IOException {
        if (this.bodyIsNotEmpty()) {
            this.writeEmptyLine();
            write(response.getBodyContent());
        }
    }

    private void write (byte[] bytes) throws IOException {
        this.outputStream.write(bytes);
    }

    private boolean bodyIsNotEmpty()
    {
        return response.getBodyContent() != null;
    }


    private void writeEmptyLine() throws IOException {
        write(("\r\n").getBytes());
    }

    private boolean hasHeader(String header) {
        return (this.response.getHeaders().containsKey(header));
    }
}
