import org.apache.commons.cli.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Uniq {

    public static void main(String[] args) {
        int countS = 0;
        try {
            CommandLine cmd = line(args);
            String openFile = String.join("", cmd.getArgList());
            List<String> inFile = Files.readAllLines(Paths.get(openFile));
            if (cmd.hasOption("s")) {
                countS = Integer.parseInt(cmd.getOptionValue("s"));
            }
            Change changeFile = new Change(inFile, cmd.hasOption("i"), countS,
                    cmd.hasOption("u"), cmd.hasOption("c"));
            List<String> out = changeFile.res();
            Filek output = new Filek(out);
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

    public static CommandLine line(String[] args) throws ParseException {
        Options option = new Options();
        option.addOption("i", "register");
        option.addOption("u", "unique");
        option.addOption("o", "output", true, "file name");
        option.addOption("s", true, "ignore");
        option.addOption("c", "counter");
        CommandLineParser parser = new DefaultParser();
        return parser.parse(option, args);
    }

    public static String gop(String[] args) {
        try {
            return ForTest.forTest(line(args));
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return "";
    }
}
