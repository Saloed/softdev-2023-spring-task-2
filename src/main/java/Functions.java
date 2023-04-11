import java.util.List;

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
        double mb = kb / k;
        double gb = mb / k;
        double tb = gb / k;
        if (tb > 1) return (String.format("%.2f", tb) + "TB");
        else if (gb > 1) return (String.format("%.2f", gb) + "GB");
        else if (mb > 1) return (String.format("%.2f", mb) + "MB");
        else if (kb > 1) return (String.format("%.2f", kb) + "KB");
        else return (String.format("%.2f", b) + "B");
    }

}
