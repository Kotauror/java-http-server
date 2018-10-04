package responseTests;

import httpserver.response.FileTypeDecoder;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class FileTypeDecoderTests {

    private FileTypeDecoder fileTypeDecoder;

    @Before
    public void setup() {
        fileTypeDecoder = new FileTypeDecoder();
    }

    @Test
    public void decodesFileTypeForTxt() {
        String expected = fileTypeDecoder.getFileType("file.txt");
        assertEquals("text/plain", expected);
    }

    @Test
    public void decodesFileTypeForJpeg() {
        String expected = fileTypeDecoder.getFileType("file.jpeg");
        assertEquals("image/jpeg", expected);
    }

    @Test
    public void decodesFileTypeForGif() {
        String expected = fileTypeDecoder.getFileType("file.gif");
        assertEquals("image/gif", expected);
    }


    @Test
    public void decodesFileTypeForPng() {
        String expected = fileTypeDecoder.getFileType("file.png");
        assertEquals("image/png", expected);
    }

    @Test
    public void decodesFileTypeForhtml() {
        String expected = fileTypeDecoder.getFileType("file.html");
        assertEquals("text/html", expected);
    }

    @Test
    public void decodesFileWithoutExtension() {
        String expected = fileTypeDecoder.getFileType("file");
        assertEquals("text/plain", expected);
    }

}
