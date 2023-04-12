package packrle;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

class AppTest {

    private static boolean comparingFiles(String filename1, String filename2) throws IOException {
        try (InputStream input1 = new FileInputStream(filename1);
             InputStream input2 = new FileInputStream(filename2)) {
            while (true) {
                int byte1 = input1.read();
                int byte2 = input2.read();
                if (byte1 != byte2) return false;
                if (byte1 == -1) return true;
            }
        }
    }

    @Test
    public void processingTest() throws IOException {
        App.process("data/input.txt", "data/output.rle", true);
        App.process("data/output.rle", "data/output.txt", false);
        assertTrue(comparingFiles("data/input.txt", "data/output.txt"));
    }
}
