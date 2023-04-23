package softdev.spring.task;

import org.apache.commons.cli.*;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class utilizer {

    private static final Option AMO_SYM = new Option("c", true, "input number of needed symbols");
    private static final Option AMO_LIN = new Option("n", true, "input number of needed lines");
    private static final Option NAM_OFI = new Option("o", true, "input name of output file");

    public static void mainParce(String[] args) {
        CommandLineParser parcer = new DefaultParser();

        Options options = new Options();
        options.addOption(AMO_SYM);
        options.addOption(AMO_LIN);
        options.addOption(NAM_OFI);
        try {
            CommandLine cl = parcer.parse(options, args);
            ArrayList<String> filenames = new ArrayList(cl.getArgList());
            if (!cl.hasOption(AMO_LIN) && !cl.hasOption(AMO_SYM)) {
                if (cl.hasOption(NAM_OFI)) {
                    ArrayList<String> outputList = new ArrayList<>();
                    for (String file:filenames) {
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        while ((reader.readLine()) != null) {
                            outputList.add(reader.readLine());
                        }
                        BufferedWriter writer = new BufferedWriter(new FileWriter(cl.getOptionValue("o")));
                        List<String> writeList = tail.getEndStrings(outputList, 10);
                        for (String lineOfFile: writeList) {
                            writer.write(lineOfFile);
                        }
                    }
                }
                else {
                    ArrayList<String> outputList = new ArrayList<>();
                    for (String file:filenames) {
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        while ((reader.readLine()) != null) {
                            outputList.add(reader.readLine());
                        }
                        System.out.println(tail.getEndStrings(outputList, 10));
                    }
                }
            }
            else if (cl.hasOption(AMO_LIN)) {
                if (cl.hasOption(NAM_OFI)) {
                    ArrayList<String> outputList = new ArrayList<>();
                    for (String file:filenames) {
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        while ((reader.readLine()) != null) {
                            outputList.add(reader.readLine());
                        }
                        BufferedWriter writer = new BufferedWriter(new FileWriter(cl.getOptionValue("o")));
                        List<String> writeList = tail.getEndStrings(outputList,
                                Integer.parseInt(cl.getOptionValue("n")));
                        for (String lineOfFile: writeList) {
                            writer.write(lineOfFile);
                        }
                    }
                }
                else {
                    ArrayList<String> outputList = new ArrayList<>();
                    for (String file:filenames) {
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        while ((reader.readLine()) != null) {
                            outputList.add(reader.readLine());
                        }
                        System.out.println(tail.getEndStrings(outputList,
                                Integer.parseInt(cl.getOptionValue("n"))));
                    }
                }
            }
            else if (cl.hasOption(AMO_SYM)) {
                if (cl.hasOption(NAM_OFI)) {
                    for (String file:filenames) {
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        String fullRead = "";
                        while ((reader.readLine()) != null) {
                            fullRead += reader.readLine();
                        }
                        BufferedWriter writer = new BufferedWriter(new FileWriter(cl.getOptionValue("o")));
                        String extract = tail.getEndSymbols(fullRead,
                                Integer.parseInt(cl.getOptionValue("c")));
                        writer.write(extract);
                    }
                }
                else {
                    for (String file:filenames) {
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        String fullRead = "";
                        while ((reader.readLine()) != null) {
                            fullRead += reader.readLine();
                        }
                        System.out.println(tail.getEndSymbols(fullRead,
                                Integer.parseInt(cl.getOptionValue("c"))));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Не удалось считать аргументы. Необходимо проверить правильность ввода");
            System.out.println("пример правильного ввода строки: tail [-c num|-n num] [-o ofile] file0 file1 file2 … \n " +
                    "-c num отвечает за количество выводимых символов с кона файла\n" +
                    "-b num отвечает за количество выводимых строк с конца файла\n" +
                    "-o file отвечает за имя файла, содержащего результат выполнения утилиты");
        }

    }
}
