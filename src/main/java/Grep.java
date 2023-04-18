

import org.apache.commons.cli.*;

import java.util.List;

//grep
// Вывод на консоль указанного (в аргументе командной строки) текстового файла с фильтрацией:
// word задаёт слово для поиска (на консоль выводятся только содержащие его строки)
// -r (regex) вместо слова задаёт регулярное выражение для поиска (на консоль выводятся только строки,
// содержащие данное выражение)
// -v инвертирует условие фильтрации (выводится только то, что ему НЕ соответствует)
// -i игнорировать регистр слов
// Command Line: grep [-v] [-i] [-r] word inputname.txt



public class Grep {
    public static void main(String[] args) {
        Options option = new Options();
        option.addOption("v", "invert");
        option.addOption("i", "ignore register");
        option.addOption("r", true, "regex");
        CommandLineParser lineParser = new DefaultParser();
        try {
            CommandLine cmd = lineParser.parse(option, args);
            List<String> arguments = cmd.getArgList();
            String word = new Arguments().parsArgWord(arguments);
            List<String> file = new Arguments().parsArgFile(arguments);
            Processing data = new Processing();
            data.result(cmd.hasOption("v"),
                    cmd.hasOption("i"), cmd.hasOption("r"), word, file).forEach(System.out::println);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}

