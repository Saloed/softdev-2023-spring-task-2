import org.apache.commons.io.FileUtils;
import org.example.Main;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.kohsuke.args4j.CmdLineException;

import java.io.*;

public class AppTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }
    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
    @org.junit.jupiter.api.Test
    void SingleFile() throws CmdLineException, IOException {
        String[] args = new String[]{"-h",
                "D:\\IntelliJ IDEA Community Edition 2022.2.1\\softdev-2023-spring-task-2\\files\\TestingFiles\\Test1.txt"};
        Main newMain = new Main();
        newMain.argsParse(args);
        newMain.app();
        assertEquals("111 2023-03-21T17:15:43.7193103Z 2KB", outputStreamCaptor.toString()
                .trim());
    }
    @org.junit.jupiter.api.Test
    void SingleFileBytes() throws CmdLineException, IOException {
        String[] args = new String[]{
                "D:\\IntelliJ IDEA Community Edition 2022.2.1\\softdev-2023-spring-task-2\\files\\TestingFiles\\Test1.txt"};
        Main newMain = new Main();
        newMain.argsParse(args);
        newMain.app();
        assertEquals("111 2023-03-21T17:15:43.7193103Z 2415", outputStreamCaptor.toString()
                .trim());
    }
    @org.junit.jupiter.api.Test
    void Directory() throws CmdLineException, IOException {
        String[] args = new String[]{"-l", "-h",
                "D:\\IntelliJ IDEA Community Edition 2022.2.1\\softdev-2023-spring-task-2\\files\\TestingFiles\\"};
        Main newMain = new Main();
        newMain.argsParse(args);
        newMain.app();
        assertEquals("Test1.txt 111 2023-03-21T17:15:43.7193103Z 2KB" +
                "Test2.txt 111 2023-03-21T17:16:18.0868823Z 9KB" +
                "Test3.txt 110 2023-03-21T17:16:24.8923173Z 1bytes" +
                "Test4.jpg 111 2020-10-26T18:38:04Z 40KB", outputStreamCaptor.toString()
                .trim().replaceAll("\n", "").replaceAll("\r", ""));
    }
    @org.junit.jupiter.api.Test
    void DirectoryAndOutput() throws CmdLineException, IOException {

        String outputName =
                "D:\\IntelliJ IDEA Community Edition 2022.2.1\\softdev-2023-spring-task-2\\files\\Test.txt\\";

        String[] args = new String[]
                {"-l", "-h", "-o", outputName,
                        "D:\\IntelliJ IDEA Community Edition 2022.2.1\\softdev-2023-spring-task-2\\files\\TestingFiles\\"};

        File file = new File(outputName);
        Main newMain = new Main();
        newMain.argsParse(args);
        newMain.app();
        assertEquals("Test1.txt 111 2023-03-21T17:15:43.7193103Z 2KB" +
                        "Test2.txt 111 2023-03-21T17:16:18.0868823Z 9KB" +
                        "Test3.txt 110 2023-03-21T17:16:24.8923173Z 1bytes" +
                        "Test4.jpg 111 2020-10-26T18:38:04Z 40KB",
                FileUtils.readFileToString(file).trim()
                        .replaceAll("\n", "").replaceAll("\r", ""));
    }
}
