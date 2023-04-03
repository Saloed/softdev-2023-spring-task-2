package task2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class Finder {

    private final String fileName;

    private String dir = "";

    private final boolean recursive;

    public Finder(boolean recursive, String dir, String fileName) {
        this.recursive = recursive;
        this.dir = dir;
        this.fileName = fileName;
    }

    public ArrayList<String> find() throws IOException {
        ArrayList<File> fileList = new ArrayList<>();
        ArrayList<String> result = new ArrayList<>();
        if (dir.equals("")) {dir = System.getProperty("user.dir");}
        search(new File(dir), fileList);
        for(File file : fileList) {
            result.add(file.getCanonicalPath());
        }
        if (result.isEmpty()) {
            System.err.println("Not found");
        }
        return result;
    }

    private void search(File root, List<File> fileList) {
        if (root.isDirectory()){
            File[] files = root.listFiles();
            Pattern p = Pattern.compile(fileName, Pattern.CASE_INSENSITIVE);
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        if (recursive) {
                            search(file, fileList);
                        }
                    }
                    else {
                        if (p.matcher(file.getName()).find()) {
                            fileList.add(file);
                        }
                    }
                }
            }
        }
    }
}
