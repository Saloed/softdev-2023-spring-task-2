import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class parser {

    private int num = 0;
    private boolean sen = false;
    private boolean o = false;
    private boolean in = false;
    private String outputName = "";
    private String inputName = "";


    //список комманд
    private List<String> commands = new ArrayList<>();

    private final Pattern fileName = Pattern.compile("^\\.*.txt$");
    parser(String[] args) {
        for (int i = 0; i < args.length; ++i) {
            switch (args[i]) {
                case ("-i"):
                    commands.add("i");
                    sen = true;
                    break;
                case ("-c"):
                    commands.add("c");
                    break;
                case ("-u"):
                    commands.add("u");
                    break;
                case ("-s"):
                    commands.add("s");
                    num = Integer.parseInt(args[++i]);
                    break;
                case ("-o"):
                    o = true;
                    outputName = args[++i];
                    break;
                default:
                    inputName = args[i];
                    in = true;
                    break;
            }

        }
    }

    //геттеры


    public int getNum() {
        return num;
    }

    public boolean insensitiveCase(){
        return sen;
    }

    public boolean ToFile(){
        return o;
    }

    public boolean FromFile(){
        return in;
    }

    public String getOutputName() {
        if (!outputName.matches("^\\.*.txt$") && !o)
            try {
                throw new FileNotFoundException("Некорректное имя файла");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        return outputName;
    }

    public String getInputName() {
        Matcher matcher = fileName.matcher(inputName);
        if (!in && !matcher.matches()){
            try {
                throw new FileNotFoundException("Некорректное имя файла или его не существует");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return inputName;
    }

    public List<String> getCommands() {
        return commands;
    }

}