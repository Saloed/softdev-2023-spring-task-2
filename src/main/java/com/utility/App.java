package com.utility;

import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) {
        Options options = new Options();

        Option encode = new Option("z", "zcommand", true, "input file path");
        encode.setRequired(false);
        options.addOption(encode);

        Option decode = new Option("u", "ucommand", true, "output file");
        decode.setRequired(false);
        options.addOption(decode);

        Option outputFile = new Option("out", "outcommand", true, "output file");
        outputFile.setRequired(false);
        options.addOption(outputFile);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
            String encoding = cmd.getOptionValue("zcommand");
            String decoding = cmd.getOptionValue("ucommand");
            String outputFileName = cmd.getOptionValue("outcommand");
            if (outputFileName == null) {
                outputFileName = "changed-" + ((encoding == null) ? decoding : encoding);
                File file = new File(Paths.get(outputFileName).toAbsolutePath().toString());
                file.createNewFile();
            }
            if (encoding != null && decoding == null) {
                Coder.encode(encoding, outputFileName);
            } else if (encoding == null && decoding != null) {
                Coder.decode(decoding, outputFileName);
            } else throw new IllegalArgumentException();
        } catch (IllegalArgumentException | ParseException e) {
            System.out.println("Something wrong with arguments");
        } catch (IOException e) {
            System.out.println("Can't create output file");
        }
    }
}
