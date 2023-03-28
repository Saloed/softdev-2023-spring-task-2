import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
public class Grep {
    public List grep(Boolean inv, Boolean reg, Boolean ign, String word, String inputFile) throws IOException {
        List<String> filteredFile = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                boolean flag = false;
                if (reg) {
                    if (ign) {
                        Pattern pattern = Pattern.compile(word, Pattern.CASE_INSENSITIVE);
                        Matcher matcher = pattern.matcher(currentLine);
                        if (matcher.find()) flag = true;
                    } else {
                        Pattern pattern = Pattern.compile(word);
                        Matcher matcher = pattern.matcher(currentLine);
                        if (matcher.find()) flag = true;
                    }
                } else if (ign && currentLine.toLowerCase().matches(".*\\b" + word.toLowerCase() + "\\b.*"))
                    flag = true;
                else if (!ign && currentLine.matches(".*\\b" + word + "\\b.*")) flag = true;
                if (inv) flag = !flag;
                if (flag) filteredFile.add(currentLine);
            }
        }
        return filteredFile;
    }
}
