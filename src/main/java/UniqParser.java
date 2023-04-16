import org.kohsuke.args4j.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class UniqParser {
    @Option(name = "-o", metaVar = "-o")
            private boolean isToFile;
    @Argument(metaVar = "-o")
            private Path outputName;
    @Option(name = "-i")
            private boolean i;
    @Option(name = "-u")
            private boolean u;
    @Option(name = "-s", metaVar = "-s")
            private boolean s;

    @Argument(metaVar = "-s")
            private int num;

    @Option(name = "-c")
            private boolean c;
    @Argument(metaVar = "")
            private Path inputName;

    List<String> commands = new ArrayList<>();


    UniqParser(String[] args){
        CmdLineParser parser = new CmdLineParser(args);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.out.println(e.getMessage());
            parser.printUsage(System.err);
        }
    }

//геттеры
    public boolean isToFile(){
       return isToFile;
    }
    public Path getOutputName(){
        return outputName;
    }
    public Path getInputName(){
        return inputName;
    }
    public boolean getI(){
       return i;
    }
    public boolean getU(){
        return u;
    }
    public boolean getS(){
        return s;
    }
    public int getNum(){
        return num;
    }
    public boolean getC(){
        return c;
    }

    UniqParser(boolean insen, boolean uniq, boolean seq, int numCh, boolean count,
               boolean out, Path outputNameF, Path inputNameF) {
      i = insen;
      u = uniq;
      s = seq;
      num = numCh;
      c = count;
      isToFile = out;
      outputName = outputNameF;
      inputName = inputNameF;
    }
}
