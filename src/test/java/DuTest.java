import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class DuTest {

    @Test
    public void test1(){
        List<String> fileList = Arrays.asList(Paths.get("Inputs", "File1").toString(),
                Paths.get("Inputs", "File2").toString(),
                Paths.get("Inputs", "File3").toString());
        Functions functions = new Functions(new Explorer(fileList).makingLongArrayList(), true, true, true);
        Assertions.assertEquals("208.74KB",functions.outForTest().trim());
    }

    @Test
    public void test2(){
        List<String> fileList = Arrays.asList(Paths.get("Inputs", "File1").toString(),
                Paths.get("Inputs", "File2").toString(),
                Paths.get("Inputs", "File3").toString());
        Functions functions = new Functions(new Explorer(fileList).makingLongArrayList(), true, false, true);
        Assertions.assertEquals("""
                12.00B
                124.36KB
                84.37KB""",functions.outForTest().trim());
    }

    @Test
    public void test3(){
        List<String> fileList = Arrays.asList(Paths.get("Inputs", "File2").toString(),
                Paths.get("Inputs", "File1").toString(),
                Paths.get("Inputs", "File3").toString());
        Functions functions = new Functions(new Explorer(fileList).makingLongArrayList(), true, false, true);
        Assertions.assertEquals("""
                124.36KB
                12.00B
                84.37KB""",functions.outForTest().trim());
    }

    @Test
    public void test4(){
        List<String> fileList = Arrays.asList(Paths.get("Inputs", "File2").toString(),
                Paths.get("Inputs", "File1").toString());
        Functions functions = new Functions(new Explorer(fileList).makingLongArrayList(), true, false, true);
        Assertions.assertEquals("124.36KB\n12.00B",functions.outForTest().replace(',','.').trim());
    }

    @Test
    public void test5(){
        List<String> fileList = Arrays.asList(Paths.get("Inputs", "File2").toString(),
                Paths.get("Inputs", "File1").toString());
        Functions functions = new Functions(new Explorer(fileList).makingLongArrayList(), true, true, true);
        Assertions.assertEquals("124.37KB",functions.outForTest().trim());
    }

    @Test
    public void test6(){
        List<String> fileList = Arrays.asList(Paths.get("Inputs", "File1").toString(),
                Paths.get("Inputs", "File2").toString(),
                Paths.get("Inputs", "File3").toString());
        Functions functions = new Functions(new Explorer(fileList).makingLongArrayList(), true, true, false);
        Assertions.assertEquals(functions.sumFileSizesForTest(),functions.outForTest().trim());
    }
}