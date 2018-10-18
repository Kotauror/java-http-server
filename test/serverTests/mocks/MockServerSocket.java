package serverTests.mocks;

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
    public MockSocket accept() throws IOException {
        if (this.errorStatus == true) {
            throw new IOException();
        } else {
            return this.mockSocket;
        }
    }

    public void setIOException() {
        this.errorStatus = true;
    }
}
