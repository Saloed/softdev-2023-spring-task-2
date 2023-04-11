package org.example;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    @Option(name = "-r", required = true)
    String range;
    @Option(name = "-o")
    String outputName;
    @Option(name = "-w")
    boolean wordIndent = false;
    @Option(name = "-c")
    boolean charIndent = false;
    @Argument()
    String inputText = "";

    public static void main(String[] args) throws CmdLineException, IOException {
        final Main instance = new Main();
        instance.doMain(args);
    }

    private void doMain(String[] args) throws CmdLineException, IOException {
        final CmdLineParser parser = new CmdLineParser(this);
        parser.parseArgument(args);
        List<List<String>> answer;
        List<String> stringsText = new ArrayList<>();
        if (wordIndent == charIndent) throw new IllegalArgumentException("Ошибка во флагах -c/-w");
        if (new File(inputText).exists()) {
            stringsText = getText(inputText);
        } else {
            stringsText.add(inputText + " ");
        }
        if (wordIndent) answer = wordIndention(stringsText);
        else answer = charIndention(stringsText);
        if (outputName != null) writeOutput(outputName, answer.toString());
        else writeOutput(System.out.toString(), answer.toString());
    }

    private List<Integer> countRange(String startData) {
        List<Integer> answer = new ArrayList<>();
        String[] textAnswer = startData.split("-");
        answer.add(Integer.parseInt(textAnswer[0]));
        answer.add(Integer.parseInt(textAnswer[1]));
        if (answer.get(0) > answer.get(1) || answer.get(0) < 0)
            throw new IllegalArgumentException("Неправильно указан range");
        return answer;
    }

    private List<String> getText(String inputName) throws FileNotFoundException {
        File inputFile = new File(inputName);
        List<String> stringsText = new ArrayList<>();
        Scanner scanner = new Scanner(inputFile);
        while (scanner.hasNextLine()) {
            stringsText.add(scanner.nextLine());
        }
        scanner.close();
        return stringsText;
    }

    private List<List<String>> wordIndention(List<String> stringsText){
        List<Integer> intRange = countRange(range);
        List<List<String>> answer = new ArrayList<>();
        for (String value : stringsText) {
            String[] currentString = value.split(" ");
            List<String> needSymbols = new ArrayList<>();
            for (int numberSymbol = 0; numberSymbol < currentString.length; numberSymbol++) {
                if ((numberSymbol >= intRange.get(0)) &&
                        (numberSymbol <= intRange.get(1))) needSymbols.add(currentString[numberSymbol]);
            }
            answer.add(needSymbols);
        }
        return answer;
    }
    private List<List<String>> charIndention(List<String> stringsText){
        List<Integer> intRange = countRange(range);
        List<List<String>> answer = new ArrayList<>();
        for (String value : stringsText) {
            List<String> needSymbols;
            needSymbols=List.of((value.substring(intRange.get(0),intRange.get(1))).split(" "));
            answer.add(needSymbols);
        }
        return answer;
    }
    private void writeOutput(String place, String data) throws IOException {
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        if (new File(place).isFile()){
            OutputStream out = new FileOutputStream(place);
            out.write(bytes);
        } else {
            OutputStream out = System.out;
            out.write(bytes);
        }
    }
}