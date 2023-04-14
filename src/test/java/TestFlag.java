import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class TestFlag {
    @Test
    public void flagO() throws FileNotFoundException {
        Path name = Paths.get("input", "фейрверкOut.txt");
        Path out = Paths.get("input", "фейрверк2.txt");
        UniqParser args = new UniqParser(false, false, false, 0,
                false, true, out, name);
        ConsoleApp.start(args);
    }
    @Test
    public void flagIS() throws FileNotFoundException{
        Path name = Paths.get("input", "фейрверкOut.txt");
        Path out = Paths.get("input", "null.txt");
        UniqParser args = new UniqParser(true, false, true, 22,
                false, false, out, name);
        ConsoleApp.start(args);
    }
    @Test
    public void flagCU() throws FileNotFoundException{
        Path out = Paths.get("input", "null.txt");
        Path name = Paths.get("input", "монолог.txt");
        UniqParser args = new UniqParser(false, true, false, 0,
                true, false, out, name);
        ConsoleApp.start(args);
    }
    @Test
    public void flagUS() throws FileNotFoundException{
        Path out = Paths.get("input", "null.txt");
        Path name = Paths.get("input", "фейрверкOut.txt");
        UniqParser args = new UniqParser(true, true, true, 22,
                false, false,  out, name);
        ConsoleApp.start(args);
    }
}
