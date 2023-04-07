import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Filek {

    private final List<String> in;

    public Filek(List<String> input){

        in = input;
    }
    public void out (File file){
        try (FileWriter fis = new FileWriter(file)) {
            for (String s : in) {
                fis.write(s);
                fis.write("\n");
            }
        } catch (Exception e) {
            System.out.println("Bruuuuh");
            e.printStackTrace();
        }
    }
    public void stressOut(){
        for (String s : in) {
            System.out.println(s);
        }
    }
}
