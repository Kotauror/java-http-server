package utilitiesTests;

import httpserver.utilities.Encoder;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class EncoderTests {

    private Encoder encoder;

    @Before
    public void setup() {
        encoder = new Encoder();
    }

    @Test
    public void encodesArrayOfBytesIntoStringUsingGivenAlgorithm() {
        byte[] byteArray = "abc".getBytes();
        String algorithm = "SHA-1";
        String expectedHash = "a9993e364706816aba3e25717850c26c9cd0d89d";

        String actualHash = encoder.encode(byteArray, algorithm);

        assertEquals(expectedHash, actualHash);
    }

    @Test
    public void returnsNullWhenPassedAlgorithmDoestExist() {
        byte[] byteArray = "abc".getBytes();
        String algorithm = "SHA-1DoestExist";

        String actualHash = encoder.encode(byteArray, algorithm);

        assertNull(actualHash);
    }
}
