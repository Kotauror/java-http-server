package httpserver.server;

import java.util.HashMap;
import java.util.Map;

public class Logger {

    private HashMap<LoggerHeader, String> logs;

    public Logger() {
        this.logs = new HashMap<>();
    }

    public void addNewSocketLog(String socketAddress) {
        logs.put(LoggerHeader.NEW_SOCKET, socketAddress);
    }

    public void addRequestLog(String request) {
        logs.put(LoggerHeader.REQUEST, request);
    }

    public HashMap<LoggerHeader, String> getLogs() {
        return this.logs;
    }

    public HashMap<LoggerHeader, String> filterByLoggerHeader(LoggerHeader myLoggerHeader) {
        HashMap<LoggerHeader, String> filtered = new HashMap<>();
        for (Map.Entry<LoggerHeader, String> entry : logs.entrySet()) {
            if (entry.getKey() == myLoggerHeader) {
                filtered.put(myLoggerHeader, entry.getValue());
            }
        }
       return filtered;
    }
}
