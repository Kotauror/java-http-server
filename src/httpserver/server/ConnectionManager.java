package httpserver.server;

import httpserver.handlers.Handler;
import httpserver.handlers.RequestRouter;
import httpserver.request.Request;
import httpserver.request.RequestParser;
import httpserver.response.Response;
import httpserver.response.ResponseWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ConnectionManager extends Thread {

    private final Socket clientSocket;
    private final OutputStream writer;
    private final RequestParser requestParser;
    private final RequestRouter requestRouter;

    public ConnectionManager(Socket clientSocket, RequestParser requestParser, RequestRouter requestRouter) throws IOException {
        this.clientSocket = clientSocket;
        this.writer = clientSocket.getOutputStream();
        this.requestParser = requestParser;
        this.requestRouter = requestRouter;
    }

    @Override
    public void run() {
        try {
            this.handleConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleConnection() throws IOException {
        Request request = this.requestParser.parse(clientSocket.getInputStream());
        Handler handler = this.requestRouter.findHandler(request);
        Response response = handler.getResponse(request);
        ResponseWriter responseWriter = new ResponseWriter(writer, response);
        responseWriter.write();
        clientSocket.close();
    }
}
