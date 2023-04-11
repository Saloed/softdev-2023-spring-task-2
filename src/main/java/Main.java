import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Split split = new Split();
        split.getArgs(args);
        split.split();
    }

}
