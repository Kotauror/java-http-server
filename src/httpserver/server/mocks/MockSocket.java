package httpserver.server.mocks;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.Socket;

public class MockSocket extends Socket {

    private final ByteArrayOutputStream output;
    private final ByteArrayInputStream input;

    public MockSocket(ByteArrayOutputStream outputStream, ByteArrayInputStream inputStream) {
        this.output = outputStream;
        this.input = inputStream;
    }

    @Override
    public ByteArrayOutputStream getOutputStream() {
        return output;
    }

    @Override
    public ByteArrayInputStream getInputStream() {
        return input;
    }
}
