import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FileInfoList {
    private final List<FileInfo> files = new ArrayList<>();

    public FileInfoList(String way) {
        File dir = new File(way);
        if (dir.isDirectory()) {
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                files.add(new FileInfo(file));
            }
        } else files.add(new FileInfo(dir));
    }

    public void output(Args args) throws IOException {
        if (args.reverse) Collections.reverse(files);
        if (args.out == null) {
            for (FileInfo file : files) {
                System.out.println(file.toString(args));
            }
        } else {
            FileWriter writer = new FileWriter(args.out);
            for (int i = 0; i < files.size(); i++) {
                writer.write(files.get(i).toString(args));
                if (i < files.size() - 1) writer.write(System.getProperty("line.separator"));
            }
            writer.close();
        }
    }
}
