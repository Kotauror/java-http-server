package httpserver.server;

import httpserver.request.RequestRouter;
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
    private final Logger logger;

    public WebServer(PrintStream stdOut, ServerSocket serverSocket, ServerStatus serverStatus,
                     RequestParser requestParser, RequestRouter requestRouter, Executor executor, Logger logger) {
        this.stdOut = stdOut;
        this.serverSocket = serverSocket;
        this.serverStatus = serverStatus;
        this.requestParser = requestParser;
        this.requestRouter = requestRouter;
        this.executor = executor;
        this.logger = logger;
    }

    public void start() {
        stdOut.println("I'm listening for connections");
        while (serverStatus.isRunning()) {
            this.connectWithClients();
        }
    }

    private void connectWithClients() {
        try {
            Socket clientConnection = serverSocket.accept();
            this.logger.addNewSocketLog(Integer.toString(clientConnection.getPort()));
            this.manageConnection(clientConnection);
        } catch (IOException | NullPointerException e) {
            this.logger.addConnectionException(e.getMessage());
        }
    }

    private void manageConnection(Socket clientConnection) {
        try {
            ResponseWriter responseWriter = new ResponseWriter(clientConnection.getOutputStream());
            ConnectionManager connectionManager = new ConnectionManager(clientConnection, responseWriter, this.requestParser, this.requestRouter);
            executor.execute(connectionManager);
        } catch (IOException e) {
            this.logger.addResponseException(e.getMessage());
        }
    }
}
