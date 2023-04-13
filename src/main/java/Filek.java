import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class Filek {

    public void out(String path, Variant.options option, List<String> in) {
        if (Variant.options.withFile == option) {
            File file = new File(path);
            try (FileWriter fis = new FileWriter(file)) {
                for (String s : in) {
                    fis.write(s);
                    fis.write("\n");
                }
            } catch (Exception e) {
                System.out.println("Bruuuuh");
                e.printStackTrace();
            }
        } else {
            for (String s : in) {
                System.out.println(s);
            }
        }
    }

}
