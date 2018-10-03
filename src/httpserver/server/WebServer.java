package httpserver.server;

import httpserver.handlers.RequestRouter;
import httpserver.request.RequestParser;
import httpserver.response.ResponseWriter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;

public class WebServer {

    private final PrintStream stdOut;
    private final ServerSocket serverSocket;
    private final ServerStatus serverStatus;
    private final RequestParser requestParser;
    private final RequestRouter requestRouter;
    private final Executor executor;

    public WebServer(PrintStream stdOut, ServerSocket serverSocket, ServerStatus serverStatus,
                     RequestParser requestParser, RequestRouter requestRouter, Executor executor) {
        this.stdOut = stdOut;
        this.serverSocket = serverSocket;
        this.serverStatus = serverStatus;
        this.requestParser = requestParser;
        this.requestRouter = requestRouter;
        this.executor = executor;
    }

    public void start() {
        stdOut.println("I'm listening for connections");
        while (serverStatus.isRunning()) {
            try {
                this.connectWithClients();
            } catch (IOException e) {
                stdOut.println("Error in connecting with client");
            }
        }
    }

    private void connectWithClients() throws IOException {
        Socket clientConnection = serverSocket.accept();
        ConnectionManager connectionManager = new ConnectionManager(clientConnection, this.requestParser, this.requestRouter);
        executor.execute(connectionManager);
    }
}
