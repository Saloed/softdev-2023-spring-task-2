import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.kohsuke.args4j.*;

public class UniqParser {
    @Option(name = "-o", metaVar = "-o")
            private boolean isToFile;
    @Argument(metaVar = "-o")
            private String outputName;
    @Option(name = "-i")
            private boolean insensitive;
    @Option(name = "-u")
            private boolean uniq;
    @Option(name = "-s", metaVar = "-s")
            private boolean ignoreCh;
    @Argument(metaVar = "-s")
            private int chars;
    @Option(name = "-c")
            private boolean count;
    @Argument(metaVar = "")
            private String inputName;
    UniqParser(String[] args){
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.out.println(e.getMessage());
        }
    }

}
