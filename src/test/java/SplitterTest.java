import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class SplitterTest {
    @Test
    public void TestSplitLine() throws IOException {
        Splitter splitter = new Splitter(new String[]{"-l", "3", "-o", "out", "src/test/resources/line.txt"});
        assertEquals(splitter.splitLine(), Arrays.asList("123\n456\n7890", "abcd\nefg"));
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
            splitter.save();
            assertEquals(Files.readAllLines(Paths.get("outa")), Arrays.asList("123", "456", "789"));
            assertEquals(Files.readAllLines(Paths.get("outb")), Arrays.asList("0", "abcd", "efg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}