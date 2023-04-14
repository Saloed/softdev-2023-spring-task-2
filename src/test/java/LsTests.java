import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LsTests {
    public void testOutput(boolean l, boolean h, boolean r, Set<String> expected) throws IOException {
        String dir = "files";
        String o = "output.txt";
        Args args = new Args(l, h, r, o, dir);

        Main.start(args);
        File output = new File(args.out);

        FileReader fileRead = new FileReader(output);
        BufferedReader reader = new BufferedReader(fileRead);
        Set<String> setOfFiles = new HashSet<>();
        String line = reader.readLine();
        while (line != null) {
            setOfFiles.add(line);
            line = reader.readLine();
        }

        Set<String> setWithoutTime = new HashSet<>();
        for (String i: setOfFiles) {
            String newString = i.replaceAll(" \\d+/\\d+/\\d+ \\d+:\\d+:\\d+","");
            setWithoutTime.add(newString);
        }

        assertEquals(expected, setWithoutTime);
        output.delete();
    }

    @Test
    public void testL() throws IOException {
        testOutput(true, false, false,
                Set.of("Vopros_10.docx 110 88041",
                "Vopros_21.docx 110 55720",
                "Vopros_3.docx 110 160181",
                "Vopros_9.docx 110 74587"));

    }

  @Test
    public void testH() throws IOException {
        testOutput(true, true, false,
                Set.of("Vopros_10.docx rw- 86.0KB",
                        "Vopros_21.docx rw- 54.4KB",
                        "Vopros_3.docx rw- 156.4KB",
                        "Vopros_9.docx rw- 72.8KB"));
    }

    @Test
    public void testR() throws IOException {
        testOutput(false, false, true,
                Set.of("Vopros_9.docx",
                        "Vopros_3.docx",
                        "Vopros_21.docx",
                        "Vopros_10.docx"));
    }

    @Test
    public void testWithoutArguments() throws IOException {
        testOutput(false, false, false,
                Set.of("Vopros_10.docx",
                        "Vopros_21.docx",
                        "Vopros_3.docx",
                        "Vopros_9.docx"));
    }

    @Test
    public void testLR() throws IOException {
        testOutput(true, false, true,
                Set.of("Vopros_9.docx 110 74587",
                        "Vopros_3.docx 110 160181",
                        "Vopros_21.docx 110 55720",
                        "Vopros_10.docx 110 88041"));
    }
}
