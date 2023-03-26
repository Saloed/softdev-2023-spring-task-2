import java.io.IOException;

import org.kohsuke.args4j.*;

public class Main {
    public static void main(String[] args) {
        try {
            Args arguments = new Args(args);
            start(arguments);
        } catch (CmdLineException e) {
            System.out.println("Неправильно введены аргументы");
        } catch (IOException e) {
            System.out.println("Что-то пошло не так с директорией");
        } catch (IllegalArgumentException e) {
            System.out.println("Флаг -h не может быть без -l");
        }
    }

    public static void start(Args args) throws IOException {
        FileInfoList listOfFiles = new FileInfoList(args.dir);
        if (!args.longFormat && args.humanReadable) throw new IllegalArgumentException();
        listOfFiles.output(args);
    }
}

class Args {
    @Option(name = "-l", usage = "switches the output to a long format")
    boolean longFormat;

    @Option(name = "-h", usage = "switches the output to a human-readable format")
    boolean humanReadable;

    @Option(name = "-r", usage = "reverse")
    boolean reverse;

    @Option(name = "-o", usage = "output to this file <output.file> )", metaVar = "OUTPUT")
    String out;

    @Argument(metaVar = "dir")
    String dir;

    Args(String[] args) throws CmdLineException {
        CmdLineParser parser = new CmdLineParser(this);
        parser.parseArgument(args);
        if (dir == null) {
            dir = System.getProperty("user.dir");
        }
    }

    Args(boolean l, boolean h, boolean r, String o, String dir) {
        longFormat = l;
        humanReadable = h;
        reverse = r;
        out = o;
        this.dir = dir;
    }
}
