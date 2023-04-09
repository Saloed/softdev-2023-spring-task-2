import java.io.FileNotFoundException;

import static java.lang.System.out;
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        out.println("ptog work");
        Split split = new Split();
        split.getArgs(args);
        split.split();
    }

}
