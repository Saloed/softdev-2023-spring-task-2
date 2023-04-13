import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class NewTest {

    private void comparasion(){

    }
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
}
