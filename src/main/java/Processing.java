import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Processing {
    public List<String> result(boolean v, boolean i, boolean r, String regex, List<String> file) {
        List<String> res = new ArrayList<>();
        for (String line : file) {
            if ((containReg(i, r, line, regex) && !v) ||
                    (!containReg(i, r, line, regex) && v))
                res.add(line);
        }
        return res;
    }

    private boolean containReg(boolean i, boolean r, String line, String regex) {
        if (r) {
            if (i) {
                Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(line);
                return matcher.matches();
            } else return line.matches(regex);
        }
        else if (i) {
            return line.toLowerCase().contains(regex.toLowerCase());
        } else return line.contains(regex);
    }
}
