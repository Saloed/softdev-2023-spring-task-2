package task2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.TreeSet;

public class FindTests {

    private final String curDir = System.getProperty("user.dir");
    String sep = File.separator;

    @Test
    public void test1() throws IOException {
        String[] input = {"-r", "-d", curDir + sep + "src" + sep + "test" + sep + "resources", "test"};
        TreeSet<String> res = new TreeSet<>();
        res.add(curDir + sep + "src" + sep +"test" + sep + "resources" + sep + "subdir" + sep + "test15.txt");
        res.add(curDir + sep + "src" + sep + "test" + sep + "resources" + sep + "test1.txt");
        res.add(curDir + sep + "src" + sep + "test" + sep + "resources" + sep + "test2.txt");
        res.add(curDir + sep + "src" + sep + "test" + sep + "resources" + sep + "test20.txt");
        Assertions.assertEquals(new Parser().parse(input), res);
    }

    @Test
    public void test2() throws IOException {
        String[] input = {"-d", curDir + "" + sep + "src" + sep + "test" + sep + "resources", "test2"};
        TreeSet<String> res = new TreeSet<>();
        res.add(curDir + sep + "src" + sep + "test" + sep + "resources" + sep + "test2.txt");
        res.add(curDir + sep + "src" + sep + "test" + sep + "resources" + sep + "test20.txt");
        Assertions.assertEquals(new Parser().parse(input), res);
    }

    @Test
    public void test3() throws IOException {
        String[] input = {"-r", "test2"};
        TreeSet<String> res = new TreeSet<>();
        res.add(curDir + sep + "build" + sep + "resources" + sep + "test" + sep + "test2.txt");
        res.add(curDir + sep + "build" + sep + "resources" + sep + "test" + sep + "test20.txt");
        res.add(curDir + sep + "src" + sep + "test" + sep + "resources" + sep + "test2.txt");
        res.add(curDir + sep + "src" + sep + "test" + sep + "resources" + sep + "test20.txt");
        Assertions.assertEquals(new Parser().parse(input), res);
    }

    @Test
    public void test4() throws IOException {
        String[] input = {"someFileName"};
        TreeSet<String> res = new TreeSet<>();
        Assertions.assertEquals(new Parser().parse(input), res);
    }

    @Test
    public void test5() throws IOException {
        String[] input = {"build.gradle"};
        TreeSet<String> res = new TreeSet<>();
        res.add(curDir + "" + sep + "build.gradle.kts");
        Assertions.assertEquals(new Parser().parse(input), res);
    }

    @Test
    public void test6() throws IOException {
        String[] input = {"-r", "a.b.c.txt"};
        TreeSet<String> res = new TreeSet<>();
        Assertions.assertEquals(new Parser().parse(input), res);
    }

    @Test
    public void test7() throws IOException {
        String[] input = {"-r", "axbxc.txt"};
        TreeSet<String> res = new TreeSet<>();
        Assertions.assertEquals(new Parser().parse(input), res);
    }

}
