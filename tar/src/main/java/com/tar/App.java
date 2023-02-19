package com.tar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        // TODO: Добавить парсинг аргументов
        ArrayList<String> filenames = new ArrayList<String>();

        filenames.add("ex.jpg");
        filenames.add("ex.txt");
        combine(filenames, "out.e");
    }

    public static void combine(ArrayList<String> filesToCombine, String outFilePath) {
        File outFile = new File(outFilePath);
        try (FileOutputStream fos = new FileOutputStream(outFile)) {
            System.out.println("Created File");
            for (String inFilePath : filesToCombine) {
                File inFile = new File(inFilePath);
                try (FileInputStream fis = new FileInputStream(inFile)) {
                    byte[] bytes = new byte[(int) inFile.length()];
                    fis.read(bytes);

                    fos.write(("<" + inFilePath + ">").getBytes());
                    fos.write(bytes);
                    fos.write(("</" + inFilePath + ">").getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception ignored) {
        }
    }

    public static void decombine(String inFilePath) {
        File inFile = new File(inFilePath);
        try (FileInputStream fis = new FileInputStream(inFile)) {
            byte[] bytes = new byte[(int) inFile.length()];// Может, не самая лучшая идея кастить лонг в инт?))

        } catch (Exception ignored) {
        }
    }
}
