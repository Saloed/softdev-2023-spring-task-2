import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;

public class testF {
    @Test
        public void flagI() throws IOException {
        String args[] = {"-i", "input\\yesenin.txt" };
        consoleApp.main(args);
    }

    @Test
        public void flagU() throws IOException {
        String args[] = {"-u", "input\\times.txt"};
        consoleApp.main(args);
    }

    @Test
        public void flagC() throws IOException {
        String args[] = {"-c", "input\\повторы.txt"};
        consoleApp.main(args);
    }

    @Test
        public void flagS() throws IOException {
        String args[] = {"-s", "3", "input\\numTest.txt"};
        consoleApp.main(args);
    }

    @Test
        public void flagO() throws IOException {
        String args[] = {"-o", "timesOut.txt", "-i", "input\\times.txt"};
        consoleApp.main(args);
    }

    @Test
       public void osiu() throws IOException {
        String args[] = {"-o", "фейрверкSIU.txt", "-s", "22","-i", "u", "input\\фейрверк.txt"};
        consoleApp.main(args);
    }
    @Test
    public void oisu() throws IOException {
        String args[] = {"-o", "фейрверкISU.txt","-i", "-s", "22", "u", "input\\фейрверк.txt"};
        consoleApp.main(args);
    }
    @Test
    public void uс() throws IOException {
        String args[] = {"-u", "-c", "input\\монолог.txt"};
        consoleApp.main(args);
    }
    @Test
    public void cu() throws IOException {
        String args[] = {"-c", "-u", "input\\монолог.txt"};
        consoleApp.main(args);
    }
}
