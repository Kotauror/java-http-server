package httpserver.server;

import java.util.ArrayList;
import java.util.Collections;

public class MockServerStatus extends ServerStatus {

    private ArrayList runServerBooleans;

    public MockServerStatus() {
        this.runServerBooleans = new ArrayList<Boolean>();
        fillBooleans(true, false);
    }

    private void fillBooleans(boolean runServer, boolean stopServer) {
        Collections.addAll(this.runServerBooleans, runServer);
        Collections.addAll(this.runServerBooleans, stopServer);
    }

    public boolean isRunning() {
        Boolean firstBoolean = (Boolean) this.runServerBooleans.get(0);
        this.runServerBooleans.remove(0);
        return firstBoolean;
    }
}
