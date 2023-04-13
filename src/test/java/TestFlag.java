import java.io.FileNotFoundException;
import org.junit.jupiter.api.Test;

public class TestFlag {
    @Test
    public void flagO() throws FileNotFoundException {
        UniqParser args = new UniqParser(false, false, false, 0,
                false, true, "input\\фейрверкOut.txt", "input\\фейрверк.txt");
        ConsoleApp.start(args);
    }
    @Test
    public void flagIS() throws FileNotFoundException{
        UniqParser args = new UniqParser(true, false, true, 22,
                false, false, "", "input\\фейрверк.txt");
        ConsoleApp.start(args);
    }
    @Test
    public void flagCU() throws FileNotFoundException{
        UniqParser args = new UniqParser(false, true, false, 0,
                true, false, "", "input\\монолог.txt");
        ConsoleApp.start(args);
    }
    @Test
    public void flagUS() throws FileNotFoundException{
        UniqParser args = new UniqParser(true, true, true, 22,
                false, false, "", "input\\фейрверк.txt");
        ConsoleApp.start(args);
    }
}
