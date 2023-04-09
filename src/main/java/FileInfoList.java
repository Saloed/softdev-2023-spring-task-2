import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FileInfoList {
    public static Set<String> setOfFiles = new HashSet<>();
    private final List<FileInfo> files = new ArrayList<>();

    public FileInfoList(String way) {
        File dir = new File(way);
        if (dir.isDirectory()) {
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                files.add(new FileInfo(file));
            }
        } else files.add(new FileInfo(dir));
    }

    StringBuilder result = new StringBuilder();

    public void output(Args args) throws IOException {
        if (args.reverse) Collections.reverse(files);
        if (args.out == null) {
            for (FileInfo file : files) {
                file.toString(args, result, setOfFiles);
                result.append(System.lineSeparator());
            }
            System.out.println(result);
        } else {
            FileWriter writer = new FileWriter(args.out);
            for (int i = 0; i < files.size(); i++) {
                files.get(i).toString(args, result, setOfFiles);
                if (i < files.size() - 1) result.append(System.lineSeparator());
            }
            writer.write(result.toString());
            writer.close();
        }
    }
}
