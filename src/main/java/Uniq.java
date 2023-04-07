import org.apache.commons.cli.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Uniq {

    private static String string;
    public static void main(String[] args){
        Options option = new Options();
        int countS = 0;
        option.addOption("i","register");
        option.addOption("u", "unique");
        option.addOption("o","output", true, "file name");
        option.addOption("s",true,"ignore");
        option.addOption("c","counter");
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(option, args);
            String tuti = String.join("",cmd.getArgList());
            List<String> inFile =  Files.readAllLines(Paths.get(tuti));
            if (cmd.hasOption("s")) {
                countS = Integer.parseInt(cmd.getOptionValue("s"));
            }
            Change jojo = new Change(inFile, cmd.hasOption("i"), countS,
                    cmd.hasOption("u"), cmd.hasOption("c"));
            List<String> out = jojo.res();
            Filek output = new Filek(out);
            string = String.join("\n", out);
            if (cmd.hasOption("o")) {
                String m2 = cmd.getOptionValue("o");
                File f12 = new File(m2);
                output.out(f12);
            } else {
                output.stressOut();
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
    public static String out(){
        return string;
    }
}
