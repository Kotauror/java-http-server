package serverTests;

import httpserver.server.ServerStatus;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class ServerStatusTests {

    private ServerStatus serverStatus;

    @Before
    public void setup() {
        serverStatus = new ServerStatus();
    }

    @Test
    public void isRunningReturnsTrue() {
        assertEquals(true, serverStatus.isRunning());
    }
}
