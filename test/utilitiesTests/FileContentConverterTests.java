package utilitiesTests;

import httpserver.utilities.FileContentConverter;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import static org.junit.Assert.assertArrayEquals;

public class FileContentConverterTests {

    private FileContentConverter fileContentConverter;

    @Before
    public void setup() {
        fileContentConverter = new FileContentConverter();
    }

    @Test
    public void transformsFileIntoArrayOfBytes() throws IOException {
        String filePath = "src/httpserver/utilities/sampleTestFiles/testFile.txt";
        byte[] fileContent = "test strjjing hehhe\n".getBytes();
        Files.write(Paths.get(filePath), fileContent);

        byte[] actual = fileContentConverter.getFileContent(new File(filePath));

        assertArrayEquals(fileContent, actual);
    }

    @Test
    public void transformsRangeOfFileIntoArrayOfBytes() throws IOException {
        String filePath = "src/httpserver/utilities/sampleTestFiles/partial_content.txt";
        byte[] fileContent = "This is a file".getBytes();
        Files.write(Paths.get(filePath), fileContent);
        HashMap startEndMap = new HashMap<String, Integer>(){};
        startEndMap.put("start", 0);
        startEndMap.put("end", 13);
        byte[] actual = fileContentConverter.getPartOfFile(new File(filePath), startEndMap);

        assertArrayEquals(fileContent, actual);
    }
}
