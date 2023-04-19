import java.io.*;
import org.apache.commons.io.FileUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class IfFile {
    Conclusion conclusion = new Conclusion();
    private File file;
    FileWriter fos;
    List<File> lst = new ArrayList<>();

    void superV(String[] args) throws IOException{
        conclusion.line(args);
        File dir = null;
        try {
            dir = new File(conclusion.loc.toUri());
        } catch (Exception e) {
            System.exit(1);
        }
        if (conclusion.output!=null) {
            fos = new FileWriter(conclusion.output);
        }

        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (file.isFile())
                    lst.add(file);
            }
        }
        else {
            lst.add(dir);
        }
        if (conclusion.output==null) {
            str();
        }
        else {
            file();
        }
    }


    void str() {
        if (!conclusion.r) {
            for (int i = 0; i < lst.toArray().length; i++) {
                this.file = lst.get(i);
                System.out.println(Format(file));
            }
        } else {
            for (int i = lst.toArray().length - 1; i >= 0; i--) {
                this.file = lst.get(i);
                System.out.println(Format(file));
            }
        }
    }
    void file() throws IOException {
        if (!conclusion.r) {
            for (int i = 0; i < lst.toArray().length; i++) {
                this.file = lst.get(i);
                fos.write(Format(file));
            }
            fos.close();
        } else {
            for (int i = lst.toArray().length - 1; i >= 0; i--) {
                this.file = lst.get(i);
                fos.write(Format(file));
            }
            fos.close();
        }
    }
    public String Format(File file) {
        String rez = file.getName() + " ";
        if(conclusion.h) {
            if (file.canRead()) rez += "r";
            else rez += "-";
            if (file.canWrite()) rez += "w";
            else rez += "-";
            if (file.canExecute()) rez += "x";
            else rez += "-";
            SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            rez += " " + date.format(file.lastModified());
            rez += " " + FileUtils.byteCountToDisplaySize(file.length()).replaceAll(" ", "");
        }
        else if (conclusion.l) {
            if (file.canRead()) rez += "1";
            else rez += "0";
            if (file.canWrite()) rez += "1";
            else rez += "0";
            if (file.canExecute()) rez += "1";
            else rez += "0";
            SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            rez += " " + date.format(file.lastModified());
            rez += " " + file.length() + "bytes";
        }
        return rez;
    }
}
