package tar;

import org.apache.commons.cli.*;


import java.io.*;
import java.util.ArrayList;


public class App {
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
        if (cmd.hasOption("u")) { // Распаковать файл
            try {
                decombine(cmd.getOptionValue("u"));
            } catch (java.io.IOException e) {
                System.out.println("Ошибка при открытии файла");
                System.out.println(e.getMessage());
                System.exit(1);
            }
        } else if (cmd.hasOption("out")) { // Запаковать в файл
            ArrayList<String> filenames = new ArrayList(cmd.getArgList());
            try {
                combine(filenames, cmd.getOptionValue("out"));
            } catch (java.io.IOException e) {
                System.out.println("Ошибка при открытии файла");
                System.out.println(e.getMessage());
                System.exit(1);
            }
        } else { // Неверные аргументы
            printUsage();
            System.exit(1);
        }

    }

    public static void combine(ArrayList<String> filesToCombine, String outFilePath) throws IOException {
        File outFile = new File(outFilePath);

        try (FileOutputStream fos = new FileOutputStream(outFile)) {

            FileHeader header = new FileHeader(filesToCombine);

            fos.write(header.getHeader());

            for (TFile file : header.getFiles()) {
                File inFile = new File(file.getFilepath());
                try (FileInputStream fis = new FileInputStream(inFile)) {
                    long written = 0;
                    byte[] chunk = new byte[4096];
                    int read;
                    while( file.getSize()!=written){
                        read = fis.read(chunk);
                        fos.write(chunk,0,read);
                        written+=read;
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
            byte[] headerLengthBytes = new byte[8];
            fis.read(headerLengthBytes);
            long headerLength = FileHeader.bytesToLong(headerLengthBytes);

            byte[] headerBytes = new byte[(int) headerLength - 8]; // Заголовок же не будет больше 2ГБ?
            fis.read(headerBytes);
            FileHeader header = new FileHeader(headerBytes);
            ArrayList<TFile> files = header.getFiles();
            for (TFile file : files) {
                File outFile = new File(file.getFilename());
                try (FileOutputStream fos = new FileOutputStream(outFile)) {
                    long written = 0;
                    int read = 0;
                    byte[] chunk = new byte[4096];
                    while( file.getSize()!=written){
                        read = fis.read(chunk);
                        fos.write(chunk,0,read);
                        written+=read;
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
