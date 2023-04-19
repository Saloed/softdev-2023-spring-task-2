import java.util.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
 */

public class ConsoleApp {

    private static List<Pair<Integer, String>> counting(List<String> input,
                                                        boolean senCase, boolean ignChar, int num) {
        List<Pair<Integer, String>> res = new ArrayList<>();
        int c = 1;
        String previousLine = null;

        for (int i = 0; i < input.size(); ++i) {
            String actualLine = input.get(i);
            String b = previousLine;
               if (i > 0) {
                   String a = actualLine;
                   if (senCase) {
                       a = a.toLowerCase();
                       b = previousLine.toLowerCase();
                   }
                   if (ignChar){
                       if (num < a.length()){
                           a = a.substring(num);
                       } else {
                           a = "";
                       }
                       if (num < b.length()){
                           b = b.substring(num);
                       } else {
                           b = "";
                       }
                   }
                   if (!a.equals(b)) {
                       c = 1;
                       res.add(new Pair<>(c,actualLine));
                   } else {
                       res.remove(res.size() - 1);
                       res.add(new Pair<>(c, actualLine));
                   }
               } else {
                   res.add(new Pair<>(1, actualLine));
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
    //Можно сделать функцию, которая принимает OutputStream и в одном случае передать туда файл,
    // а в другом System.out

    static void outputStream(OutputStream out, List<String> list){
        try(PrintWriter writer = new PrintWriter(out)){
            for (String s : list) {
                writer.println(s);
            }
        }
    }

    static void output(List<String> out, String output, boolean isToFile) {
        if (isToFile) {
            File result = new File(output);
            try (FileOutputStream writer = new FileOutputStream(result)) {
                outputStream(writer,out);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
           outputStream(System.out,out);
        }
    }


    // флаги
    static List<String> defaultFun (List<String> input, boolean sen, boolean ignore, int num) {
        List<String> res = new ArrayList<>();
        List<Pair<Integer, String>> interTable = counting(input, sen, ignore, num);
        for (Pair<Integer, String> integerStringPair : interTable) {
            res.add(integerStringPair.second);
        }
        return res;
    }

    static List<String> countingLines (List<String> input, boolean senCase, boolean ignChar, int num) {
        List<Pair<Integer, String>> list = counting(input, senCase, ignChar, num);
        ArrayList<String> res = new ArrayList<>();
        for (Pair<Integer, String> integerStringPair : list) {
            res.add(integerStringPair.toString());
        }
        return res;
    }

    static List<String> unique (List<String> input, boolean senCase, boolean ignChar, int num) {
        List <Pair<Integer, String>> list = counting(input, senCase, ignChar, num);
        ArrayList<String> res = new ArrayList<>();
        for (Pair<Integer, String> integerStringPair : list) {
            if (integerStringPair.first == 1) {
                res.add(integerStringPair.second);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        UniqParser arguments = new UniqParser(args);
        try {
            start(arguments);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void start(UniqParser pars) throws FileNotFoundException {
        List<String> list;

        Scanner input;

        //получение и запись строчек в список

        if (!pars.getInputName().toString().equals("")) {
            input = new Scanner(new FileReader(pars.getInputName().toString()));
        }
        else {
            input = new Scanner(new InputStreamReader(System.in));
        }
        list = readOut(input);

        int num = 0;
        if (pars.getS()){
            num = pars.getNum();
        }

        //выполнение флагов
        if (pars.getU()){
            list = unique(list, pars.getI(), pars.getS(), num);
        }

        if (pars.getC()){
            list = countingLines(list, pars.getI(),pars.getS(), num);
        }

        if (!pars.getU() && !pars.getC()){
            list = defaultFun(list, pars.getI(), pars.getS(), num);
        }

        //вывод полученного списка строк как файл или построчно на консоль

        output(list, pars.getOutputName().toString(), pars.isToFile());

    }

}
