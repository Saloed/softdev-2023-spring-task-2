import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Functions {
    private final boolean h;
    private final boolean c;
    private final double k;
    private final ArrayList<Long> list;
    public String tester = "";

    public Functions(ArrayList<Long> i, boolean h, boolean c, boolean si) {
        this.h = h;
        this.c = c;
        if (si) k = 1000.0;
        else k = 1024.0;
        this.list = i;
    }

    private void print (Long num){
        if (h) {
            tester+=outputSize(num);
            tester+="\n";
            System.out.println(outputSize(num));
        }
        else {
            tester+=num;
            tester+="\n";
            System.out.println(num);
        }
    }


    public void out() {
        if (c) {
            Long sum = sumFileSizes();
            print(sum);
        }
        else {
            for (Long aLong : list) {
                print(aLong);
            }
        }
    }

    private long sumFileSizes() {
        long sum = 0;
        for (Long aLong : list) {
            sum += aLong;
        }
        return sum;
    }

    private String outputSize(long size) {
        double b = size;
        double kb = size / k;
        double mb = size / k / k;
        double gb = size / k / k / k;
        double tb = size / k / k / k / k;
        if (tb > 1) return (String.format("%.2f", tb) + "TB");
        else if (gb > 1) return (String.format("%.2f", gb) + "GB");
        else if (mb > 1) return (String.format("%.2f", mb) + "MB");
        else if (kb > 1) return (String.format("%.2f", kb) + "KB");
        else return (String.format("%.2f", b) + "B");
    }

}
