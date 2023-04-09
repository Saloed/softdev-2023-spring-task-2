package org.example;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
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
    List<String> inputText = new ArrayList<>();

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
        if (inputText.contains("input/input.txt")) {
            stringsText = getText(inputText.get(0));
        } else {
            StringBuilder inputTxt = new StringBuilder();
            inputText.forEach(s -> inputTxt.append(s).append(" "));
            stringsText.add(String.valueOf(inputTxt));
        }
        if (wordIndent) answer = wordIndention(stringsText);
        else answer = charIndention(stringsText);
        if (outputName != null) writeFileOutput(answer, outputName);
        else writeOutput(answer);
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

    private void writeFileOutput(List<List<String>> answer, String outputName) throws FileNotFoundException {
        File outputFile = new File(outputName);
        PrintWriter output = new PrintWriter(outputFile);
        for (List<String> strings : answer) {
            output.println(strings);
        }
        output.close();
    }

    private void writeOutput(List<List<String>> answer) {
        for (List<String> currentString : answer) {
            for (String s : currentString) {
                System.out.print(s + " ");
            }
            System.out.println();
        }
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
}
