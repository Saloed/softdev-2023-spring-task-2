package org.example;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
    ArrayList<String> inputText = new ArrayList<>();

    public static void main(String[] args) throws CmdLineException, IOException {
        final Main instance = new Main();
        instance.doMain(args);
    }

    private void doMain(String[] args) throws CmdLineException, IOException {
        final CmdLineParser parser = new CmdLineParser(this);
        parser.parseArgument(args);
        ArrayList<ArrayList<String>> answer;
        ArrayList<String> stringsText = new ArrayList<>();
        if (wordIndent == charIndent) throw new IllegalArgumentException("Ошибка во флагах -c/-w");
        if ((inputText != null) && (inputText.size() == 1)) {
            stringsText = getText(inputText.get(0));
        } else {
            StringBuilder inputTxt = new StringBuilder();
            assert inputText != null;
            inputText.forEach(s -> inputTxt.append(s).append(" "));
            stringsText.add(String.valueOf(inputTxt));
        }
        if (wordIndent) answer = wordIndention(stringsText);
        else answer = charIndention(stringsText);
        if (outputName != null) writeFileOutput(answer, outputName);
        else writeOutput(answer);
    }

    private ArrayList<Integer> countRange(String startData) {
        ArrayList<Integer> answer = new ArrayList<>();
        String[] textAnswer = startData.split("-");
        answer.add(Integer.parseInt(textAnswer[0]));
        answer.add(Integer.parseInt(textAnswer[1]));
        if (answer.get(0) < answer.get(1) || answer.get(0) < 0)
            throw new IllegalArgumentException("Неправильно указан range");
        return answer;
    }

    private ArrayList<String> getText(String inputName) throws FileNotFoundException {
        File inputFile = new File(inputName);
        ArrayList<String> stringsText = new ArrayList<>();
        Scanner scanner = new Scanner(inputFile);
        while (scanner.hasNextLine()) {
            stringsText.add(scanner.nextLine());
        }
        scanner.close();
        return stringsText;
    }

    private void writeFileOutput(ArrayList<ArrayList<String>> answer, String outputName) throws FileNotFoundException {
        File outputFile = new File(outputName);
        PrintWriter output = new PrintWriter(outputFile);
        for (ArrayList<String> strings : answer) {
            output.println(strings);
        }
        output.close();
    }

    private void writeOutput(ArrayList<ArrayList<String>> answer) {
        for (ArrayList<String> currentString : answer) {
            for (String s : currentString) {
                System.out.print(s + " ");
            }
            System.out.println("");
        }
    }
    private ArrayList<ArrayList<String>> wordIndention(ArrayList<String> stringsText){
        ArrayList<Integer> intRange = countRange(range);
        ArrayList<ArrayList<String>> answer = new ArrayList<>();
        for (String value : stringsText) {
            String[] currentString = value.split(" ");
            ArrayList<String> needSymbols = new ArrayList<>();
            for (int numberSymbol = 0; numberSymbol < currentString.length; numberSymbol++) {
                if ((numberSymbol >= intRange.get(0)) &&
                        (numberSymbol <= intRange.get(1))) needSymbols.add(currentString[numberSymbol]);
            }
            answer.add(needSymbols);
        }
        return answer;
    }
    private ArrayList<ArrayList<String>> charIndention(ArrayList<String> stringsText){
        ArrayList<Integer> intRange = countRange(range);
        ArrayList<ArrayList<String>> answer = new ArrayList<>();
        for (String value : stringsText) {
            ArrayList<String> needSymbols = new ArrayList<>();
            needSymbols.add(value.substring(intRange.get(0),intRange.get(1)));
            answer.add(needSymbols);
        }
        return answer;
    }
}
