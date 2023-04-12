package task2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FindTests {

    private final String curDir = System.getProperty("user.dir");
    String sep = File.separator;

    @Test
    public void test1() throws IOException {
        String[] input = {"-r", "-d", curDir + sep + "src" + sep + "test" + sep + "resources", "test"};
        ArrayList<String> res = new ArrayList<>();
        res.add(curDir + sep + "src" + sep +"test" + sep + "resources" + sep + "subdir" + sep + "test15.txt");
        res.add(curDir + sep + "src" + sep + "test" + sep + "resources" + sep + "test1.txt");
        res.add(curDir + sep + "src" + sep + "test" + sep + "resources" + sep + "test2.txt");
        res.add(curDir + sep + "src" + sep + "test" + sep + "resources" + sep + "test20.txt");
        System.err.println(new Parser().parse(input));
        Assertions.assertEquals(new Parser().parse(input), res);
    }

    @Test
    public void test2() throws IOException {
        String[] input = {"-d", curDir + "" + sep + "src" + sep + "test" + sep + "resources", "test2"};
        ArrayList<String> res = new ArrayList<>();
        res.add(curDir + sep + "src" + sep + "test" + sep + "resources" + sep + "test2.txt");
        res.add(curDir + sep + "src" + sep + "test" + sep + "resources" + sep + "test20.txt");
        System.err.println(new Parser().parse(input));
        Assertions.assertEquals(new Parser().parse(input), res);
    }

    @Test
    public void test3() throws IOException {
        String[] input = {"-r", "test2"};
        ArrayList<String> res = new ArrayList<>();
        res.add(curDir + sep + "build" + sep + "resources" + sep + "test" + sep + "test2.txt");
        res.add(curDir + sep + "build" + sep + "resources" + sep + "test" + sep + "test20.txt");
        res.add(curDir + sep + "src" + sep + "test" + sep + "resources" + sep + "test2.txt");
        res.add(curDir + sep + "src" + sep + "test" + sep + "resources" + sep + "test20.txt");
        System.err.println(new Parser().parse(input));
        Assertions.assertEquals(new Parser().parse(input), res);
    }

    @Test
    public void test4() throws IOException {
        String[] input = {"someFileName"};
        ArrayList<String> res = new ArrayList<>();
        System.err.println(new Parser().parse(input));
        Assertions.assertEquals(new Parser().parse(input), res);
    }

    @Test
    public void test5() throws IOException {
        String[] input = {"build.gradle"};
        ArrayList<String> res = new ArrayList<>();
        res.add(curDir + "" + sep + "build.gradle.kts");
        System.err.println(new Parser().parse(input));
        Assertions.assertEquals(new Parser().parse(input), res);
    }

}
