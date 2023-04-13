import org.apache.commons.cli.*;

import java.util.List;

public class ForTest {
    public static String forTest(CommandLine cmd) {
        InFile file = new InFile();
        List<String> out = file.open(cmd);
        return String.join("\n", out);
    }
}
