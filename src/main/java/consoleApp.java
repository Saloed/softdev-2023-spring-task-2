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

    public static void main(String[] args) {
        new parser(args).commandLine(args);
    }

    // подготовка информации с консоли или из файла

    //static List<String> fromFile(File input) throws IOException{}


    private static List<Pair<Integer, String>> counting(List<String> input) {

        //BufferedReader reader = new BufferedReader(new FileReader(input));
        List<Pair<Integer, String>> res = new ArrayList<>();
        int c = 1;
        //String actualLine = reader.readLine();
        String previousLine = "";

        for (int i = 0; i < input.size(); ++i){//(actualLine != null) {
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
            //actualLine = reader.readLine();
            ++c;

        }

        return res;
    }


    // методы для вывода информации
    static void outputFile(List<String> list, String output) throws IOException {
        FileWriter writer =  new FileWriter(output);
        for (int i = 0; i <= list.size(); i++){
            writer.write(list.get(i));
        }
        writer.close();
    }

    static void outputStream(List<String> list) {
        for (int i = 0; i <= list.size(); ++i) {
            System.out.print(list.get(i));
        }
    }


    // флаги
    static List<String> defaultFun (File input) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(input));

        String actualLine = reader.readLine();
        String previousLine = "";

        List<String> res = new ArrayList<>();

        while (actualLine != null) {

            if (!previousLine.equals(actualLine)){
                res.add(actualLine);
            }

            previousLine = actualLine;
            actualLine = reader.readLine();
        }
        reader.close();
        //writer.close();
        return res;
    }

    static List<String> notSensitiveCase (File input) throws  IOException{
        BufferedReader reader = new BufferedReader(new FileReader(input));

        String actualLine = reader.readLine();
        String previousLine = "";

        List<String> res = new ArrayList<>();
        while (actualLine != null) {

            if (!previousLine.toLowerCase(Locale.ROOT).equals(actualLine.toLowerCase())) {
                res.add(actualLine);

            }
            previousLine = actualLine;
            actualLine = reader.readLine();
        }
        reader.close();
        //writer.close();
        return res;
    }

    static List<String> numChar (File input, int num) throws  IOException{

        BufferedReader reader = new BufferedReader(new FileReader(input));

        String actualLine = reader.readLine();
        String previousLine = "";

        List<String> res = new ArrayList<>();

        while (actualLine != null) {
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
            actualLine = reader.readLine();
        }
        reader.close();
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
}
