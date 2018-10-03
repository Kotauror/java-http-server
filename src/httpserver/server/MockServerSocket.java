package httpserver.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ServerSocket;

public class MockServerSocket extends ServerSocket {


    private final MockSocket mockSocket;

    public MockServerSocket(MockSocket mockSocket) throws IOException {
        this.mockSocket = mockSocket;
    }

    @Override
    public MockSocket accept() {
        return this.mockSocket;
    }
}
