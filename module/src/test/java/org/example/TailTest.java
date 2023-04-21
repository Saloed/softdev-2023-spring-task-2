package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TailTest {
    @Test
    public void testTail() throws IOException {
        String symbols = "10";
        String lines = null;
        List<File> list = new ArrayList<>();
        //по-другому файлы не видны :(
        list.add(new File("Files/input1"));
        list.add(new File("Files/input2"));
        list.add(new File("Files/input3"));
        File outputFile = new File("Files/output");
        List<Object> list1 = read(new File("Files/check1"));
        assertEquals(list1, read(new File(new Tail().last(list, symbols, lines, outputFile).toURI())));
    }
    @Test
    public void testTail2() throws IOException {
        String symbols = null;
        String lines = "3";
        List<File> list = new ArrayList<>();
        list.add(new File("Files/input1"));
        list.add(new File("Files/input2"));
        list.add(new File("Files/input3"));
        File outputFile = new File("Files/output");
        List<Object> list1 = read(new File("Files/check2"));
        assertEquals(list1, read(new File(new Tail().last(list, symbols, lines, outputFile).toURI())));
    }
    @Test
    public void testTail3() throws IOException {
        String symbols = null;
        String lines = null;
        List<File> list = new ArrayList<>();
        list.add(new File("Files/input1"));
        list.add(new File("Files/input2"));
        list.add(new File("Files/input3"));
        File outputFile = new File("Files/output");
        List<Object> list1 = read(new File("Files/check3"));
        assertEquals(list1, read(new File(new Tail().last(list, symbols, lines, outputFile).toURI())));
    }
    @Test
    public void testTail4() throws IOException {
        String symbols = null;
        String lines = "2";
        List<File> list = new ArrayList<>();
        list.add(new File("Files/input3"));
        File outputFile = new File("Files/output");
        List<Object> list1 = read(new File("Files/check4"));
        assertEquals(list1, read(new File(new Tail().last(list, symbols, lines, outputFile).toURI())));
    }
    public List<Object> read(File file) throws IOException {
        String line;
        BufferedReader bufReader = new BufferedReader(new FileReader(file));
        List<Object> listOfLines = new ArrayList<>();
        while ((line = bufReader.readLine()) != null) {
            listOfLines.add(line);
        }
        return listOfLines;
    }
}
