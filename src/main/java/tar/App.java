package tar;

import org.apache.commons.cli.*;


import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class App {
    private final static int BLOCK_SIZE = 4096;

    public static void main(String[] args) {

        Options options = new Options();

        Option input = new Option("u", "input", true, "input file");
        options.addOption(input);

        Option output = new Option("out", "output", true, "output file");
        options.addOption(output);


        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        if (cmd == null) {
            printUsage();
            System.exit(1);
        }
        try {
            if (cmd.hasOption("u")) { // Распаковать файл

                decombine(cmd.getOptionValue("u"));

            } else if (cmd.hasOption("out")) { // Запаковать в файл
                List<String> filenames = new ArrayList(cmd.getArgList());
                combine(filenames, cmd.getOptionValue("out"));

            } else { // Неверные аргументы
                printUsage();
                System.exit(1);
            }
        } catch (java.io.IOException e) {
            System.out.println("Ошибка при открытии файла");
            System.out.println(e.getMessage());
            System.exit(1);
        }

    }

    public static void combine(List<String> filesToCombine, String outFilePath) throws IOException {
        File outFile = new File(outFilePath);
        try (FileOutputStream fos = new FileOutputStream(outFile)) {
            FileHeader header = FileHeader.getHeader(filesToCombine);
            header.writeHeader(fos);
            for (TFile file : header.getFiles()) {
                File inFile = new File(file.getFilepath());
                try (FileInputStream fis = new FileInputStream(inFile)) {
                    long written = 0;
                    byte[] chunk = new byte[BLOCK_SIZE];
                    int read;
                    while (file.getSize() != written) {
                        read = fis.read(chunk);
                        fos.write(chunk, 0, read);
                        written += read;
                    }
                } catch (Exception e) {
                    throw new IOException(file.getFilename());
                }
            }
        }
    }


    public static void decombine(String inFilePath) throws IOException {
        File inFile = new File(inFilePath);
        try (FileInputStream fis = new FileInputStream(inFile)) {
            FileHeader header = FileHeader.parseHeader(fis);
            List<TFile> files = header.getFiles();
            for (TFile file : files) {
                File outFile = new File(file.getFilename());
                try (FileOutputStream fos = new FileOutputStream(outFile)) {
                    long written = 0;
                    int read = 0;
                    byte[] chunk = new byte[BLOCK_SIZE];
                    while (file.getSize() != written) {
                        read = fis.read(chunk);
                        fos.write(chunk, 0, read);
                        written += read;
                    }
                } catch (Exception e) {
                    throw new IOException(file.getFilename());
                }
            }
        }
    }

    public static void printUsage() {
        System.out.println("Использование: " +
                "tar -u filename.txt или\n" +
                "tar file1.txt file2.txt … -out output.txt.");
    }
}
