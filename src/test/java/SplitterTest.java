import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

//AAA test methodology
public class SplitterTest {
    @Test
    public void TestSplitLine() throws IOException {
        Splitter splitter = new Splitter(new String[]{"-l", "3", "-o", "out", "src/test/resources/line.txt"});
        assertEquals(splitter.splitLine(), Arrays.asList("123\r\n456\r\n7890", "abcd\r\nefg"));
    }

    @Test
    public void TestSplitChar() throws IOException {
        Splitter splitter = new Splitter(new String[]{"-c", "2", "-o", "out", "src/test/resources/line.txt"});
        assertEquals(splitter.splitChar(), Arrays.asList("12", "34", "56", "78", "90", "ab", "cd", "ef", "g"));
    }

    @Test
    public void TestSplitFile() {
        try {
            Splitter splitter = new Splitter(new String[]{"-n", "2", "-o", "out", "src/test/resources/line.txt"});
            assertEquals(Files.readAllLines(Paths.get("outaa")), Arrays.asList("123", "456", "789"));
            assertEquals(Files.readAllLines(Paths.get("outab")), Arrays.asList("0", "abcd", "efg"));
            splitter.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}