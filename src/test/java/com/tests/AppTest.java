package com.tests;

import com.utility.Coder;
import com.utility.FileDecodingException;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AppTest {
    @Test
    public void testingDecoding() {
        List<String> strs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("decodingTests.txt"))) {
            while (true) {
                String st = br.readLine();
                if (st == null) break;
                strs.add(st);
            }
        } catch (IOException e) {
            Assert.fail();
        }

        for (int i = 0; i < strs.size() / 2; i++) {
            try {
                // Input file
                String nameIn = "." + File.separator + "inputTestFile.txt";
                File inputFile = new File(nameIn);
                inputFile.createNewFile();
                // Output file
                String nameOut = "." + File.separator + "outputTestFile.txt";
                File outputFile = new File(nameOut);
                outputFile.createNewFile();
                // Write to input, decode, read from output
                BufferedWriter bw = new BufferedWriter(new FileWriter(nameIn));
                bw.write(strs.get(i));
                bw.close();
                Coder.decode(nameIn, nameOut);
                BufferedReader br = new BufferedReader(new FileReader(nameOut));
                String result = br.readLine();
                br.close();
                if (result == null && strs.get(strs.size() - 1 - i).isEmpty()) Assert.assertTrue(true);
                else if (!result.equals(strs.get(strs.size() - 1 - i))) Assert.fail();
                // Delete input and output files
                inputFile.delete();
                outputFile.delete();
            } catch (IOException e) {
                Assert.fail();
            }
        }
        Assert.assertTrue(true);
    }

    @Test
    public void testingEncoding() {
        List<String> strs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("encodingTests.txt"))) {
            while (true) {
                String st = br.readLine();
                if (st == null) break;
                strs.add(st);
            }
        } catch (IOException e) {
            Assert.fail();
        }

        for (int i = 0; i < strs.size() / 2; i++) {
            try {
                // Input file
                String nameIn = "." + File.separator + "inputTestFile.txt";
                File inputFile = new File(nameIn);
                inputFile.createNewFile();
                // Output file
                String nameOut = "." + File.separator + "outputTestFile.txt";
                File outputFile = new File(nameOut);
                outputFile.createNewFile();
                // Write to input, decode, read from output
                BufferedWriter bw = new BufferedWriter(new FileWriter(nameIn));
                bw.write(strs.get(i));
                bw.close();
                Coder.encode(nameIn, nameOut);
                BufferedReader br = new BufferedReader(new FileReader(nameOut));
                String result = br.readLine();
                br.close();
                if (result == null && strs.get(strs.size() - 1 - i).isEmpty()) Assert.assertTrue(true);
                else if (!result.equals(strs.get(strs.size() - 1 - i))) Assert.fail();
                // Delete input and output files
                inputFile.delete();
                outputFile.delete();
            } catch (IOException e) {
                Assert.fail();
            }
        }
        Assert.assertTrue(true);
    }
}
