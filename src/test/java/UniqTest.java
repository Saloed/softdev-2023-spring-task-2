import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.FileInputStream;

public class UniqTest {
    @Test
    public void test(){
           String[] args = {"-o","output.txt","input\\test1.txt"};
           Uniq.main(args);
    }
    @Test
    public void test2(){
        String[] args = {"input\\test1.txt"};
        Uniq.main(args);
    }
    @Test
    public void test3(){
        String[] args = {"-i","input\\test1.txt"};
        Uniq.main(args);
    }
    @Test
    public void test4(){
        String[] args = {"-c","input\\test1.txt"};
        Uniq.main(args);
    }
    @Test
    public void test5(){
        String[] args = {"-c","-i","input\\test1.txt"};
        Uniq.main(args);
    }
    @Test
    public void test6(){
        String[] args = {"-u","input\\test1.txt"};
        Uniq.main(args);
    }
}
