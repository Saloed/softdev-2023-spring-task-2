import java.util.ArrayList;
import java.util.List;

public class Change {

    private final List<String> in;
    private final boolean funI;
    private final int funS;
    private final boolean funU;
    private final boolean funC;
    private final List<String> exp;

    public Change(List<String> i, boolean fuI, int fuS,
                  boolean fuU, boolean fuC) {
        in = i;
        funI = fuI;
        funS = fuS;
        funU = fuU;
        funC = fuC;
        exp = new ArrayList<>();
    }

    public List<String> res() {
        return expi();
    }

    private List<String> expi() {
        String a;
        String b;
        int count = 0;
        for (int i = 1; i < in.size(); i++) {
            a = in.get(i - 1).substring(funS);
            b = in.get(i).substring(funS);
            if (funI) {
                if (a.equalsIgnoreCase(b)) {
                    count++;
                } else {
                    addList(count, i);
                    count = 0;

                }
            } else {
                if (a.equals(b)) {
                    count++;
                } else {
                    addList(count, i);
                    count = 0;
                }
            }
        }
        addList(count, in.size() - 1);
        return exp;
    }

    private void addList(int count, int i) {
        if (funU) {
            if (count == 0) {
                exp.add(in.get(i - 1));

            }
        } else {
            if (count != 0) {
                count++;
            }
            if (funC) {
                exp.add(Integer.toString(count) + " " + in.get(i - 1));
            } else {
                exp.add(in.get(i - 1));
            }
        }
    }
}
