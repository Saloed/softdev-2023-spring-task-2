import org.apache.commons.cli.*;

public class Uniq {

    public static void main(String[] args) {
        InFile file = new InFile();
        Filek output = new Filek();
        if (line(args).hasOption("o")) {
            output.out(line(args).getOptionValue("o"), Variant.options.withFile, file.open(line(args)));
        } else {
            output.out(line(args).getOptionValue("o"), Variant.options.noFile, file.open(line(args)));
        }
    }

    public static CommandLine line(String[] args) {
        Options option = new Options();
        option.addOption("i", "register");
        option.addOption("u", "unique");
        option.addOption("o", "output", true, "file name");
        option.addOption("s", true, "ignore");
        option.addOption("c", "counter");
        CommandLineParser parser = new DefaultParser();
        try {
            return parser.parse(option, args);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}
