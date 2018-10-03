package httpserver.server;

import httpserver.handlers.Handler;
import httpserver.handlers.RequestRouter;
import httpserver.request.Request;
import httpserver.request.RequestParser;
import httpserver.response.Response;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

    private final PrintStream stdOut;
    private final ServerSocket serverSocket;
    private final ServerStatus serverStatus;
    private final RequestParser requestParser;
    private final RequestRouter requestRouter;

    public WebServer(PrintStream stdOut, ServerSocket serverSocket, ServerStatus serverStatus, RequestParser requestParser, RequestRouter requestRouter) {
        this.stdOut = stdOut;
        this.serverSocket = serverSocket;
        this.serverStatus = serverStatus;
        this.requestParser = requestParser;
        this.requestRouter = requestRouter;
    }

    public void start() throws IOException {
        stdOut.println("I'm listening for connections");
        while (serverStatus.isRunning()) {
            Socket clientConnection = serverSocket.accept();
            ConnectionManager connectionManager = new ConnectionManager(clientConnection, this.requestParser, this.requestRouter);
            connectionManager.handleConnection();
        }
    }
}
