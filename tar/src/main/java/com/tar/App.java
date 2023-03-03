package com.tar;

import org.apache.commons.cli.*;
import tar.src.main.java.com.tar.FileHeader;


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
        if (cmd == null) { // немного похоже на CPP
            printUsage();
            System.exit(1);
        }
        if (cmd.hasOption("u")) { // Распаковать файл
            decombine(cmd.getOptionValue("u")); // Что делать с рантайм ошибками если файла не существует/ что-то еще
        } else if (cmd.hasOption("out")) { // Запаковать в файл
            ArrayList<String> filenames = new ArrayList(cmd.getArgList());
            try {
                combine(filenames, cmd.getOptionValue("out"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else { // Неверные аргументы
            printUsage();
            System.exit(1);
        }

        // Мой парсер
//        if (args.length == 0) {
//            printUsage();
//        }
//        if (args[0].equals("-u")) {
//            if (args.length == 2) {
//                decombine(args[1]);
//            }
//        } else {
//            int i = 0;
//            while (!args[i].equals("-out") && i < args.length) {
//                filenames.add(args[i]);
//                i++;
//            }
//            if (args[i].equals("-out") && args.length == i + 2 && filenames.size() > 0) {
//                combine(filenames, args[i + 1]);
//            } else {
//                printUsage();
//            }
//        }

    }

    public static void combine(ArrayList<String> filesToCombine, String outFilePath) throws IOException {
        File outFile = new File(outFilePath);

        try (FileOutputStream fos = new FileOutputStream(outFile)) {

            FileHeader header = new FileHeader(filesToCombine);
            try {
                fos.write(header.getHeader());
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (tar.src.main.java.com.tar.TFile file : header.getFiles()) {
                File inFile = new File(file.getFilepath());
                try (FileInputStream fis = new FileInputStream(inFile)) {
                    long written = 0; // TODO: Изменить на записал столько, сколько прочитал
                    byte[] chunk = new byte[4096];
                    int read;
                    while (file.getSize() > written + 4096) {
                        read = fis.read(chunk);
//                        fos.write(chunk,0,read); TODO: Как-то так
                        fos.write(chunk);
                        written += 4096;
                    }
                    byte[] chunk2 = new byte[(int) (file.getSize() - written)]; // TODO: тогда это не нужно
                    if (file.getSize() - written > 0) {

                        fis.read(chunk2);
                        fos.write(chunk2);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void decombine(String inFilePath) {
        File inFile = new File(inFilePath);
        try (FileInputStream fis = new FileInputStream(inFile)) {
            byte[] headerLengthBytes = new byte[8];
            fis.read(headerLengthBytes);
            long headerLength = FileHeader.bytesToLong(headerLengthBytes);

            byte[] headerBytes = new byte[(int) headerLength - 8]; // Заголовок же не будет больше 2ГБ?
            fis.read(headerBytes);
            FileHeader header = new FileHeader(headerBytes);
            ArrayList<tar.src.main.java.com.tar.TFile> files = header.getFiles();
            for (tar.src.main.java.com.tar.TFile file : files) {
                File outFile = new File(file.getFilename());
                try (FileOutputStream fos = new FileOutputStream(outFile)) {
                    long written = 0;
                    while (file.getSize() > written + 4096) {
                        byte[] chunk = new byte[4096];
                        fis.read(chunk);
                        fos.write(chunk);
                        written += 4096;
                    }
                    if (file.getSize() - written > 0) {
                        byte[] chunk = new byte[(int) (file.getSize() - written)];
                        fis.read(chunk);
                        fos.write(chunk);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printUsage() {
        System.out.println("Использование: " +
                "tar -u filename.txt или\n" +
                "tar file1.txt file2.txt … -out output.txt.");
    }
}
