package softdev.spring.task;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class tail {
    public static String getEndSymbols(String readFileChars, Integer numberOfSymbols)  {
        if (numberOfSymbols >= readFileChars.length())
            return readFileChars.substring(readFileChars.length() - numberOfSymbols);
        else throw new java.lang.IllegalArgumentException();
    }
    public static List<String> getEndStrings(ArrayList<String> readFileStrings, Integer numberOfStrings){
        if (numberOfStrings >= readFileStrings.size())
            return readFileStrings.subList(readFileStrings.size() - numberOfStrings, readFileStrings.size());
        else throw new java.lang.IllegalArgumentException();
    }
}
