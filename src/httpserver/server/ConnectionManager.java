package httpserver.server;

import httpserver.handlers.Handler;
import httpserver.request.RequestRouter;
import httpserver.request.Request;
import httpserver.request.RequestParser;
import httpserver.response.Response;
import httpserver.response.ResponseWriter;

import java.io.IOException;
import java.net.Socket;

public class ConnectionManager extends Thread {

    private final Socket clientSocket;
    private final RequestParser requestParser;
    private final RequestRouter requestRouter;
    private final ResponseWriter responseWriter;

    public ConnectionManager(Socket clientSocket, ResponseWriter responseWriter, RequestParser requestParser, RequestRouter requestRouter) {
        this.clientSocket = clientSocket;
        this.requestParser = requestParser;
        this.requestRouter = requestRouter;
        this.responseWriter = responseWriter;
    }

    @Override
    public void run() {
        try {
            this.handleConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleConnection() throws IOException {
        Request request = this.requestParser.parse(this.clientSocket.getInputStream());
        Handler handler = this.requestRouter.findHandler(request);
        Response response = handler.processRequest(request);
        this.responseWriter.write(response);
        this.clientSocket.close();
    }
}
