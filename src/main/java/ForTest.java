import org.apache.commons.cli.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ForTest {
    public static String forTest(CommandLine cmd){
        int countS = 0;
        List<String> out = null;
        try {
            String openFile = String.join("", cmd.getArgList());
            List<String> inFile = Files.readAllLines(Paths.get(openFile));
            if (cmd.hasOption("s")) {
                countS = Integer.parseInt(cmd.getOptionValue("s"));
            }
            Change changeFile = new Change(inFile, cmd.hasOption("i"), countS,
                    cmd.hasOption("u"), cmd.hasOption("c"));
            out = changeFile.res();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return String.join("\n", out);
    }
}
