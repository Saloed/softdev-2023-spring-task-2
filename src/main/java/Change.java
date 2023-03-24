import java.util.ArrayList;

public class Change {

    private final ArrayList<String> in;
    private final boolean funI;
    private final int funS;
    private final boolean funU;
    private final boolean funC;

    public Change(ArrayList<String> i, boolean fuI, int fuS,
                  boolean fuU, boolean fuC) {
        in = i;
        funI = fuI;
        funS = fuS;
        funU = fuU;
        funC = fuC;
    }

    public ArrayList<String> res() {
        return expi();
    }

    private ArrayList<String> expi() {
        ArrayList<String> exp = new ArrayList<>();
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
                    if (funU) {
                        if (count == 0) {
                            exp.add(in.get(i - 1));

                        }
                        count = 0;
                    } else {
                        if (count != 0) {
                            count++;
                        }
                        if (funC) {
                            exp.add(Integer.toString(count) + " " + in.get(i - 1));
                        } else {
                            exp.add(in.get(i - 1));
                        }
                        count = 0;
                    }
                }
            } else {
                if (a.equals(b)) {
                    count++;
                } else {
                    if (funU) {
                        if (count == 0) {
                            exp.add(in.get(i - 1));
                        }
                        count = 0;
                    } else {
                        if (count != 0) {
                            count++;
                        }
                        if (funC) {
                            exp.add(Integer.toString(count) + " " + in.get(i - 1));
                        } else {
                            exp.add(in.get(i - 1));
                        }
                        count = 0;
                    }
                }
            }
        }
        if (funC) {
            if (!funU) {
                if (count != 0) {
                    count++;
                }
                exp.add(Integer.toString(count) + " " + in.get(in.size() - 1));
            } else {
                if (count == 0) {
                    exp.add(Integer.toString(count) + " " + in.get(in.size() - 1));
                }
            }
        } else {
            if (!funU) {
                if (count != 0) {
                    count++;
                }
                exp.add(in.get(in.size() - 1));
            } else {
                if (count == 0) {
                    exp.add(in.get(in.size() - 1));
                }
            }
        }
        return exp;
    }
}
