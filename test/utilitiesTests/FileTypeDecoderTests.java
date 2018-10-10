package utilitiesTests;

import httpserver.utilities.FileType;
import httpserver.utilities.FileTypeDecoder;
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
        FileType expected = fileTypeDecoder.getFileType("file.txt");
        assertEquals("text/plain", expected.value());
    }

    @Test
    public void decodesFileTypeForJpeg() {
        FileType expected = fileTypeDecoder.getFileType("file.jpeg");
        assertEquals("image/jpeg", expected.value());
    }

    @Test
    public void decodesFileTypeForGif() {
        FileType expected = fileTypeDecoder.getFileType("file.gif");
        assertEquals("image/gif", expected.value());
    }


    @Test
    public void decodesFileTypeForPng() {
        FileType expected = fileTypeDecoder.getFileType("file.png");
        assertEquals("image/png", expected.value());
    }

    @Test
    public void decodesFileTypeForhtml() {
        FileType expected = fileTypeDecoder.getFileType("file.html");
        assertEquals("text/html", expected.value());
    }

    @Test
    public void decodesFileWithoutExtension() {
        FileType expected = fileTypeDecoder.getFileType("file");
        assertEquals("text/plain", expected.value());
    }

}
