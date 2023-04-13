import org.apache.commons.cli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class InFile {

    public List<String> open(CommandLine cmd) {
        int countS = 0;
        String openFile = String.join("", cmd.getArgList());
        List<String> inFile = null;
        try {
            inFile = Files.readAllLines(Paths.get(openFile));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        if (cmd.hasOption("s")) {
            countS = Integer.parseInt(cmd.getOptionValue("s"));
        }
        Change changeFile = new Change(inFile, cmd.hasOption("i"), countS,
                cmd.hasOption("u"), cmd.hasOption("c"));
        return changeFile.res();
    }
}
