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

import static junit.framework.Assert.assertEquals;

public class WebServerTests {

    private ByteArrayOutputStream mockOutputStream;
    private ByteArrayOutputStream mockClientOutputStream;
    private MockServerStatus mockServerStatus;
    private RequestParser requestParser;
    private String rootPath;
    private RequestRouter requestRouter;
    private CurrentThreadExecutor executor;
    private PrintStream mockSystemOut;

    @Before
    public void setup() {
        // Server output
        mockOutputStream = new ByteArrayOutputStream();
        mockSystemOut = new PrintStream(mockOutputStream);
        // Objects passed to the server on creation
        mockServerStatus = new MockServerStatus();
        requestParser = new RequestParser();
        rootPath = "/Users/justynazygmunt/Desktop/cob_spec/public/";
        requestRouter = new RequestRouter(rootPath);
        executor = new CurrentThreadExecutor();
    }

    @Test
    public void serverTellsItsRunning() throws IOException {
        // Client Input
        String requestString = "GET /file1 HTTP/1.1\n";
        ByteArrayInputStream mockInputSteam = new ByteArrayInputStream(requestString.getBytes());
        // Client Output
        mockClientOutputStream = new ByteArrayOutputStream();
        // Client Socket
        MockSocket mockSocket = new MockSocket(mockClientOutputStream, mockInputSteam);
        // Server
        MockServerSocket mockServerSocket = new MockServerSocket(mockSocket);
        WebServer webServer = new WebServer(mockSystemOut, mockServerSocket, mockServerStatus, requestParser, requestRouter, executor);

        webServer.start();

        assertEquals("I'm listening for connections", mockOutputStream.toString().trim());
    }

    @Test
    public void integrationServerSends200ResponseToClientOnSuccess() throws IOException {
        // Client Input
        String requestString = "GET /file1 HTTP/1.1\n" +
                "Host: 0.0.0.0:5000\n" +
                "Content-Length: 23\n\r\n" +
                "nomethod body\ntestbody";
        ByteArrayInputStream mockInputSteam = new ByteArrayInputStream(requestString.getBytes());
        // Client Output
        mockClientOutputStream = new ByteArrayOutputStream();
        // Client Socket
        MockSocket mockSocket = new MockSocket(mockClientOutputStream, mockInputSteam);
        // Server
        MockServerSocket mockServerSocket = new MockServerSocket(mockSocket);
        WebServer webServer = new WebServer(mockSystemOut, mockServerSocket, mockServerStatus, requestParser, requestRouter, executor);

        webServer.start();

        assertEquals("HTTP/1.1 200\r\nfile1 contents", mockClientOutputStream.toString().trim());
    }

    @Test
    public void integrationServerSends404ResponseToClientWhenFileIsNotThere() throws IOException {
        // Client Input
        String requestString = "GET /fileThatDoesntExist HTTP/1.1\n" +
                "Host: 0.0.0.0:5000\n" +
                "Content-Length: 23\n\r\n" +
                "nomethod body\ntestbody";
        ByteArrayInputStream mockInputSteam = new ByteArrayInputStream(requestString.getBytes());
        // Client Output
        mockClientOutputStream = new ByteArrayOutputStream();
        // Client Socket
        MockSocket mockSocket = new MockSocket(mockClientOutputStream, mockInputSteam);
        MockServerSocket mockServerSocket = new MockServerSocket(mockSocket);
        // Server
        WebServer webServer = new WebServer(mockSystemOut, mockServerSocket, mockServerStatus, requestParser, requestRouter, executor);

        webServer.start();

        assertEquals("HTTP/1.1 404", mockClientOutputStream.toString().trim());
    }
}
