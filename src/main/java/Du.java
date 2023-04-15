import org.apache.commons.cli.*;

public class Du {
    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("c",false,"summary size");
        options.addOption("h",false,"comfort for eye result");
        options.addOption("si",false,"base 1000");
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse( options, args);
            Explorer explorer = new Explorer(cmd.getArgList());
            Functions functions = new Functions(explorer.makingLongArrayList(),cmd.hasOption("h"), cmd.hasOption("c"), cmd.hasOption("si"));
            functions.out();

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}

