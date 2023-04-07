import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.nio.file.Files;

import org.apache.commons.cli.*;



public class Splitter {
    private boolean flagD = false; //Означает, что выходные файлы следует называть ofile1...
    private boolean flagO = false; //Задаёт базовое имя выходного файла
    private int sizeFile = 100; //Размер по умолчанию
    private String inputFile; //Имя входного файла
    private String outputFile = "x"; //Базовое имя выходного файла равняется “x”.
    private final List<String> content; //Содержимое входного файла
    private Type splitType;

    private enum Type {
        LINE, CHAR, FILE
    }

    public Splitter(String[] args) throws IOException {
        initialize(args);
        if (splitType == null) splitType = Type.LINE;
        File file = new File(inputFile);
        if (!file.exists()) throw new FileNotFoundException("Файл " + inputFile + " не найден");
        content = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
    }

    //Метод инициализирует переменные класса Splitter на основе аргументов командной строки
    private void initialize(String[] args) {
        Options options = new Options();
        options.addOption("d", false, "Отладка");
        options.addOption("l", true, "Разбить на строки");
        options.addOption("c", true, "Разбить на символы");
        options.addOption("n", true, "Разбить на файлы");
        options.addOption("o", true, "Выходной файл");
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            flagD = cmd.hasOption("d");
            if (cmd.hasOption("l")) {
                if (splitType != null) throw new IllegalFormatFlagsException("Указаны несколько флагов размерности.");
                splitType = Type.LINE;
                sizeFile = Integer.parseInt(cmd.getOptionValue("l"));
            }
            if (cmd.hasOption("c")) {
                if (splitType != null) throw new IllegalFormatFlagsException("Указаны несколько флагов размерности.");
                splitType = Type.CHAR;
                sizeFile = Integer.parseInt(cmd.getOptionValue("c"));
            }
            if (cmd.hasOption("n")) {
                if (splitType != null) throw new IllegalFormatFlagsException("Указаны несколько флагов размерности.");
                splitType = Type.FILE;
                sizeFile = Integer.parseInt(cmd.getOptionValue("n"));
            }

            if (cmd.hasOption("o")) {
                String value = cmd.getOptionValue("o");
                if (value.equals("-")) flagO = true;
                else outputFile = value;
            }
            // Обработка аргументов
            String[] arguments = cmd.getArgs();
            if (arguments.length == 0) throw new IllegalFormatFlagsException("Нет входного файл");
            else if (arguments.length > 1) throw new IllegalFormatFlagsException("Указано несколько входных файлов");
            else inputFile = arguments[0];
        } catch (ParseException e) {
            System.err.println("Ошибка при разборе аргументов командной строки: " + e.getMessage());
            System.exit(1);
        }
    }

    //Метод разбивает содержимое на список строк фиксированного размера типа LINE
    public List<String> splitLine() {
        if (splitType != Type.LINE) throw new IllegalStateException();
        List<String> result = new ArrayList<>();
        int startSize = content.size();
        for (int i = 0; i < startSize / sizeFile; i++) {
            result.add(String.join("\n", content.subList(0, sizeFile)));
            if (sizeFile > 0) {
                content.subList(0, sizeFile).clear();
            }
        }
        if (content.size() % sizeFile != 0) result.add(String.join("\n", content));
        return result;
    }

    //Метод разбивает содержимое на список строк фиксированного размера типа CHAR
    public List<String> splitChar() {
        if (splitType != Type.CHAR) throw new IllegalStateException();
        List<String> result = new ArrayList<>();
        String content = String.join("", this.content);
        int i = 0;
        while (i < content.length()) {
            result.add(content.substring(i, Math.min(i + sizeFile, content.length())));
            i += sizeFile;
        }
        return result;
    }

    //Метод разбивает содержимое на список строк фиксированного размера типа BYTE
    private byte[][] splitFile() throws IOException {
        if (splitType != Type.FILE) throw new IllegalStateException();
        File file = new File(inputFile);
        int arrayLength = (int) Math.ceil((double) file.length() / sizeFile);
        byte[][] result = new byte[sizeFile][];
        byte[] bytes = Files.readAllBytes(file.toPath());
        int length = (int) file.length() / arrayLength;
        for (int i = 0; i < length; i++) {
            result[i] = Arrays.copyOfRange(bytes, i * arrayLength, (i + 1) * arrayLength);
        }
        int del = (int) file.length() % arrayLength;
        if (del != 0) result[result.length - 1] = Arrays.copyOfRange(bytes, bytes.length - del, bytes.length);
        return result;
    }

    public void save() throws IOException {
        outputFile = (flagO) ? inputFile : outputFile;
        if (splitType == Type.LINE || splitType == Type.CHAR) {
            List<String> files = (splitType == Type.LINE) ? splitLine() : splitChar();
            for (int i = 0; i < files.size(); i++) {
                File file = new File(outputFile + getPostfix(i));
                FileWriter wr = new FileWriter(file);
                wr.write(files.get(i));
                wr.close();
            }
        } else {
            byte[][] files = splitFile();
            for (int i = 0; i < files.length; i++) {
                File file = new File(outputFile + getPostfix(i));
                FileOutputStream wr = new FileOutputStream(file);
                wr.write(files[i]);
                wr.close();
            }
        }
    }

    //Метод возвращает строку, которая является постфиксом для заданного целочисленного значения.
    private String getPostfix(int i) {
        String chars = "abcdefghijklmnopqrstuvwxyz";
        if (flagD) return String.valueOf(i);
        StringBuilder postfix = new StringBuilder();
        try (ByteArrayOutputStream newFileName = new ByteArrayOutputStream()) {
            do {
                newFileName.write(chars.charAt(i % 26));
                i /= 26;
            } while (i > 0);
            postfix.append(newFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return postfix.reverse().toString();
    }
}