package com.utility;

import java.io.File;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        try {
            // Checking for correct input arguments
            StringBuilder sb = new StringBuilder();
            for (String arg : args) sb.append(arg);
            String outputFileName;
            String inputFileName;
            if (sb.toString().matches("-[zu]-out\\w+.txt\\w+.txt") && args.length == 4) {
                outputFileName = args[2];
                inputFileName = args[3];
            } else if (sb.toString().matches("-[zu]\\w+.txt") && args.length == 2) {
                outputFileName = null;
                inputFileName = args[1];
            } else throw new IllegalArgumentException();
            // if -out not defined create file: [inputFileName]-changed.txt
            if (outputFileName == null) {
                outputFileName = inputFileName + "-changed.txt";
                File file = new File("." + File.separator + inputFileName + "-changed.txt");
                file.createNewFile();
            }
            // Encoding or decoding
            if (args[0].equals("-z")) Coder.encode(inputFileName, outputFileName);
            else Coder.decode(inputFileName, outputFileName);
        } catch (IllegalArgumentException e) {
            System.out.println("Something wrong with arguments");
        } catch (FileDecodingException | IllegalFileFormatException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println("Can't create output.txt file");
        }
    }
}
