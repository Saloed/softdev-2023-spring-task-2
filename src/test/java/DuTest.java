import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class DuTest {
    @Test
    public void test1() {
        String[] args = {"-si","-c","-h","Inputs\\File1", "Inputs\\File2", "Inputs\\File3"};
        Du.main(args);
        Assertions.assertEquals("208,74KB",Du.answer.substring(0, Du.answer.length()-1));
    }

    @Test
    public void test2() {
        String[] args = {"-si","-h","Inputs\\File1", "Inputs\\File2", "Inputs\\File3"};
        Du.main(args);
        Assertions.assertEquals("""
                12,00B
                124,36KB
                84,37KB""",Du.answer.substring(0, Du.answer.length()-1));
    }

    @Test
    public void test3() {
        String[] args = {"-si","-h","Inputs\\File2", "Inputs\\File1", "Inputs\\File3"};
        Du.main(args);
        Assertions.assertEquals("""
                124,36KB
                12,00B
                84,37KB""",Du.answer.substring(0, Du.answer.length()-1));
    }

    @Test
    public void test4() {
        String[] args = {"-h","-si","Inputs\\File2", "Inputs\\File1"};
        Du.main(args);
        Assertions.assertEquals("""
                124,36KB
                12,00B""",Du.answer.substring(0, Du.answer.length()-1));
    }

    @Test
    public void test5() {
        String[] args = {"-si","-h","-c","Inputs\\File2", "Inputs\\File1"};
        Du.main(args);
        Assertions.assertEquals("124,37KB",Du.answer.substring(0, Du.answer.length()-1));
    }

    @Test
    public void test6() {
        String[] args = {"-c","-h","Inputs\\File1", "Inputs\\File2", "Inputs\\File3"};
        Du.main(args);
        Assertions.assertEquals("203,85KB",Du.answer.substring(0, Du.answer.length()-1));
    }
}
