package httpserver.utilities;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encoder {


    public String getHash(byte[] byteArray, String algorithm) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(byteArray);
            byte[] digestedBytes = messageDigest.digest();
            return DatatypeConverter.printHexBinary(digestedBytes).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
