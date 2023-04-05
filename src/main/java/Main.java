import java.io.IOException;

class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Параметры командной строки: split [-d] [-l num|-c num|-n num] [-o ofile] file");
            System.exit(1);
        }
        try {
            Splitter s = new Splitter(args);
            s.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}