import org.apache.commons.cli.ParseException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        IfFile file=new IfFile();
        file.superV(args);
    }
}
