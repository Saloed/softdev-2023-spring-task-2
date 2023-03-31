import org.junit.jupiter.api.Test;

public class TestSplit {
    @Test
    public void test() throws Exception {
        String[]args={"-d","-l","37","src/test/resources/file.txt"};
        Main.main(args);
        //Files.deleteIfExists(Path.of("softdev-2023-spring-task-2"));
    }
    @Test
    public void test_l_base() throws Exception {
        String[]args={"-d","-l","src/test/resources/test1.txt"};
        Main.main(args);
    }
    @Test
    public void test1() throws Exception {
        String[]args={"-c","500","-o","-","src/test/resources/file.txt"};
        Main.main(args);
    }
    @Test
    public void test2() throws Exception {
        String[]args={"-c","500","-o","outFile","src/test/resources/file.txt"};
        Main.main(args);
    }
    @Test
    public void test3() throws Exception {
        String[]args={"-d","-n","5","-o","test3_","src/test/resources/f.txt"};
        Main.main(args);
    }
    @Test
    public void test4() throws Exception {
        String[]args={"-d","-n","5","-o","test4_","src/test/resources/file.txt"};
        Main.main(args);
    }
}
