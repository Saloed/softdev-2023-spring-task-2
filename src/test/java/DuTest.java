import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class DuTest {

    @Test
    public void test1(){
        List<String> fileList = Arrays.asList(Paths.get("Inputs", "File1.jpg").toString(),
                Paths.get("Inputs", "File2.jpg").toString(),
                Paths.get("Inputs", "File3.jpg").toString());
        Functions functions = new Functions(new Explorer(fileList).makingLongArrayList(), true, true, true);
        Assertions.assertEquals("322.38KB",functions.outForTest().trim());
    }

    @Test
    public void test2(){
        List<String> fileList = Arrays.asList(Paths.get("Inputs", "File1.jpg").toString(),
                Paths.get("Inputs", "File2.jpg").toString(),
                Paths.get("Inputs", "File3.jpg").toString(),
                Paths.get("Inputs", "File4").toString());
        Functions functions = new Functions(new Explorer(fileList).makingLongArrayList(), true, false, true);
        Assertions.assertEquals("""
                170.06KB
                16.00KB
                136.32KB
                12.00B""",functions.outForTest().trim());
    }

    @Test
    public void test3(){
        List<String> fileList = Arrays.asList(Paths.get("Inputs", "File2.jpg").toString(),
                Paths.get("Inputs", "File1.jpg").toString(),
                Paths.get("Inputs", "File3.jpg").toString());
        Functions functions = new Functions(new Explorer(fileList).makingLongArrayList(), true, false, true);
        Assertions.assertEquals("""
                16.00KB
                170.06KB
                136.32KB""",functions.outForTest().trim());
    }

    @Test
    public void test4(){
        List<String> fileList = Arrays.asList(Paths.get("Inputs", "File2.jpg").toString(),
                Paths.get("Inputs", "File1.jpg").toString());
        Functions functions = new Functions(new Explorer(fileList).makingLongArrayList(), true, false, true);
        Assertions.assertEquals("16.00KB\n170.06KB",functions.outForTest().replace(',','.').trim());
    }

    @Test
    public void test5(){
        List<String> fileList = Arrays.asList(Paths.get("Inputs", "File2.jpg").toString(),
                Paths.get("Inputs", "File1.jpg").toString());
        Functions functions = new Functions(new Explorer(fileList).makingLongArrayList(), true, true, true);
        Assertions.assertEquals("186.06KB",functions.outForTest().trim());
    }

    @Test
    public void test6(){
        List<String> fileList = Arrays.asList(Paths.get("Inputs", "File1.jpg").toString(),
                Paths.get("Inputs", "File2.jpg").toString(),
                Paths.get("Inputs", "File3.jpg").toString());
        Functions functions = new Functions(new Explorer(fileList).makingLongArrayList(), true, true, false);
        Assertions.assertEquals("314.83KB",functions.outForTest().trim());
    }
}