package org.example;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

import org.apache.commons.io.FileUtils;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import static java.util.Arrays.sort;

public class Main {
    @Option(name = "-l", aliases = "--long", usage = "Display file info")
    private Boolean longArg = false;
    @Option(name = "-h", aliases = "--human", usage = "Display human-readable file size")
    private Boolean humanReadable = false;
    @Option(name = "-r", aliases = "--reverse", usage = "Reverse the output file")
    private Boolean reverse = false;
    @Option(name = "-o", aliases = "--output", usage = "Path of the output txt file")
    private String outputName;
    @Argument(usage = "Path of the input file/directory", required = true)
    private String inputName;

    private File[] filesList;

    public static void main(final String[] args) throws CmdLineException, IOException {
        Main newMain = new Main();
        newMain.argsParse(args);
        newMain.app();
    }


    private void argsParse(final String[] args) throws CmdLineException {
        if (args.length < 1) {
            System.exit(-1);
        }
        CmdLineParser parser = new CmdLineParser(this);
        parser.parseArgument(args);
    }

    private void app() throws IOException {
        File files = new File(inputName);
        if (outputName != null) {
            PrintStream output = new PrintStream(outputName);
            System.setOut(output);
        }
        if (!files.exists()) {
            throw new NullPointerException("Such file does not exist");
        }
        if (files.isFile()) {
            System.out.println(stringAssembler(0, files));
        } else {
            filesList = Objects.requireNonNull(files.listFiles());
            sort(filesList); //Добавлено чисто из-за проблем с тестами GitHub (там файлы шли не в том порядке)
            int dirFileCount = filesList.length;
            if (!reverse) {
                for (int i = 0; i < dirFileCount; i++) {
                    System.out.println(stringAssembler(i, files));
                }
            }
            else {
                for (int i = dirFileCount - 1; i > 0; i--) {
                    System.out.println(stringAssembler(i, files));
                }
            }
        }
    }

    private String checkAccess(String string, File file) { //Проверяет доступ к файлу и добавляет к str соответствующую маску
        String str = string;
        if (file.canExecute()) {
            str += "1";
        } else {
            str += "0";
        }
        if (file.canRead()) {
            str += "1";
        } else {
            str += "0";
        }
        if (file.canWrite()) {
            str += "1";
        } else {
            str += "0";
        }
        return str;
    }

    private String stringAssembler(int i, File directory) throws IOException {
        File file;
        if (directory.listFiles() != null) {
            file = filesList[i];
        }
        else {
            file = directory;
            longArg = true;
        }
        String str = file.getName() + " ";
        if (longArg) {
            str = checkAccess(str, file);
            str += ' ';
            BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            str += attr.lastModifiedTime();
            str += ' ';
            if (humanReadable) {
                str += FileUtils.byteCountToDisplaySize(file.length()).replaceAll(" ", "");
            } else {
                str += file.length();
            }
        }
        return str.trim();
    }
}


