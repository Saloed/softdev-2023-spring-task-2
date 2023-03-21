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

public class Main {
    @Option(name = "-l", aliases = "--long", usage = "Display file info")
    private Boolean longArg = false;
    @Option(name = "-h", aliases = "--human", usage = "Display human-readable file size")
    private Boolean humanReadable = false;
    @Option(name = "-r", aliases = "--reverse", usage = "Reverse the output file")
    private Boolean reverse = false;
    @Option(name = "-o", aliases = "--output", usage = "Path of the output txt file")
    private String outputName;
    @Argument(usage = "Path of the output txt file", required = true)
    private String inputName;

    public static void main(final String[] args) throws CmdLineException, IOException {
        Main newMain = new Main();
        newMain.argsParse(args);
        newMain.app();
    }


    public void argsParse(final String[] args) throws CmdLineException {
        if (args.length < 1) {
            System.exit(-1);
        }
        CmdLineParser parser = new CmdLineParser(this);
        parser.parseArgument(args);
    }

    public void app() throws IOException {
        File files = new File(inputName);
        if (outputName != null) {
            PrintStream output = new PrintStream(outputName);
            System.setOut(output);
        }
        if (files.isFile()) {
            BasicFileAttributes attr = Files.readAttributes(files.toPath(), BasicFileAttributes.class);
            String str = checkAccess("", files) + ' ';
            str += attr.lastModifiedTime() + " ";
            if (humanReadable) {
                str += FileUtils.byteCountToDisplaySize(files.length()).replaceAll(" ", "");
            } else {
                str += files.length();
            }
            System.out.println(str);
        } else {
            if (!reverse) {
                for (int i = 0; i < Objects.requireNonNull(files.listFiles()).length; i++) {
                    System.out.println(stringAssembler(i, files));
                }
            }
            else {
                for (int i = Objects.requireNonNull(files.listFiles()).length - 1; i > 0; i--) {
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
        File file = Objects.requireNonNull(directory.listFiles())[i];
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


