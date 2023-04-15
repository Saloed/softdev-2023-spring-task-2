import org.apache.commons.cli.*;

public class Du {
    public static void main(String[] args) {
        Options options = new Options();
//        options.addOption("c","summary size");
//        options.addOption("h","comfort for eye result");
//        options.addOption("si","osnovanie 1000");
        options.addOption(Option.builder("c").longOpt("summary-size").build());
        options.addOption(Option.builder("h").longOpt("comfort-for-eye-result").build());
        options.addOption(Option.builder("si").longOpt("osnovanie-1000").build());
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

