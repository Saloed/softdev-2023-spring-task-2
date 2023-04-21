package org.example;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tail {
    @Option(name = "-c", usage = "number of last symbols")
    public static String symbols;
    @Option(name = "-n", usage = "number of last lines")
    public static String lines;
    @Argument(required = true, usage = "list of input files")
    public static List<File> inputFiles;
    @Option(name = "-o", usage = "name of output file")
    public static File outputFile;

    public void start(String[] args) {
        CmdLineParser clp = new CmdLineParser(this);
        try {
            clp.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
        }
        //"-c" and "-n" used together
        if (symbols != null && lines != null) System.err.println("You cannot use '-c' and '-n' at the same time!");
        //there are no any input files
        else if (inputFiles.size() == 0) {
            try {
                File enteredText = new File("ifThereIsNoInputFile");
                BufferedWriter bufWriterForNewFile = new BufferedWriter(new FileWriter(enteredText));
                System.out.println("Enter text...");
                Scanner scan = new Scanner(System.in);
                bufWriterForNewFile.write(scan.next());
                inputFiles.add(enteredText);
                bufWriterForNewFile.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        last(inputFiles, symbols, lines, outputFile);
    }

    public void main(String[] args) {
        new Tail().start(args);
    }

    public File last(List<File> inputFiles, String symbols, String lines, File outputFile) {
        boolean isIndentNeeded = false;
        try {
            BufferedReader bufReader;
            File newOutputFile = new File("newOutputFile");
            if (outputFile != null) newOutputFile = outputFile;
            else System.out.println("Here is response for your request...");
            BufferedWriter bufWriter = new BufferedWriter(new FileWriter(newOutputFile));
            //last lines
            if (symbols == null) {
                String line;
                for (File file : inputFiles) {
                    bufReader = new BufferedReader(new FileReader(file));
                    if (isIndentNeeded) bufWriter.write("\n");
                    if (inputFiles.size() > 1) bufWriter.write("File name: " + file.getName() + "\n");
                    List<String> listOfLines = new ArrayList<>();
                    while ((line = bufReader.readLine()) != null) {
                        listOfLines.add(line);
                    }
                    int count;
                    //when there are no "-c" and "-n"
                    if (lines == null) count = 10;
                    else count = Integer.parseInt(lines);
                    if (count > listOfLines.size()) count = listOfLines.size();
                    List<String> subList;
                    subList = listOfLines.subList(listOfLines.size() - count, listOfLines.size());
                    int countOfIndents = 1;
                    for (String anotherLine : subList) {
                        bufWriter.write(anotherLine);
                        if (countOfIndents != subList.size()) bufWriter.write("\n");
                        countOfIndents++;
                    }
                    isIndentNeeded = true;
                }
            }
            //last symbols
            if (symbols != null) {
                int symbol;
                for (File file : inputFiles) {
                    bufReader = new BufferedReader(new FileReader(file));
                    if (isIndentNeeded) bufWriter.write("\n");
                    if (inputFiles.size() > 1) bufWriter.write("File name: " + file.getName() + "\n");
                    int count = Integer.parseInt(symbols);
                    List<String> listOfSymbols = new ArrayList<>();
                    while ((symbol = bufReader.read()) != -1) {
                        listOfSymbols.add(String.valueOf((char) symbol));
                    }
                    if (count > listOfSymbols.size()) count = listOfSymbols.size();
                    List<String> subList;
                    subList = listOfSymbols.subList(listOfSymbols.size() - count, listOfSymbols.size());
                    for (String anotherSymbol : subList) {
                        bufWriter.write(anotherSymbol);
                    }
                    isIndentNeeded = true;
                }
            }
            bufWriter.close();
            if (outputFile == null) {
                bufReader = new BufferedReader(new FileReader("newOutputFile"));
                String line;
                while ((line = bufReader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return outputFile;
    }
}