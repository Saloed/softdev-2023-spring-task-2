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

    private final static int DEFINE_TYPE = 128;

    public static void printByteArray(byte[] bytes, int size, OutputStream os) throws IOException {
        if (size != -1) {
            if (size >= DEFINE_TYPE) {
                os.write(size);
                os.write(bytes, 0, size - DEFINE_TYPE + 1);
            } else {
                os.write(size);
                os.write(bytes, 0, 1);
            }
        }
    }

    public static void encode(String inputFile, String outputFile) throws IOException {
        try (InputStream br = new FileInputStream(inputFile);
             OutputStream bw = new FileOutputStream(outputFile)) {
            byte[] bytes = new byte[DEFINE_TYPE];
            int size = -1;
            while (true) {
                byte now = (byte) br.read();
                if (now == -1) {
                    if (size > 1)
                        printByteArray(bytes, size + ((bytes[size - 1] == bytes[size]) ? 0 : 1) * DEFINE_TYPE, bw);
                    else printByteArray(bytes, size + DEFINE_TYPE, bw);
                    break;
                }
                size++;
                bytes[size] = now;
                if (size > 1) {
                    if (bytes[size - 2] == bytes[size - 1] && bytes[size - 1] != bytes[size]) {
                        printByteArray(bytes, size - 1, bw);
                        bytes[0] = now;
                        size = 0;
                    } else if (bytes[size - 2] != bytes[size - 1] && bytes[size - 1] == bytes[size]) {
                        printByteArray(bytes, size + DEFINE_TYPE, bw);
                        size = -1;
                    }
                }
                if (size == DEFINE_TYPE - 1) {
                    printByteArray(bytes, size + ((bytes[size - 1] == bytes[size]) ? 0 : 1) * DEFINE_TYPE, bw);
                    size = -1;
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
                int now = br.read();
                if (now == -1) break;
                if (now >= DEFINE_TYPE) {
                    for (int i = 0; i <= now - DEFINE_TYPE; i++) bw.write((char) br.read());
                } else {
                    char let = (char) br.read();
                    for (int i = 0; i <= now; i++) bw.write(let);
                }
            }
        } catch (IOException e) {
            System.out.println("Can't find file");
        }
    }
}

