import java.io.*;
import java.util.List;

public class Filek {

    public void out(String path, Variant.options option, List<String> in) {
        if (Variant.options.withFile == option) {
            File file = new File(path);
            try (var fis = new FileOutputStream(file)) {
                out(fis, in);
            } catch (Exception e) {
                System.out.println("Bruuuuh");
                e.printStackTrace();
            }
        } else {
           out(System.out, in);
        }
    }

    public void out(OutputStream out, List<String> in){
        try(var writer = new PrintWriter(out)){
            for (String s : in) {
                writer.println(s);
            }
        }
    }
}