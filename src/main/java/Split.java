import java.io.*;
import java.nio.file.Path;

import org.apache.commons.cli.*;

public class Split {
    private boolean d;
    private String oValue = "x";
    private String input;
    private int size;
    private String type;
    final String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private Path fileName;

    void getArgs(String[] args) {
        Options options = new Options();
        options.addOption("d", false, "numeric");
        options.addOption("l", true, "split strings");
        options.addOption("c", true, "split strings");
        options.addOption("n", true, "split numbs");
        options.addOption("o", true, "output file");
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            d = cmd.hasOption("d");
            try {
                String[] arguments = cmd.getArgs();
                input = arguments[0];
            } catch (Exception ignored) {
            }
            if (cmd.hasOption("l")) {
                if (type != null) {
                    new Exception().printStackTrace();
                    System.exit(1);
                }
                type = "l";
                try {
                    size = Integer.parseInt(cmd.getOptionValue("l"));
                } catch (NumberFormatException e) {
                    size = 100;
                    input = cmd.getOptionValue("l");
                }
            }
            if (cmd.hasOption("c")) {
                if (type != null) {
                    new Exception().printStackTrace();
                    System.exit(1);
                }
                type = "c";
                size = Integer.parseInt(cmd.getOptionValue("c"));
            }
            if (cmd.hasOption("n")) {
                if (type != null) {
                    new Exception().printStackTrace();
                    System.exit(1);
                }
                type = "n";
                size = Integer.parseInt(cmd.getOptionValue("n"));
            }
            if (cmd.hasOption("o")) {
                String value = cmd.getOptionValue("-o");
                if (value.equals("-")) oValue = String.valueOf(Path.of(input).getFileName().toString().split("\\.")[0]);
                else oValue = value;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    String str(int i) {
        return i < 0 ? "" : String.valueOf(alphabet.charAt(i / alphabet.length() - 1)) + alphabet.charAt(i % alphabet.length());
    }
    String dNum(int fileNum) {
        if (!d) {
            int i = (alphabet.length() - 1) + fileNum;
            return str(i);
        }
        return String.valueOf(fileNum);
    }
    String getOutName(int i){
        return oValue + dNum(i) + ".txt";
    }
    BufferedReader bufferedReader() {
        try {
            return new BufferedReader(new FileReader(fileName.toFile()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    FileWriter writer(String name) throws IOException {
        return new FileWriter(name);
    }
    public void lSplit() throws IOException {
        BufferedReader br = bufferedReader();
        int fileNum = 1;
        String line;
        boolean k = true;
        while (k) {
            int i = 0;
            FileWriter writer = writer(getOutName(fileNum));
            while (i++ < size) {
                if ((line = br.readLine()) != null) {
                    writer.write(line);
                    writer.write("\n");
                } else {
                    k = false;
                    break;
                }
            }
            writer.close();
            fileNum++;
        }
        bufferedReader().close();
    }
    public void cSplit() throws IOException {
        BufferedReader br = bufferedReader();
        int fileNum = 1;
        int value;
        int last = 0;
        while ((value = br.read()) != -1) {
            int i = 0;
            FileWriter writer = writer(getOutName(fileNum));
            if (last != 0) writer.write(last);
            writer.write(value);
            while ((last = br.read()) != -1 & i++ < size) {
                writer.write(last);
            }
            writer.close();
            fileNum++;
        }
        bufferedReader().close();
    }
    public void nSplit() throws IOException {
        BufferedReader br = bufferedReader();
        int fileNum = 1;
        int fileLength = (int) new File(fileName.toUri()).length();
        boolean k = fileLength % size > 0;
        fileLength=(int) new File(fileName.toUri()).length() / size;
        int value;
        while (fileNum <= size) {
            FileWriter writer = writer(getOutName(fileNum));
            int length = 0;
            while (length < fileLength) {
                if ((value = br.read()) != -1) writer.write(value);
                if (value<=255) length++;
                else length+=2;
            }
            if (fileNum == size & k) {
                while ((value = br.read()) != -1) {
                    writer.write(value);
                }
            }
            writer.close();
            fileNum++;
        }
        bufferedReader().close();
    }
    public void split() throws IOException {
        fileName = Path.of(input);
        switch (type) {
            case "l" -> lSplit();
            case "c" -> cSplit();
            case "n" -> nSplit();
        }
    }
}
