import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

public class Functions {
    private final boolean h;
    private final boolean c;
    private final double k;
    private final List<Long> list;
    public String tester = "";

    public Functions(List<Long> i, boolean h, boolean c, boolean si) {
        this.h = h;
        this.c = c;
        if (si) k = 1000.0;
        else k = 1024.0;
        this.list = i;
    }


        private void print (Long num){
        if (h) {
            System.out.println(outputSize(num));
        }
        else {
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

    public String outForTest() {
        StringBuilder result = new StringBuilder();
        if (c) {
            Long sum = sumFileSizes();
            result.append(outputSize(sum));
            result.append("\n");
        } else {
            for (Long aLong : list) {
                result.append(outputSize(aLong));
                result.append("\n");
            }
        }
        return result.toString();
    }

    public String sumFileSizesForTest(){
        StringBuilder result = new StringBuilder();
        long sum = 0;
        for (Long aLong : list) {
            sum += aLong;
        }
        return outputSize(sum);
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
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        double kb = b / k;
        double mb = kb / k;
        double gb = mb / k;
        double tb = gb / k;
        if (tb > 1) return (String.format(Locale.US,"%.2f", tb) + "TB");
        else if (gb > 1) return (String.format(Locale.US,"%.2f", gb) + "GB");
        else if (mb > 1) return (String.format(Locale.US,"%.2f", mb) + "MB");
        else if (kb > 1) return (String.format(Locale.US,"%.2f", kb) + "KB");
        else return (String.format(Locale.US,"%.2f", b) + "B");
    }

}
