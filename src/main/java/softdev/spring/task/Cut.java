package softdev.spring.task;

import org.jetbrains.annotations.NotNull;

import java.io.*;

public class Cut {
    public static void cut(boolean chars, boolean word, String inputFileName, String outputFileName, String range) throws IOException {
        BufferedReader reader;
        BufferedWriter writer;
        String line;
        if (chars == word) {
            System.err.println("There must be exactly one option");
            return;
        }

        if (outputFileName != null) {
            try {writer = new BufferedWriter(new FileWriter(outputFileName));}
            catch (IOException e) {
                System.err.println(e.getMessage());
                throw new IOException();
            }
        } else {
            writer = new BufferedWriter(new OutputStreamWriter(System.out));
        }

        if (!(new File(inputFileName).exists())) {
            String result = startEnd(inputFileName, range, chars);
            writer.write(result);
            writer.newLine();
            writer.close();
        }
        else {
            try {
                reader = new BufferedReader(new FileReader(inputFileName));
            } catch (IOException e) {
                System.err.println(e.getMessage());
                throw new IOException();
            }
            while ((line = reader.readLine()) != null) {
                String result = startEnd(line, range, chars);
                writer.write(result);
                writer.newLine();
            }
            reader.close();
            writer.close();
        }
    }

    static String startEnd(String line, @NotNull String range, boolean chars) {
        try {
            String[] rg = range.split("-");
            int start;
            int end;
            String res;
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
            if (start < 0) start = 0;
            if (end < start) return "";
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
