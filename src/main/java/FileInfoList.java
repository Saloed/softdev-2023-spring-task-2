import java.io.*;
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

    StringBuilder result = new StringBuilder();

    public void output(Args args) throws IOException {
        BufferedWriter writer;
        if (args.reverse) Collections.reverse(files);
        if (args.out == null) {
            writer = new BufferedWriter(new OutputStreamWriter(System.out));
        } else {
            writer = new BufferedWriter(new FileWriter(args.out));
        }
        for (FileInfo file: files) {
            file.toString(args, result);
            result.append(System.lineSeparator());
        }
        writer.write(result.toString());
        writer.flush();
        writer.close();
    }
}
