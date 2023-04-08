import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LsTests {
    public void testOutput(boolean l, boolean h, boolean r, String expected) throws IOException {
        String dir = "files";
        String o = "output.txt";
        Args args = new Args(l, h, r, o, dir);
        Main.start(args);
        File output = new File(args.out);
        System.out.println(FileUtils.readFileToString(output).trim()
                .replaceAll(" \\d+/\\d+/\\d+ \\d+:\\d+:\\d+",""));
        assertEquals(expected, FileUtils.readFileToString(output).trim()
                .replaceAll(" \\d+/\\d+/\\d+ \\d+:\\d+:\\d+",""));
        output.delete();
    }

    String newLine = System.lineSeparator();

    @Test
    public void testL() throws IOException {
        testOutput(true, false, false,
                "Vopros_10.docx 111 88041" + newLine
                        + "Vopros_21.docx 111 55720" + newLine
                        + "Vopros_3.docx 111 160181" + newLine
                        + "Vopros_9.docx 111 74587");
    }

    @Test
    public void testH() throws IOException {
        testOutput(true, true, false,
                "Vopros_10.docx rwx 86,0KB" + newLine
                        + "Vopros_21.docx rwx 54,4KB" + newLine
                        + "Vopros_3.docx rwx 156,4KB" + newLine
                        + "Vopros_9.docx rwx 72,8KB");
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
                "Vopros_9.docx 111 74587" + newLine
                        + "Vopros_3.docx 111 160181" + newLine
                        + "Vopros_21.docx 111 55720" + newLine
                        + "Vopros_10.docx 111 88041");
    }
}
