import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Processing {
    public static List<String> result(boolean v, boolean i, boolean r, String regex, List<String> file) {
        List<String> res = new ArrayList<>();
        if (r) {
            if (i) {
                Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                for (String line : file) {
                    Matcher matcher = pattern.matcher(line);
                    if ((matcher.matches() && !v) || (!matcher.matches() && v)) res.add(line);
                }
            } else {
                for (String line : file) {
                    if ((line.matches(regex) && !v) || (!line.matches(regex) && v)) res.add(line);
                }
            }
        } else {
            if (i) {
                for (String line : file) {
                    if ((line.toLowerCase().contains(regex.toLowerCase()) && !v) ||
                            (!line.toLowerCase().contains(regex.toLowerCase()) && v))
                        res.add(line);
                }
            } else {
                for (String line : file) {
                    if ((line.contains(regex) && !v) || (!line.contains(regex) && v)) res.add(line);
                }
            }
        }
        return res;
    }

}
