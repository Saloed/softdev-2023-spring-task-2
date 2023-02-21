package com.tar;

import org.apache.commons.cli.*;
import tar.src.main.java.com.tar.FileHeader;
import tar.src.main.java.com.tar.PFile;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
            ArrayList<String> filenames = new ArrayList<String>(cmd.getArgList());
            combine(filenames, cmd.getOptionValue("out"));
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


//        filenames.add("ex.jpg");
//        filenames.add("ex.txt");
//        combine(filenames, "out.e");
//        decombine("out.e");
    }

    public static void combine(ArrayList<String> filesToCombine, String outFilePath) {
        File outFile = new File(outFilePath);
        try (FileOutputStream fos = new FileOutputStream(outFile)) {
            FileHeader header = new FileHeader(filesToCombine);
            try {
                fos.write(header.getHeader());
            } catch (Exception e) { // TODO: Вообще это критическая ошибка, ее не нужно игнорировать
                e.printStackTrace();
            }
            for (PFile inFilePath : header.getFiles()) {
                File inFile = new File(inFilePath.getFilename());
                try (FileInputStream fis = new FileInputStream(inFile)) {
                    byte[] bytes = new byte[(int) inFile.length()]; // TODO: Сделать чтение по блокам
                    fis.read(bytes);
                    fos.write(bytes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void decombine(String inFilePath) {
        File inFile = new File(inFilePath);
        try (FileInputStream fis = new FileInputStream(inFile)) {
            byte[] headerLengthBytes = new byte[8];// Может, не самая лучшая идея кастить лонг в инт?))
            fis.read(headerLengthBytes);
            long headerLength = FileHeader.bytesToLong(headerLengthBytes);

            byte[] headerBytes = new byte[(int) headerLength - 8]; // Заголовок же не будет больше 2ГБ?
            fis.read(headerBytes);
            FileHeader header = new FileHeader(headerBytes);
            ArrayList<PFile> files = header.getFiles();
            for (PFile file : files) {
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
//                    byte[] data = new byte[(int) file.getSize()];
//                    fis.read(data);
//                    fos.write(data);
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
