import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LsTests {
    public void testOutput(boolean l, boolean h, boolean r, Set<String> expected) throws IOException {
        String dir = "files";
        String o = "output.txt";
        Args args = new Args(l, h, r, o, dir);
        Main.start(args);
        File output = new File(args.out);
        assertEquals(expected, FileInfoList.setOfFiles);
        FileInfoList.setOfFiles.clear();
        output.delete();
    }

    @Test
    public void testL() throws IOException {
        testOutput(true, false, false,
                Set.of("Vopros_10.docx 111 88041",
                "Vopros_21.docx 111 55720",
                "Vopros_3.docx 111 160181",
                "Vopros_9.docx 111 74587"));
    }

  @Test
    public void testH() throws IOException {
        testOutput(true, true, false,
                Set.of("Vopros_10.docx rwx 86,0KB",
                        "Vopros_21.docx rwx 54,4KB",
                        "Vopros_3.docx rwx 156,4KB",
                        "Vopros_9.docx rwx 72,8KB"));
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
                Set.of("Vopros_9.docx 111 74587",
                        "Vopros_3.docx 111 160181",
                        "Vopros_21.docx 111 55720",
                        "Vopros_10.docx 111 88041"));
    }
}
