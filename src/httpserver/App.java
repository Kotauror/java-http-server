package httpserver;

import httpserver.handlers.RequestRouter;
import httpserver.request.RequestParser;
import httpserver.server.ServerStatus;
import httpserver.server.WebServer;

import java.io.IOException;
import java.net.ServerSocket;

public class App {

    public static void main(String[] args) throws IOException {
        int portNumber = Integer.parseInt(args[1]);
        String pathToFileDirectory = args[3];

        ServerSocket serverSocket = new ServerSocket(portNumber);
        ServerStatus serverStatus = new ServerStatus();
        RequestParser requestParser = new RequestParser();
        RequestRouter requestRouter = new RequestRouter();
        WebServer webServer = new WebServer(System.out, serverSocket, serverStatus, requestParser, requestRouter);
        webServer.start();
    }
}
