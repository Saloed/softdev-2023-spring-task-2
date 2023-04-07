import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UniqTest {
    @Test
    public void test(){
           String[] args = {"-o","output.txt","input\\test1.txt"};
        Assertions.assertEquals("""
                Hello, World
                hello, world
                Hello, Worlds
                Hi, Yooondooo""", Uniq.main(args));

    }
    @Test
    public void test2(){
        String[] args = {"-o","output.txt","input\\test1.txt"};
        Assertions.assertEquals("""
                Hello, World
                hello, world
                Hello, Worlds
                Hi, Yooondooo""", Uniq.main(args));
    }
    @Test
    public void test3(){
        String[] args = {"-o","output.txt","-i","input\\test1.txt"};
        Assertions.assertEquals("""
                hello, world
                Hello, Worlds
                Hi, Yooondooo""", Uniq.main(args));
    }
    @Test
    public void test4(){
        String[] args = {"-o","output.txt","-c","input\\test1.txt"};
        Assertions.assertEquals("""
                2 Hello, World
                2 hello, world
                0 Hello, Worlds
                2 Hi, Yooondooo""", Uniq.main(args));
    }
    @Test
    public void test5(){
        String[] args = {"-o","output.txt","-c","-i","input\\test1.txt"};
        Assertions.assertEquals("""
                4 hello, world
                0 Hello, Worlds
                2 Hi, Yooondooo""", Uniq.main(args));
    }
    @Test
    public void test6(){
        String[] args = {"-o","output.txt","-u","input\\test1.txt"};
        Assertions.assertEquals("Hello, Worlds", Uniq.main(args));
    }
}
