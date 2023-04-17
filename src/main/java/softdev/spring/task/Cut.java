package softdev.spring.task;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Scanner;

public class Cut {
    public static void cut(boolean chars, boolean word, String inputFileName, String outputFileName, @NotNull String range) throws IOException {
        BufferedReader reader;
        BufferedWriter writer;
        String line;
        int start;
        int end;
        if (chars == word) {
            System.err.println("There must be exactly one option");
            return;
        }
        try {
            String[] rg = range.split("-");
            if (rg.length == 1) {
                start = 0;
                end = Integer.parseInt(rg[0]);
            } else if (rg[0].isEmpty()) {
                start = 0;
                end = Integer.parseInt(rg[1]);
            } else {
                start = Integer.parseInt(rg[0]);
                end = Integer.parseInt(rg[1]);
            }
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
            return;
        }
        if (outputFileName != null) {
            writer = new BufferedWriter(new FileWriter(outputFileName));
        } else {
            writer = new BufferedWriter(new OutputStreamWriter(System.out));
        }

        if (!(new File(inputFileName).exists())) {
            Scanner in = new Scanner(System.in);
            String result = startEnd(in.nextLine(), start, end, chars);
            writer.write(result);
            writer.newLine();
            writer.close();
        }
        else {
            reader = new BufferedReader(new FileReader(inputFileName));
            while ((line = reader.readLine()) != null) {
                String result = startEnd(line, start, end, chars);
                writer.write(result);
                writer.newLine();
            }
            reader.close();
            writer.close();
        }
    }

    static String startEnd(String line, int start, int end, boolean chars) {
        try {
            String res;
            if (start < 0) start = 0;
            if (end < start) return "";
            if (start > line.length()) return "";
            if (chars) {
                if (end >= line.length()) end = line.length() - 1;
                res = line.substring(start, end + 1);
            } else {
                String[] words = line.split(" ");
                if (end > words.length) end = words.length;
                StringBuilder sb = new StringBuilder();
                for (int i = start; i < end; i++) {
                    sb.append(words[i]);
                    sb.append(" ");
                }
                res = sb.toString().trim();
            }
            return res;
        } catch (NumberFormatException e) {
            return "";
        }
    }
}
