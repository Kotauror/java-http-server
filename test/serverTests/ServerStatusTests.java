package serverTests;

import httpserver.server.ServerStatus;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class ServerStatusTests {

    private ServerStatus serverStatus;

    @Before
    public void setup() {
        serverStatus = new ServerStatus();
    }

    @Test
    public void isRunningReturnsTrue() {
        assertTrue(serverStatus.isRunning());
    }
}
