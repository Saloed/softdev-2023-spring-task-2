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
        System.out.println(FileInfoList.setOfFiles);
        assertEquals(expected, FileInfoList.setOfFiles);
        output.delete();
    }

    @Test
    public void testL() throws IOException {
        testOutput(true, false, false,
                Set.of("Vopros_10.docx 110 88041",
                "Vopros_21.docx 110 55720",
                "Vopros_3.docx 110 160181",
                "Vopros_9.docx 110 74587"));
        FileInfoList.setOfFiles.clear();
    }

  @Test
    public void testH() throws IOException {
        testOutput(true, true, false,
                Set.of("Vopros_10.docx rw- 86,0KB",
                        "Vopros_21.docx rw- 54,4KB",
                        "Vopros_3.docx rw- 156,4KB",
                        "Vopros_9.docx rw- 72,8KB"));
      FileInfoList.setOfFiles.clear();
    }

    @Test
    public void testR() throws IOException {
        testOutput(false, false, true,
                Set.of("Vopros_9.docx",
                        "Vopros_3.docx",
                        "Vopros_21.docx",
                        "Vopros_10.docx"));
        FileInfoList.setOfFiles.clear();
    }

    @Test
    public void testWithoutArguments() throws IOException {
        testOutput(false, false, false,
                Set.of("Vopros_10.docx",
                        "Vopros_21.docx",
                        "Vopros_3.docx",
                        "Vopros_9.docx"));
        FileInfoList.setOfFiles.clear();
    }

    @Test
    public void testLR() throws IOException {
        testOutput(true, false, true,
                Set.of("Vopros_9.docx 110 74587",
                        "Vopros_3.docx 110 160181",
                        "Vopros_21.docx 110 55720",
                        "Vopros_10.docx 110 88041"));
        FileInfoList.setOfFiles.clear();
    }
}
