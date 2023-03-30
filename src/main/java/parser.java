import org.kohsuke.args4j.*;
import java.io.*;

public class parser {
    @Option(name = "-i", required = false)
    Boolean flag_i = true;

    @Option(name = "-s")
    Integer flag_s = 0;

    @Option(name = "-u", required = false)
    Boolean flag_u = false;

    @Option(name = "-c", required = false)
    Boolean flag_c = false;

    @Option(name = "-o")
    String flag_o = "";
}
