import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LsTests {
    public void testOutput(boolean l, boolean h, boolean r, String expected) throws IOException {
        String dir = "files";
        String o = "output.txt";
        Args args = new Args(l, h, r, o, dir);
        Main.start(args);
        File output = new File(args.out);
        System.out.println(Files.readString(output.toPath()));
        assertEquals(Files.readString(output.toPath()), expected);
        output.delete();
    }

    String newLine = System.lineSeparator();

    @Test
    public void testL() throws IOException {
        testOutput(true, false, false,
                "Vopros_10.docx 111 88041 31/01/2022 22:32:54" + newLine
                        + "Vopros_21.docx 111 55720 31/01/2022 22:33:00" + newLine
                        + "Vopros_3.docx 111 160181 29/01/2022 17:14:12" + newLine
                        + "Vopros_9.docx 111 74587 31/01/2022 22:32:50");
    }

    @Test
    public void testH() throws IOException {
        testOutput(true, true, false,
                "Vopros_10.docx rwx 86,0KB 31/01/2022 22:32:54" + newLine
                        + "Vopros_21.docx rwx 54,4KB 31/01/2022 22:33:00" + newLine
                        + "Vopros_3.docx rwx 156,4KB 29/01/2022 17:14:12" + newLine
                        + "Vopros_9.docx rwx 72,8KB 31/01/2022 22:32:50");
    }

    @Test
    public void testR() throws IOException {
        testOutput(false, false, true,
                "Vopros_9.docx" + newLine
                        + "Vopros_3.docx" + newLine
                        + "Vopros_21.docx" + newLine
                        + "Vopros_10.docx");
    }

    @Test
    public void testWithoutArguments() throws IOException {
        testOutput(false, false, false,
                "Vopros_10.docx" + newLine
                        + "Vopros_21.docx" + newLine
                        + "Vopros_3.docx" + newLine
                        + "Vopros_9.docx");
    }

    @Test
    public void testLR() throws IOException {
        testOutput(true, false, true,
                "Vopros_9.docx 111 74587 31/01/2022 22:32:50" + newLine
                        + "Vopros_3.docx 111 160181 29/01/2022 17:14:12" + newLine
                        + "Vopros_21.docx 111 55720 31/01/2022 22:33:00" + newLine
                        + "Vopros_10.docx 111 88041 31/01/2022 22:32:54");
    }
}
