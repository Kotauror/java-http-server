package serverTests;

import httpserver.request.RequestBuilder;
import httpserver.request.RequestRouter;
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
import static org.junit.Assert.assertTrue;

public class WebServerTests {

    private ByteArrayOutputStream mockOutputStream;
    private ByteArrayOutputStream mockClientOutputStream;
    private MockServerStatus mockServerStatus;
    private RequestParser requestParser;
    private String rootPath;
    private RequestRouter requestRouter;
    private CurrentThreadExecutor executor;
    private PrintStream mockSystemOut;
    private Logger logger;

    @Before
    public void setup() {
        // Server output
        mockOutputStream = new ByteArrayOutputStream();
        mockSystemOut = new PrintStream(mockOutputStream);
        // Objects passed to the server on creation
        mockServerStatus = new MockServerStatus();
        requestParser = new RequestParser(new RequestBuilder());
        rootPath = "/Users/justynazygmunt/Desktop/cob_spec/public/";
        logger = new Logger();
        requestRouter = new RequestRouter(rootPath, logger);
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
        WebServer webServer = new WebServer(mockSystemOut, mockServerSocket, mockServerStatus, requestParser, requestRouter, executor, logger);

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
        WebServer webServer = new WebServer(mockSystemOut, mockServerSocket, mockServerStatus, requestParser, requestRouter, executor, logger);

        webServer.start();

        assertTrue(mockClientOutputStream.toString().trim().contains("HTTP/1.1 200"));
        assertTrue(mockClientOutputStream.toString().trim().contains("Content-Type: text/plain"));
        assertTrue(mockClientOutputStream.toString().trim().contains("file1 contents"));
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
        WebServer webServer = new WebServer(mockSystemOut, mockServerSocket, mockServerStatus, requestParser, requestRouter, executor, logger);

        webServer.start();

        assertEquals("HTTP/1.1 404", mockClientOutputStream.toString().trim());
    }

    @Test
    public void serverLogHasLogAboutConnectionException_whenThereWasAnErrorInConnectingWithClientSocket() throws IOException {
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
        mockServerSocket.setIOException();
        // Server
        WebServer webServer = new WebServer(mockSystemOut, mockServerSocket, mockServerStatus, requestParser, requestRouter, executor, logger);

        webServer.start();

        assertTrue(logger.getLogs().containsKey(LoggerHeader.CONNECTION_EXCEPTION));
    }
}
