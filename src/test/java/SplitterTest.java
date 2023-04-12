import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class SplitterTest {
    URL resourceUrl = Splitter.class.getClassLoader().getResource("line.txt");
    String filePath;

    {
        try {
            assert resourceUrl != null;
            filePath = Paths.get(resourceUrl.toURI()).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void splitLineTest() throws IOException {
        Splitter splitter = new Splitter(new String[]{"-l", "3", "-o", "out", filePath});
        assertEquals(splitter.splitLine(), Arrays.asList("123\n456\n7890", "abcd\nefg"));
    }

    @Test
    public void splitCharTest() throws IOException {
        Splitter splitter = new Splitter(new String[]{"-c", "2", "-o", "out", filePath});
        assertEquals(splitter.splitChar(), Arrays.asList("12", "34", "56", "78", "90", "ab", "cd", "ef", "g"));
    }

    @Test
    public void splitFileTest() {
        try {
            Splitter splitter = new Splitter(new String[]{"-n", "2", "-o", "out", filePath});
            splitter.save();
            assertEquals(Files.readAllLines(Paths.get("outa")), Arrays.asList("123", "456", "789"));
            assertEquals(Files.readAllLines(Paths.get("outb")), Arrays.asList("0", "abcd", "efg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}