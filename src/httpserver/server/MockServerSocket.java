package httpserver.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ServerSocket;

public class MockServerSocket extends ServerSocket {

    private final ByteArrayOutputStream output;
    private final ByteArrayInputStream input;

    public MockServerSocket(ByteArrayInputStream inputStream, ByteArrayOutputStream outputStream) throws IOException {
        super();
        this.input = inputStream;
        this.output = outputStream;
    }

    @Override
    public MockSocket accept() {
        return new MockSocket(this.output, this.input);
    }

}
