import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GrepTest {
    @Test
    public void grepTest1() {
        List<String> args = List.of("worde", "inputname.txt");
        Assertions.assertEquals("""
                ejhygtfrdeworde""", GrepResult.Res4Test(args));

    }
    @Test
    public void grepTest2() {
        List<String> args = List.of("-i", "-v", "worde", "inputname.txt");
        Assertions.assertEquals("""
                huh
                ghyjk
                Guhuokkl""", GrepResult.Res4Test(args));
    }

    @Test
    public void grepTest3() {
        List<String> args = List.of("-r", "([A-Z])([a-z])+", "inputname.txt");
        Assertions.assertEquals("""
                Wordefgvhbjn
                Guhuokkl""", GrepResult.Res4Test(args));
    }

    @Test
    public void grepTest4() {
        List<String> args = List.of("-i", "-r", "([A-Z])([a-z])+", "inputname.txt");
        Assertions.assertEquals("""
                huh
                ghyjk
                Wordefgvhbjn
                Guhuokkl
                ejhygtfrdeworde""", GrepResult.Res4Test(args));
    }

}
