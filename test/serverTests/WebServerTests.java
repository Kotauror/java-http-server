package serverTests;

import httpserver.handlers.RequestRouter;
import httpserver.request.RequestParser;
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
        String requestString = "GET http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1\n" +
                "Host: 0.0.0.0:5000\n" +
                "Content-Length: 23\n\r\n" +
                "nomethod body\ntestbody";
        ByteArrayInputStream mockClientInputStream = new ByteArrayInputStream(requestString.getBytes());
        MockServerSocket mockServerSocket = new MockServerSocket(mockClientInputStream, mockClientOutputStream);
        MockServerStatus mockServerStatus = new MockServerStatus();
        RequestParser requestParser = new RequestParser();
        RequestRouter requestRouter = new RequestRouter();
        webServer = new WebServer(mockSystemOut, mockServerSocket, mockServerStatus, requestParser, requestRouter);
    }

    @Test
    public void tellsItsRunning() throws IOException {
        webServer.start();

        assertEquals("I'm listening for connections", mockOutputStream.toString().trim());
    }
}
