import java.io.*;
import java.nio.file.Path;
import org.apache.commons.cli.*;
public class Split {
    private boolean d;
    private String oValue="x";
    private String input;
    private int size;
    private String type;
    private final int CHAR_CONST=26;
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
            if (cmd.hasOption("l")) {
                if (type != null) throw new Exception("много аргументов");
                type="l";
                try {
                    size = Integer.parseInt(cmd.getOptionValue("l"));
                }catch (NumberFormatException e){
                    size=100;
                    input=cmd.getOptionValue("l");
                }
            }
            if (cmd.hasOption("c")) {
                if (type != null) throw new Exception("много аргументов");
                type = "c";
                size = Integer.parseInt(cmd.getOptionValue("c"));
            }
            if (cmd.hasOption("n")) {
                if (type != null) throw new Exception("много аргументов");
                type = "n";
                size = Integer.parseInt(cmd.getOptionValue("n"));
            }

            if (cmd.hasOption("o")) {
                String value = cmd.getOptionValue("-o");
                if (value.equals("-")) oValue = input;
                else oValue = value;
            }
            try {
                String[] arguments = cmd.getArgs();
                input = arguments[0];
            }
            catch (Exception e){
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    String str(int i) {
        return i < 0 ? "" : str((i / CHAR_CONST) - 1) + (char)(65 + i % CHAR_CONST);
    }
    String dNum(int fileNum){
        if (!d) {
            int i = (CHAR_CONST-1)+fileNum;
            return str(i);
        }
        return String.valueOf(fileNum);
    }
    public void lSplit() {
        try(BufferedReader br = new BufferedReader(new FileReader(fileName.toFile()))){
            int fileNum=1;
            String outName;
            FileWriter writer;
            String line;
            boolean k =true;
            while (k) {
                int i = 0;
                outName=oValue+ dNum(fileNum) +".txt";
                writer=new FileWriter(outName);
                while (i++<size) {
                    if ((line= br.readLine())!=null){
                        writer.write(line);
                        writer.write("\n");
                    }
                    else {
                        k=false;
                        break;
                    }
                }
                writer.close();
                fileNum++;
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void cSplit(){
        try(BufferedReader br = new BufferedReader(new FileReader(fileName.toFile()))){
            String outName;
            FileWriter writer;
            int fileNum=1;
            int value;
            int last=0;
            while ((value=br.read() )!= -1) {
                int i = 0;
                outName=oValue+ dNum(fileNum) +".txt";
                writer=new FileWriter(outName);
                if (last!=0) writer.write(last);
                writer.write(value);
                while ((last = br.read())!=-1 & i++<size) {
                    writer.write(last);
                }
                writer.close();
                fileNum++;
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void nSplit(){
        try(BufferedReader br = new BufferedReader(new FileReader(fileName.toFile()))) {
            int fileNum = 1;
            String outName;
            FileWriter writer;
            int fileLength = ((int) new File(fileName.toUri()).length() / 2)/size;
            boolean k = fileLength % size > 0;
            int value;
            while (fileNum <= size) {
                outName = oValue + dNum(fileNum) + ".txt";
                writer = new FileWriter(outName);
                int length = 0;
                while (length < fileLength) {
                    if ((value = br.read()) != -1) writer.write(value);
                    length++;
                }
                if (fileNum == size & k) {
                    while ((value = br.read()) != -1) {
                        writer.write(value);
                    }
                }
                writer.close();
                fileNum++;
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void split() throws FileNotFoundException {
        fileName= Path.of(input);
        try(BufferedReader br = new BufferedReader(new FileReader(fileName.toFile()))) {
            int fileNum = 1;
            String outName;
            FileWriter writer;
        switch (type) {
            case "l" -> lSplit();
            case "c" -> cSplit();
            case "n" -> nSplit();
        }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
