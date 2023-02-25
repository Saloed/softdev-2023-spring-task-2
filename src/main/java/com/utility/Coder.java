package com.utility;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
    Bad case occurs when there are many single symbols
    Hence we create StringBuilder that contains them
    Finally it adds them with negative length at the beginning
    That way we have constant 2 symbols added instead of twice size
 */

public class Coder {
    private final static byte DEFINE_TYPE = 64;

    // method used to encode and write sequence of bytes according to content of "bytes" list
    public static void printByteArray(List<Byte> bytes, OutputStream bw) throws IOException {
        if (bytes.isEmpty()) return;
        else if (bytes.size() == 1 || bytes.get(0) == bytes.get(1)) {
            bw.write((byte) bytes.size());
            bw.write(bytes.get(0));
        } else {
            bw.write((byte) (DEFINE_TYPE + bytes.size()));
            for (Byte b : bytes) bw.write(b);
        }
    }

    public static void encode(String inputFile, String outputFile) throws IOException {
        try (InputStream br = new FileInputStream(inputFile);
             OutputStream bw = new FileOutputStream(outputFile)) {
            List<Byte> bytes = new ArrayList<>();
            while (true) {
                if (bytes.size() == DEFINE_TYPE - 1) {
                    printByteArray(bytes, bw);
                    bytes.clear();
                    continue;
                }
                byte now = (byte) br.read();
                // EOF
                if (now == -1) {
                    printByteArray(bytes, bw);
                    break;
                }
                if (bytes.size() <= 1) {
                    bytes.add(now);
                } else if (now == bytes.get(bytes.size() - 1)) {
                    if (bytes.get(bytes.size() - 1) == bytes.get(bytes.size() - 2)) bytes.add(now);
                    else {
                        printByteArray(bytes, bw);
                        bytes.clear();
                        bytes.add(now);
                    }
                } else {
                    if (bytes.get(bytes.size() - 1) != bytes.get(bytes.size() - 2)) bytes.add(now);
                    else {
                        printByteArray(bytes, bw);
                        bytes.clear();
                        bytes.add(now);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Can't find file");
        }
    }

    public static void decode(String inputFile, String outputFile) {
        try (InputStream br = new FileInputStream(inputFile);
             OutputStream bw = new FileOutputStream(outputFile)) {
            while (true) {
                byte now = (byte) br.read();
                if (now == -1) break;
                if (now < DEFINE_TYPE) {
                    byte chatToOut = (byte) br.read();
                    for (int i = 0; i < now; i++) bw.write((char) chatToOut);
                } else {
                    for (int i = 0; i < now - DEFINE_TYPE; i++) bw.write((char) (byte) br.read());
                }
            }
        } catch (IOException e) {
            System.out.println("Can't find file");
        }
    }
}

