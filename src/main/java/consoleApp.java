import java.util.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**Объединение последовательностей одинаковых идущих подряд строк в файле в одну:
 file задаёт имя входного файла. Если параметр отсутствует, следует считывать текст с консоли.
 Флаг -o ofile  задаёт имя выходного файла. Если параметр отсутствует,
 следует выводить результаты на консоль.
 Флаг -i означает, что при сравнении строк следует не учитывать регистр символов.
 Флаг -s N означает, что при сравнении строк следует игнорировать первые N символов каждой строки.
 Выводить нужно первую строку.
 Флаг -u означает, что следует выводить в качестве результата только уникальные строки
 (т.е. те, которые не были объединены с соседними).
 Флаг -с означает, что перед каждой строкой вывода следует вывести количество строк,
 которые были заменены данной (т.е. если во входных данных было 2 одинаковые строки,
 в выходных данных должна быть одна с префиксом “2”).

 Command line: uniq [-i] [-u] [-c] [-s num] [-o ofile] [file]

 В случае, когда какое-нибудь из имён файлов указано неверно, следует выдать ошибку.

 Кроме самой программы, следует написать автоматические тесты к ней.
 input:
 xxx
 avc
 avc

 -u -c
 1 xxx

 -c
 1 xxx
 2 avc

 */

public class consoleApp {

    private static List<Pair<Integer, String>> counting(List<String> input) {
        List<Pair<Integer, String>> res = new ArrayList<>();
        int c = 1;
        String previousLine = "";

        for (int i = 0; i < input.size(); ++i){
            String actualLine = input.get(i);
            if (!actualLine.equals(previousLine)) {
                c = 1;
                res.add(new Pair<>(c, actualLine));
            }
            else {
                res.remove(res.size() - 1);
                res.add(new Pair<>(c, actualLine));
            }

            previousLine = actualLine;
            ++c;

        }

        return res;
    }

    // подготовка информации с консоли или из файла
    private static List<String> readOut(Scanner input) {
        List<String> res = new ArrayList<>();
        while (input.hasNext()) {
            String actualLine = input.nextLine();
            res.add(actualLine);
        }
        return res;
    }


    // методы для вывода информации
    static void outputFile(List<String> list, String output) throws IOException {
        FileWriter writer =  new FileWriter(output);
        for (int i = 0; i < list.size(); i++){
            writer.write(list.get(i));
            writer.write("\n");
        }
        writer.close();
    }

    static void outputStream(List<String> list) {
        for (int i = 0; i < list.size(); ++i) {
            System.out.println(list.get(i));
        }
    }


    // флаги
    static List<String> defaultFun (List<String> input) {
        String previousLine = "";

        List<String> res = new ArrayList<>();

        for (int i = 0; i < input.size(); ++i) {

            String actualLine = input.get(i);

            if (!previousLine.equals(actualLine)) {
                res.add(actualLine);
            }
            previousLine = actualLine;
        }
        return res;
    }

    static List<String> notSensitiveCase (List<String> input) {
        String previousLine = "";
        List<String> res = new ArrayList<>();

        for (int i = 0; i < input.size(); ++i ) {
            String actualLine = input.get(i);
            if (!previousLine.toLowerCase(Locale.ROOT).equals(actualLine.toLowerCase())) {
                res.add(actualLine);
            }
            previousLine = actualLine;
        }
        return res;
    }

    static List<String> numChar (List<String> input, int num) {

        String previousLine = "";

        List<String> res = new ArrayList<>();

        for (int i = 0; i < input.size(); ++i ) {
            String actualLine = input.get(i);
            String subStr = "";
            if (actualLine.length() > num) {
                subStr = actualLine.substring(num );
            }
            String prevSubStr = "";

            if(previousLine.length() > num)  {
                prevSubStr = previousLine.substring(num );
            }

            if (!prevSubStr.equals(subStr)|| prevSubStr.equals("")){
                res.add(actualLine);
            }
            previousLine = actualLine;
        }
        return res;
    }

    static ArrayList<String> countingLines (List<String> input) {
        List<Pair<Integer, String>> list = counting(input);
        ArrayList<String> res = new ArrayList<>();
        for (Pair<Integer, String> integerStringPair : list) {
            res.add(integerStringPair.toString());
        }
        return res;
    }

    static List<String> unique (List<String> input) {
        List <Pair<Integer, String>> list = counting(input);
        ArrayList<String> res = new ArrayList<>();
        for (Pair<Integer, String> integerStringPair : list) {
            if (integerStringPair.first == 1) {
                res.add(integerStringPair.second);
            }
        }
        return res;
    }

    public static void main(String[] args) throws IOException {

        parser pars = new parser(args);
        List commands = pars.getCommands();
        List list;
        Scanner input;

        //получение и запись строчек в список
        if (pars.FromFile()) {
            input = new Scanner(new FileReader(pars.getInputName()));
        }
        else {
            input = new Scanner(new InputStreamReader(System.in));
        }
        list = readOut(input);

        //выполнение флагов
        if (commands.isEmpty()) {
            list = defaultFun(list);
        } else {
            for (int i = 0; i < commands.size(); ++i) {
                String actCom = commands.get(i).toString();
                switch (actCom){
                    case("i"):
                        list = notSensitiveCase(list);
                        break;
                    case ("u"):
                        list = unique(list);
                        break;
                    case ("c"):
                        list = countingLines(list);
                        break;
                    case ("s"):
                        list = numChar(list, pars.getNum());
                        break;
                }
            }
        }

        //вывод полученного списка строк как файл или построчно на консоль
        if (pars.ToFile()){
            outputFile(list, pars.getOutputName());
        }
        else {
            outputStream(list);
        }
    }
}
