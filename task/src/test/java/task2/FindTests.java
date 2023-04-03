package task2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

public class FindTests {

    private final String curDir = System.getProperty("user.dir");

    @Test
    public void test1() throws IOException {
        String[] input = {"-r", "-d", curDir + "\\src\\test\\resources", "test"};
        ArrayList<String> res = new ArrayList<>();
        res.add(curDir + "\\src\\test\\resources\\subdir\\test15.txt");
        res.add(curDir + "\\src\\test\\resources\\test1.txt");
        res.add(curDir + "\\src\\test\\resources\\test2.txt");
        res.add(curDir + "\\src\\test\\resources\\test20.txt");
        Assertions.assertEquals(new Parser().parse(input), res);
    }

    @Test
    public void test2() throws IOException {
        String[] input = {"-d", curDir + "\\src\\test\\resources", "test2"};
        ArrayList<String> res = new ArrayList<>();
        res.add(curDir + "\\src\\test\\resources\\test2.txt");
        res.add(curDir + "\\src\\test\\resources\\test20.txt");
        Assertions.assertEquals(new Parser().parse(input), res);
    }

    @Test
    public void test3() throws IOException {
        String[] input = {"-r", "test2"};
        ArrayList<String> res = new ArrayList<>();
        res.add(curDir + "\\build\\resources\\test\\test2.txt");
        res.add(curDir + "\\build\\resources\\test\\test20.txt");
        res.add(curDir + "\\src\\test\\resources\\test2.txt");
        res.add(curDir + "\\src\\test\\resources\\test20.txt");
        Assertions.assertEquals(new Parser().parse(input), res);
    }

    @Test
    public void test4() throws IOException {
        String[] input = {"someFileName"};
        ArrayList<String> res = new ArrayList<>();
        Assertions.assertEquals(new Parser().parse(input), res);
    }

}
