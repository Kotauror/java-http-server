package responseTests;

import httpserver.response.FileContentConverter;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertArrayEquals;

public class FileContentConverterTests {

    private FileContentConverter fileContentConverter;

    @Before
    public void setup() {
        fileContentConverter = new FileContentConverter();
    }

    @Test
    public void transformsFileIntoArrayOfBytes() throws IOException {
        String filePath = "test/responseTests/sampleTestFiles/testFile.txt";
        byte[] fileContent = "test strjjing hehhe\n".getBytes();
        Files.write(Paths.get(filePath), fileContent);

        byte[] actual = fileContentConverter.getFileContent(new File(filePath));

        assertArrayEquals(fileContent, actual);
    }
}
