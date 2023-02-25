package com.utility;

import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;

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
        //HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
            String encoding = cmd.getOptionValue("zcommand");
            String decoding = cmd.getOptionValue("ucommand");
            String outputFileName = cmd.getOptionValue("outcommand");
            if (encoding != null && decoding == null) {
                if (outputFileName == null) {
                    outputFileName = "changed-" + encoding;
                    File file = new File("." + File.separator + outputFileName);
                    file.createNewFile();
                }
                Coder.encode(encoding, outputFileName);
            } else if (encoding == null && decoding != null) {
                if (outputFileName == null) {
                    outputFileName = "changed-" + decoding;
                    File file = new File("." + File.separator + outputFileName);
                    file.createNewFile();
                }
                Coder.decode(decoding, outputFileName);
            } else throw new IllegalArgumentException();
        } catch (IllegalArgumentException | ParseException e) {
            System.out.println("Something wrong with arguments");
        } catch (IOException e) {
            System.out.println("Can't create output.txt file");
        }
    }
}
