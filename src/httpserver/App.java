package httpserver;

import httpserver.request.RequestBuilder;
import httpserver.request.RequestRouter;
import httpserver.request.RequestParser;
import httpserver.server.Logger;
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

        RequestBuilder requestBuilder = new RequestBuilder();
        ServerSocket serverSocket = new ServerSocket(portNumber);
        ServerStatus serverStatus = new ServerStatus();
        Logger logger = new Logger();
        RequestParser requestParser = new RequestParser(requestBuilder);
        RequestRouter requestRouter = new RequestRouter(rootPath, logger);
        Executor executor = Executors.newFixedThreadPool(7);
        WebServer webServer = new WebServer(System.out, serverSocket, serverStatus, requestParser, requestRouter, executor, logger);
        webServer.start();
    }
}
