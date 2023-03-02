package com.tests;

import com.utility.Coder;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class AppTest {

    @Test
    public void testingEncoding() {
        try {
            Coder.encode("test.txt", "encodedFile.txt");
            Coder.decode("encodedFile.txt", "testToCheck.txt");
            try (FileInputStream is1 = new FileInputStream("test.txt");
                 FileInputStream is2 = new FileInputStream("testToCheck.txt")) {
                while (true) {
                    int f = is1.read();
                    int s = is2.read();
                    if (f != s) Assert.fail();
                    if (f == -1 && s == -1) break;
                }
            }
            Assert.assertTrue(true);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
