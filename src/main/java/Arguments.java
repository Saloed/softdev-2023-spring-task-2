import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Arguments {
    public String parsArgWord(List<String> arguments) {
        if (arguments.size() == 0 || arguments.size() > 2) {
            System.out.println("Неверный формат ввода");
            System.exit(1);
        }
        return arguments.get(0);
    }

    public List<String> parsArgFile(List<String> arguments) {
        try {
            String inputname = arguments.get(arguments.size() - 1);
            return Files.readAllLines(Paths.get(inputname));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return arguments;
    }
}
