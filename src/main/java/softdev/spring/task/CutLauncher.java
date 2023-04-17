package softdev.spring.task;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;

public class CutLauncher {
    @Option(name = "-c", usage = "Indent by char", forbids = "-w")
    boolean ch;
    @Option(name = "-w",usage = "Indent by word", forbids = "-c")
    boolean word;
    @Option(name = "-o", usage = "Output file name")
    String outputFileName;
    @Argument(usage = "Input file name")
    String inputFileName;
    @Argument(index = 1, usage = "range")
    String range;

    public static void main(String[] args) throws IOException {

        new CutLauncher().launch(args);

    }


    public void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
            System.exit(1);
        }
        run(ch, word, inputFileName, outputFileName, range);
    }

    public void run(boolean ch, boolean word, String inputFileName, String outputFileName, String range) {
        try {
            Cut.cut(ch, word, inputFileName, outputFileName, range);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

}
