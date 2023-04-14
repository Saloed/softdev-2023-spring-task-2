import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

public class UniqTest {
    @Test
    public void test() {
        String[] args = {"-o", "output.txt", Paths.get("input", "test1.txt").toString()};
        Assertions.assertEquals("""
                Hello, World
                hello, world
                Hello, Worlds
                Hi, Yooondooo""", ForTest.testOut(args));

    }

    @Test
    public void test2() {
        String[] args = {"-o", "output.txt", "-s", "8", Paths.get("input", "test1.txt").toString()};
        Assertions.assertEquals("""
                hello, world
                Hello, Worlds
                Hi, Yooondooo""", ForTest.testOut(args));
    }

    @Test
    public void test3() {
        String[] args = {"-o", "output.txt", "-i", Paths.get("input", "test1.txt").toString()};
        Assertions.assertEquals("""
                hello, world
                Hello, Worlds
                Hi, Yooondooo""", ForTest.testOut(args));
    }

    @Test
    public void test4() {
        String[] args = {"-o", "output.txt", "-c", Paths.get("input", "test1.txt").toString()};
        Assertions.assertEquals("""
                2 Hello, World
                2 hello, world
                0 Hello, Worlds
                2 Hi, Yooondooo""", ForTest.testOut(args));
    }

    @Test
    public void test5() {
        String[] args = {"-o", "output.txt", "-c", "-i", Paths.get("input", "test1.txt").toString()};
        Assertions.assertEquals("""
                4 hello, world
                0 Hello, Worlds
                2 Hi, Yooondooo""", ForTest.testOut(args));
    }

    @Test
    public void test6() {
        String[] args = {"-o", "output.txt", "-u", Paths.get("input", "test1.txt").toString()};
        Assertions.assertEquals("Hello, Worlds", ForTest.testOut(args));
    }
}
