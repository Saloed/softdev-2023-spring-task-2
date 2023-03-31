import java.io.*;
import java.nio.file.Path;
import java.util.Objects;

public class Split {
    private boolean d = false;
    private boolean l = false;
    private int lNum;
    private boolean c = false;
    private int cNum;
    private boolean n = false;
    private int nNum;
    private boolean o = false;
    private Path fileName;
    private String oArg;
    public void getArgs(String[] args) throws Exception {
        for (int i = 0; i < args.length; i++) {
            if (args[i].matches("-d")) d = true;
            if (args[i].matches("-l")) {
                this.l = true;
                try {
                    lNum=Integer.parseInt(args[i+1]);
                }
                catch (NumberFormatException e){
                    lNum=100;
                }
            }
            if (args[i].matches("-c")){
                this.c = true;
                try {
                    cNum=Integer.parseInt(args[i+1]);
                }
                catch (NumberFormatException e){
                    throw new Exception("Не введено число");
                }
            }
            if (args[i].matches("-n")){
                this.n = true;
                try {
                    nNum=Integer.parseInt(args[i+1]);
                }
                catch (NumberFormatException e){
                    throw new Exception("Не введено число");
                }
            }
            if (args[i].matches("-o")){
                o = true;
                oArg=args[i+1];
            }
            fileName=Path.of(args[args.length-1]);
        }
        if ((l & c & n )| (l & c) | (l & n) |( c & n )| (!l & !c & !n))
            throw new Exception("Слишком много аргументов");
    }
    String str(int i) {
        return i < 0 ? "" : str((i / 26) - 1) + (char)(65 + i % 26);
    }
    String dNum(int fileNum){
        if (!d) {
            int i = 25+fileNum;
            return str(i);
        }
        return String.valueOf(fileNum);
    }
    String oName(){
        if (!o) return "x";
        else if (Objects.equals(oArg, "-")){
            return fileName.getFileName().toString().split("\\.")[0];
        }
        else {
            return oArg;
        }
    }
    public void lSplit() {
        try(BufferedReader br = new BufferedReader(new FileReader(fileName.toFile()))){
            int fileNum=1;
            String line;
            boolean k =true;
            while (k) {
                int i = 0;
                String outName=oName()+ dNum(fileNum) +".txt";
                File outFile=new File(outName);
                FileWriter writer=new FileWriter(outFile);
                while (i++<lNum) {
                    if ((line= br.readLine())!=null){
                        writer.write(line);
                        writer.write("\n");
                    }
                    else {
                        k=false;
                        break;
                    }
                }
                writer.flush();
                writer.close();
                fileNum++;
            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("Нет файла");
        }
    }

    public void cSplit(){
        try(BufferedReader br = new BufferedReader(new FileReader(fileName.toFile()))){
            int fileNum=1;
            int value;
            int last=0;
            while ((value=br.read() )!= -1) {
                int i = 0;
                String outName=oName()+ dNum(fileNum) +".txt";
                File outFile=new File(outName);
                FileWriter writer=new FileWriter(outFile);
                if (last!=0) writer.write(last);
                writer.write(value);
                while ((last = br.read())!=-1 & i++<cNum) {
                    writer.write(last);
                }
                writer.flush();
                writer.close();
                fileNum++;
            }
        }
        catch (IOException e){
            System.out.println("Нет файла");
        }
        catch (NullPointerException ignored){
        }
    }

    public void nSplit(){
        try(BufferedReader br = new BufferedReader(new FileReader(fileName.toFile()))) {
            int fileNum = 1;
            int fileLength = (int) new File(fileName.toUri()).length()/2;
            boolean k= fileLength % nNum > 0;
            fileLength/=nNum;
            int value;
            int i=0;
            String outName=oName()+ dNum(fileNum) +".txt";
            File outFile=new File(outName);
            FileWriter writer=new FileWriter(outFile);
            while (fileNum<=nNum & (value=br.read()) != -1 ) {
                writer.write(value);
                i++;
                if (i==fileLength){
                    fileNum++;
                    if (fileNum>nNum & k){
                        while ((value= br.read())!=-1) {
                            writer.write(value);
                        }
                    }
                    writer.flush();
                    writer.close();
                    if (fileNum>nNum) break;
                    outName=oName()+ dNum(fileNum) +".txt";
                    outFile=new File(outName);
                    //outFile.createNewFile();
                    writer=new FileWriter(outFile);
                    i=0;
                }
            }
        }
        catch (IOException e){
            System.out.println("Нет файла");
        }
    }
    public void split() {
        if (l) lSplit();
        else if (c) cSplit();
        else if (n) nSplit();
    }
}
