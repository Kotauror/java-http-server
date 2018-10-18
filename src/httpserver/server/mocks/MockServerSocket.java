package httpserver.server.mocks;

import java.io.IOException;
import java.net.ServerSocket;

public class MockServerSocket extends ServerSocket {


    private final MockSocket mockSocket;
    private boolean errorStatus;

    public MockServerSocket(MockSocket mockSocket) throws IOException {
        this.mockSocket = mockSocket;
        this.errorStatus = false;
    }

    @Override
    public MockSocket accept() {
        return (this.errorStatus == false) ? this.mockSocket : null;
    }

    public void setIOException() {
        this.errorStatus = true;
    }
}
