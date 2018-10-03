package httpserver;

import httpserver.handlers.RequestRouter;
import httpserver.request.RequestParser;
import httpserver.server.ServerStatus;
import httpserver.server.WebServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class App {

    public static void main(String[] args) throws IOException {
        int portNumber = Integer.parseInt(args[1]);
        String rootPath = args[3];

        ServerSocket serverSocket = new ServerSocket(portNumber);
        ServerStatus serverStatus = new ServerStatus();
        RequestParser requestParser = new RequestParser();
        System.out.println(rootPath);
        RequestRouter requestRouter = new RequestRouter(rootPath);
        Executor executor = Executors.newFixedThreadPool(7);
        WebServer webServer = new WebServer(System.out, serverSocket, serverStatus, requestParser, requestRouter, executor);
        webServer.start();
    }
}
