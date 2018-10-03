package httpserver.response;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseWriter {

    private final OutputStream outputStream;
    private final Response response;

    public ResponseWriter(OutputStream outputStream, Response response) {
        this.outputStream = outputStream;
        this.response = response;
    }

    public void write() throws IOException {
        writeStatusCode();
        writeEmptyLine();
        writeBody();
    }

    private void writeStatusCode() throws IOException {
        String statusLine = response.getHttpVersion() + " " + response.getStatus().getStatus();
        write(statusLine.getBytes());
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
}
