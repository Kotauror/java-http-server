package serverTests;

import httpserver.server.Logger;
import httpserver.server.LoggerHeader;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoggerTests {

    private Logger logger;

    @Before
    public void setup() {
        logger = new Logger();
    }

    @Test
    public void addLogAboutNewSocket() {
        logger.addNewSocketLog("123 socket address");
        assertTrue(logger.getLogs().containsKey(LoggerHeader.NEW_SOCKET));
    }

    @Test
    public void addLogAboutConnectionException() {
        logger.addConnectionException("There was error in connection etc");
        assertTrue(logger.getLogs().containsKey(LoggerHeader.CONNECTION_EXCEPTION));
    }

    @Test
    public void addLogAboutResponseException() {
        logger.addResponseException("There was error in response etc");
        assertTrue(logger.getLogs().containsKey(LoggerHeader.RESPONSE_EXCEPTION));
    }

    @Test
    public void filtersLogs() {
        logger.addNewSocketLog("1aa23 socket");
        logger.addConnectionException("fale request");

        HashMap<LoggerHeader, String> actual = logger.filterByLoggerHeader(LoggerHeader.NEW_SOCKET);

        assertTrue(actual.size() == 1);
        assertFalse(actual.containsKey(LoggerHeader.CONNECTION_EXCEPTION));
    }
}
