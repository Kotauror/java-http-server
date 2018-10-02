import httpserver.server.MockServerSocket;
import httpserver.server.MockServerStatus;
import httpserver.server.WebServer;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static junit.framework.Assert.assertEquals;

public class WebServerTests {

    private WebServer webServer;
    private ByteArrayOutputStream mockOutputStream;

    @Before
    public void setup() throws IOException {
        mockOutputStream = new ByteArrayOutputStream();
        PrintStream mockSystemOut = new PrintStream(mockOutputStream);
        ByteArrayOutputStream mockClientOutputStream = new ByteArrayOutputStream();
        ByteArrayInputStream mockClientInputStream = new ByteArrayInputStream("test String".getBytes());
        MockServerSocket mockServerSocket = new MockServerSocket(mockClientInputStream, mockClientOutputStream);
        MockServerStatus mockServerStatus = new MockServerStatus();
        webServer = new WebServer(mockSystemOut, mockServerSocket, mockServerStatus);
    }

    @Test
    public void tellsItsRunning() throws IOException {
        webServer.start();

        assertEquals("I'm listening for connections", mockOutputStream.toString().trim());
    }
}
