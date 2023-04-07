import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.kohsuke.args4j.*;

public class UniqParser {
    @Option(name = "-o", metaVar = "-o")
            private boolean isToFile;
    @Argument(metaVar = "-o")
            private String o;
    @Option(name = "-i")
            private boolean sen;
    @Option(name = "-u")
            private boolean uni;
    @Option(name = "-s", metaVar = "-s")
            private boolean ign;
    @Argument(metaVar = "-s")
            private int num;
    @Option(name = "-c")
            private boolean count;
    @Argument(metaVar = "")
            private String in;
    UniqParser(String[] args){
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.out.println(e.getMessage());
        }
    }

}
