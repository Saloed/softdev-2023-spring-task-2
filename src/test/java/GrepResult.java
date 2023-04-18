import java.util.List;

public class GrepResult {
    public static String Res4Test(List<String> input) {
        if (input.size() < 2) {
            System.out.println("Неверный формат ввода");
            System.exit(1);
        }
        boolean r = input.contains("-r");
        boolean v = input.contains("-v");
        boolean i = input.contains("-i");
        List<String> argum = input.subList(input.size() - 2, input.size());
        String word = new Arguments().parsArgWord(argum);
        List<String> file =  new Arguments().parsArgFile(argum);
        return String.join("\n", new Processing().result(v, i, r, word, file));
    }
}
