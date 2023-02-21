package com.tar;
//import junit.framework.Test;
//import junit.framework.TestCase;
//import junit.framework.TestSuite;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
//import org.apache.commons.io

/**
 * Unit test for simple App.
 */
public class AppTest {
    @Test
    public void testApp() {
        assertTrue(true);
    }

    @Test
    public void testPacking() throws IOException {
        // Удалить temp файлы если они есть
        String[] args = {"-u", "testResources/in/out.e"};
        App.main(args);
        assertTrue(FileUtils.contentEquals(new File("testResources/reference/maven.zip"), new File("maven.zip")));
        File f = new File("maven.zip");
        f.delete();


    }

    @Test
    public void fullCycle() throws IOException {
        App.main(new String[]{"testResources\\in\\test1.txt", "-out", "out.e"});
        App.main(new String[]{"-u", "out.e"});

        assertTrue(FileUtils.contentEquals(new File("testResources/in/test1.txt"), new File("test1.txt")));

        new File("test1.txt").delete();
        new File("out.e").delete();
    }
}
