import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class Parser {

    private int num = 0;
    private boolean un = false;
    private boolean sen = false;
    private boolean o = false;
    private boolean in = false;
    private String outputName = "";
    private String inputName = "";


    //список комманд
    private final List<String> commands = new ArrayList<>();
    Parser(String[] args) {
        for (int i = 0; i < args.length; ++i) {
            switch (args[i]) {
                case ("-i"):
                    sen = true;
                    break;
                case ("-c"):
                    commands.add("c");
                    break;
                case ("-u"):
                    un = true;
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

    public boolean isUn(){
        return un;
    }

    public String getOutputName() throws FileNotFoundException {
        return outputName;
    }

    public String getInputName() throws FileNotFoundException {
        return inputName;
    }

    public List<String> getCommands() { return commands; }

}