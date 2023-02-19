package com.utility;


import java.io.*;

/*
    Bad case occurs when there are many single symbols
    Hence we create StringBuilder that contains them
    Finally it adds them with negative length at the beginning
    That way we have constant 2 symbols added instead of twice size
 */

public class Coder {
    public static void encode(String inputFile, String outputFile) throws IllegalFileFormatException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
             BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            int last = br.read();
            char counter = 1;
            StringBuilder sb = new StringBuilder();
            sb.append((char) last);
            while (last != -1) {
                if ("0123456789".contains("" + (char) last)) {
                    throw new IllegalFileFormatException();
                }
                int now = br.read();
                // 3 cases: now == last, now == -1, others
                if (now == last) {
                    if (sb.length() > 1) {
                        sb.deleteCharAt(sb.length() - 1);
                        bw.write(Integer.toString((-sb.length() == -1) ? 1 : -sb.length()) + sb);
                    }
                    sb.setLength(0);
                    counter++;
                } else if (now == -1) {
                    if (counter == 1) {
                        bw.write(Integer.toString((-sb.length() == -1) ? 1 : -sb.length()) + sb);
                    } else {
                        bw.write(Integer.toString(counter) + (char) last);
                    }
                } else {
                    if (counter > 1) bw.write(Integer.toString(counter) + (char) last);
                    counter = 1;
                    sb.append((char) now);
                }
                last = now;
            }
        } catch (IOException e) {
            System.out.println("Can't find file");
        }
    }

    public static void decode(String inputFile, String outputFile) throws FileDecodingException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
             BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            int last = br.read();
            StringBuilder sb = new StringBuilder();
            while (last != -1) {
                if ("0123456789-".contains("" + (char) last)) {
                    sb.append((char) last);
                } else {
                    if (sb.length() == 0) throw new FileDecodingException();
                    int intValue = Integer.parseInt(sb.toString());
                    if (intValue > 0) {
                        for (int i = 0; i < intValue; i++) bw.write((char) last);
                    } else {
                        bw.write((char) last);
                        for (int i = 0; i < Math.abs(intValue) - 1; i++) bw.write((char) br.read());
                    }
                    sb.setLength(0);
                }
                last = br.read();
            }
        } catch (IOException e) {
            System.out.println("Can't find file");
        }
    }
}
