package httpserver.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

    private final PrintStream stdOut;
    private final ServerSocket serverSocket;
    private final ServerStatus serverStatus;

    public WebServer(PrintStream stdOut, ServerSocket serverSocket, ServerStatus serverStatus) {
        this.stdOut = stdOut;
        this.serverSocket = serverSocket;
        this.serverStatus = serverStatus;
    }

    public void start() throws IOException {
        stdOut.println("I'm listening for connections");
        while (serverStatus.isRunning()) {
            Socket clientConnection = serverSocket.accept();
            PrintWriter pw = new PrintWriter(clientConnection.getOutputStream(), true);
            pw.println("HTTP/1.1 404");
            pw.close();
        }
    }
}
