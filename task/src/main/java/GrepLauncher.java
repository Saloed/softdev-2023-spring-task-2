import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.CmdLineParser;

import java.io.IOException;
import java.util.ArrayList;

public class GrepLauncher {
    @Option(name = "-v", usage = "Inverts the filtering condition (only what does NOT match it is displayed)")
    private Boolean inv = false;
    @Option(name = "-r", usage = "Instead of a word, sets a regular expression for searching (only lines containing this expression are displayed on the console)")
    private Boolean reg = false;
    @Option(name = "-i", usage = "Ignore word case")
    private Boolean ign = false;
    @Argument(usage = "Set search word (only lines containing it are printed to the console)", required = true)
    private String word;
    @Argument(usage = "File to be filtered", index = 1, required = true)
    private String inputFile;

    public static void main(String[] args) {
        new GrepLauncher().run(args);
    }


    public void run(String[] args) {
        CmdLineParser cmdParser = new CmdLineParser(this);
        try {
            cmdParser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar softdev-2023-pring-task-2.jar [-v] [-i] [-r] word inputName.txt");
            cmdParser.printUsage(System.err);
            return;
        }
        Grep grep = new Grep();
        ArrayList result;
        try {
            result = grep.grep(inv, reg, ign, word, inputFile);
            for (Object line : result) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
