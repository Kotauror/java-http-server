package serverTests;

import httpserver.handlers.RequestRouter;
import httpserver.request.RequestParser;
import httpserver.server.*;
import httpserver.server.mocks.MockServerSocket;
import httpserver.server.mocks.MockServerStatus;
import httpserver.server.mocks.MockSocket;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static junit.framework.Assert.assertEquals;

public class WebServerTests {

    private WebServer webServer;
    private ByteArrayOutputStream mockOutputStream;
    private ByteArrayOutputStream mockClientOutputStream;

    @Before
    public void setup() throws IOException {
        // Server output
        mockOutputStream = new ByteArrayOutputStream();
        PrintStream mockSystemOut = new PrintStream(mockOutputStream);
        // Client Input
        String requestString = "GET http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1\n" +
                "Host: 0.0.0.0:5000\n" +
                "Content-Length: 23\n\r\n" +
                "nomethod body\ntestbody";
        ByteArrayInputStream mockInputSteam = new ByteArrayInputStream(requestString.getBytes());
        // Client Output
        mockClientOutputStream = new ByteArrayOutputStream();
        // Client Socket
        MockSocket mockSocket = new MockSocket(mockClientOutputStream, mockInputSteam);

        MockServerSocket mockServerSocket = new MockServerSocket(mockSocket);
        MockServerStatus mockServerStatus = new MockServerStatus();
        RequestParser requestParser = new RequestParser();
        RequestRouter requestRouter = new RequestRouter();
        Executor executor = Executors.newFixedThreadPool(20);
        Executor executor1 = new CurrentThreadExecutor();
        webServer = new WebServer(mockSystemOut, mockServerSocket, mockServerStatus, requestParser, requestRouter, executor1);
    }

    @Test
    public void serverTellsItsRunning() {
        webServer.start();
        assertEquals("I'm listening for connections", mockOutputStream.toString().trim());
    }

    @Test
    public void integrationServerSendsResponseToClient(){
        webServer.start();

        assertEquals("HTTP/1.1 404", mockClientOutputStream.toString().trim());
    }
}
