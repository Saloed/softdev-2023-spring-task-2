import org.junit.jupiter.api.Test;


public class ConTest {
    @Test
    public void test1() throws Exception{
        String[] args = {"-l", "-h","src/Check1"};
        Main.main(args);
    }
    @Test
    public void test2() throws Exception{
        String[] args = {"-l", "src/Test1.txt"};
        Main.main(args);
    }
    @Test
    public void test3() throws Exception{
        String[] args = {"-o", "Ooout.txt", "src/Test1.txt"};
        Main.main(args);
    }
    @Test
    public void test4() throws Exception{
        String[] args = {"-l", "-h", "-o", "Oout.txt", "src/Test1.txt"};
        Main.main(args);
    }
}
