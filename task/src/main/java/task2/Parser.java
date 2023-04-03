package task2;

import org.kohsuke.args4j.*;
import java.io.*;
import java.util.ArrayList;

import org.kohsuke.args4j.CmdLineException;

public class Parser {

    @Option(name = "-r", metaVar = "recursive", usage = "Search in subdirectories")
    public boolean recursive = false;

    @Option(name = "-d", metaVar = "dir", usage = "Directory to search", forbids = "")
    public String dir = "";

    @Argument(metaVar = "File name", required = true)
    public String fileName;

    public static void main(String[] args) throws IOException {
        for (String str : new Parser().parse(args)) {
            System.out.println(str);
        }
    }

    public ArrayList<String> parse(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("Example: java -jar find.jar [-r] [-d directory] filename.txt");
            parser.printUsage(System.err);
        }

        Finder finder = new Finder(recursive, dir, fileName);
        return new ArrayList<>(finder.find());
    }
}


