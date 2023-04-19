import org.apache.commons.cli.*;

import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.Arrays;

public class Conclusion {
    Path loc;
    boolean l;
    boolean h;
    boolean r;
    String output;

    public void line(String[] args){
        Options options = new Options();
        options.addOption("l", "long format");
        options.addOption("h", "human-readable");
        options.addOption("r", "reverse");
        options.addOption("o", true,"output");
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            l= cmd.hasOption("l");
            h= cmd.hasOption("h");
            r= cmd.hasOption("r");
            if (cmd.hasOption("o")) {
                output=cmd.getOptionValue("o");
            }
            loc= Path.of(cmd.getArgs()[0]);
        }
        catch (ParseException e) {
            System.out.println("Аргументы введены неверно");
            System.exit(1);
        }
        if (!l & h){
            System.out.println("Флаг -h не может быть без -l");
            System.exit(1);
        }
    }
}



