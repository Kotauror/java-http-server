package httpserver.server.mocks;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MockSocket extends Socket {

    private final ByteArrayOutputStream output;
    private final ByteArrayInputStream input;
    private boolean errorStatus;

    public MockSocket(ByteArrayOutputStream outputStream, ByteArrayInputStream inputStream) {
        this.output = outputStream;
        this.input = inputStream;
        this.errorStatus = false;
    }

    @Override
    public ByteArrayOutputStream getOutputStream() throws IOException {
        if (this.errorStatus) {
            throw new IOException();
        } else {
            return output;
        }
    }

    @Override
    public ByteArrayInputStream getInputStream() {
        return this.input;
    }

    public void setIOException() {
        this.errorStatus = true;
    }
}
