/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package softdev.spring.task;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test
    public void testStartEndChars() {
        Cut cut = new Cut();
        String line = "Hello, world!";
        String result = cut.startEnd(line, 10,15, true);
        assertEquals("ld!", result);
    }
    @Test
    public void testStartEndWord() {
        Cut cut = new Cut();
        String line = "Hello, world! It is me";
        String result = cut.startEnd(line, 1, 3, false);
        assertEquals("world! It", result);
    }
    @Test
    public void testRunInvalidOptions() {
        CutLauncher options = new CutLauncher();
        options.ch = true;
        options.word = true;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setErr(new PrintStream(out));
        options.run(true, true,options.inputFileName, options.outputFileName, options.range);
        String output = out.toString().trim();
        assertEquals("There must be exactly one option", output);
    }
    @Test
    public void testRunWithFiles() throws IOException {
        CutLauncher options = new CutLauncher();
        options.ch = true;
        options.range = "0-5";
        File inputFile = File.createTempFile("test", ".txt");
        File outputFile = File.createTempFile("test", ".txt");
        PrintWriter writer = new PrintWriter(new FileWriter(inputFile));
        writer.println("Hello, world!");
        writer.close();
        options.inputFileName = inputFile.getAbsolutePath();
        options.outputFileName = outputFile.getAbsolutePath();
        options.run(true, options.word,options.inputFileName, options.outputFileName, options.range);
        BufferedReader reader = new BufferedReader(new FileReader(outputFile));
        String result = reader.readLine();
        reader.close();
        assertEquals("Hello,", result);
    }

    @Test
    public void finalTestChar() throws IOException {
        String[] args = {"-c", "-o", "files/output.txt", "files/input.txt", "4-8"};
        CutLauncher options = new CutLauncher();
        options.launch(args);
        options.run(options.ch, options.word, options.inputFileName, options.outputFileName, options.range);
        BufferedReader reader = new BufferedReader(new FileReader(options.outputFileName));
        String result = reader.readLine();
        assertEquals(" of s", result);
        assertEquals("you s", reader.readLine());
        assertEquals(" of s", reader.readLine());
        assertEquals("e's s", reader.readLine());
        assertEquals("knows", reader.readLine());
        assertEquals("his t", reader.readLine());
        assertEquals("ne mo", reader.readLine());
        reader.close();
    }
    @Test
    public void finalTestWord() throws IOException {
        String[] args = {"-w", "-o", "files/output.txt", "files/input.txt", "2-6"};
        CutLauncher options = new CutLauncher();
        options.launch(args);
        options.run(options.ch, options.word, options.inputFileName, options.outputFileName, options.range);
        BufferedReader reader = new BufferedReader(new FileReader(options.outputFileName));
        String result = reader.readLine();
        assertEquals("stars", result);
        assertEquals("shining just for me?", reader.readLine());
        assertEquals("stars", reader.readLine());
        assertEquals("much that I can't", reader.readLine());
        assertEquals("", reader.readLine());
        assertEquals("the start of something", reader.readLine());
        assertEquals("more dream that I", reader.readLine());
        reader.close();
    }
}
