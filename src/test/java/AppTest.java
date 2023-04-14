import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.example.Main.main;
import static org.junit.jupiter.api.Assertions.*;
import org.kohsuke.args4j.CmdLineException;

import java.io.*;

public class AppTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor)); // Для проверки вывода в консоль
    }
    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
    @org.junit.jupiter.api.Test
    void SingleFile() throws CmdLineException, IOException {
        String[] args = new String[]{"-h", "files/TestingFiles/Test1.txt"};
        main(args);
        assertEquals("Test1.txt 111 2KB", outputStreamCaptor.toString()
                .trim().replaceAll("2023-[\\d\\-:.TZ]+ ","")); //Без проверки даты изменения
        // (иначе, по-моему, тесты почти никогда проходить не будут)
    }
    @org.junit.jupiter.api.Test
    void SingleFileBytes() throws CmdLineException, IOException {
        String[] args = new String[]{"files/TestingFiles/Test1.txt"};
        main(args);
        assertEquals("Test1.txt 111 2415", outputStreamCaptor.toString()
                .trim().replaceAll("2023-[\\d\\-:.TZ]+ ",""));
    }
    @org.junit.jupiter.api.Test
    void Directory() throws CmdLineException, IOException {
        String[] args = new String[]{"-l", "-h", "files/TestingFiles/"};
        main(args);
        assertEquals("Test1.txt 111 2KB" +
                "Test2.txt 111 9KB" +
                "Test3.txt 110 1bytes" +
                "Test4.jpg 111 40KB", outputStreamCaptor.toString()
                .trim().replaceAll("\n", "")
                .replaceAll("\r", "")
                .replaceAll("2023-[\\d\\-:.TZ]+ ",""));
    }
    @org.junit.jupiter.api.Test
    void DirectoryAndOutput() throws CmdLineException, IOException {

        String outputName = "files/Test.txt/";

        String[] args = new String[]{"-l", "-h", "-o", outputName, "files/TestingFiles/"};

        File file = new File(outputName);
        main(args);
        assertEquals("Test1.txt 111 2KB" +
                        "Test2.txt 111 9KB" +
                        "Test3.txt 110 1bytes" +
                        "Test4.jpg 111 40KB",
                FileUtils.readFileToString(file).trim()
                        .replaceAll("\n", "")
                        .replaceAll("\r", "")
                        .replaceAll("2023-[\\d\\-:.TZ]+ ",""));
    }
}
