import java.io.BufferedWriter
import java.io.File
import java.io.FileNotFoundException

// [-c|-w] [-o ofile] [file] range

fun main(args: Array<String>) {
    require(args.isNotEmpty())
    var index = 0

    val indentParam = when (args[index++]) {         // 0 -> 1
        "[-c]" -> 0
        "[-w]" -> 1
        else -> throw IllegalArgumentException("Неверный параметр отступа")
    }

    val outputFile: File? =
        if (args[index++] != "[-o") null  // 1 -> 2
        else File(args[index++].replace("]", "")) // нужно ли обрабатывать (выдаст файла)? // 2 -> 3

    val sourceFile: File? =
        try {
            File(args[index].substring(1, args[index++].length - 1))
        } catch (e: Exception) {
            if (index != args.size) throw FileNotFoundException()
            else null.also { index-- }
        }

    val range =
        Regex("""-""").split(args[index])

    val rangeStart: Int
    val rangeEnd: Int?

    try {
        if (range[1] <= range[0]) throw Exception()

        rangeStart =
            if (range[0].isNotBlank()) range[0].toInt()
            else 0
        rangeEnd =
            if (range[1].isNotBlank()) range[1].toInt()
            else null
    } catch (e: Exception) {
        throw IllegalArgumentException("Неправильно введен промежуток")
    }


    val writer: BufferedWriter? = outputFile?.bufferedWriter()
    var newLine: String

    when {
        sourceFile != null -> {
            sourceFile.forEachLine { line ->
                newLine = cutString(line, indentParam, rangeStart, rangeEnd)
                writeNewLine(newLine, writer)
            }
        }

        sourceFile == null -> {
            var inputText = ""
            while (inputText.isBlank()) {
                println("Параметр file пуст. Введите текст в строку:")
                inputText = readln()
            }

            val textFromUser = Regex("""\n""").split(inputText)

            textFromUser.forEach { line ->
                newLine = cutString(line, indentParam, rangeStart, rangeEnd)
                writeNewLine(newLine, writer)
            }
        }

        else -> throw IllegalArgumentException()
    }

    writer?.close()
    println("Программа завершена.")
}


fun cutString(
    string: String,
    indentParam: Int,
    rangeStart: Int,
    rangeEnd: Int?
): String =
    when (indentParam) {
        0 ->
            string.substring(
                rangeStart,
                if (rangeEnd == null || rangeEnd > string.length - 1)
                    string.length
                else rangeEnd + 1
            )

        1 ->
            Regex("""\s""").split(string).subList(
                rangeStart,
                if (rangeEnd == null || rangeEnd > string.length - 1)
                    string.length
                else rangeEnd + 1
            ).joinToString(separator = " ")

        else -> throw IllegalArgumentException()
    }

fun writeNewLine(newLine: String, writer: BufferedWriter?) {
    if (writer != null) {
        writer.write(newLine)
        writer.newLine()
    } else println(newLine)
}