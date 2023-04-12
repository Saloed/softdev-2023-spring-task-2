import java.io.BufferedWriter
import java.io.File

fun main(args: Array<String>) {
    val arguments = ArgumentsCheck(args)
    cutting(arguments)
}

fun cutting(arguments: ArgumentsCheck) {
    val writer: BufferedWriter? = arguments.outputFile?.bufferedWriter()

    if (arguments.inputFile == null) {
        println("Введите текст (после ввода перейдети на новую строку и нажмите Ctrl + D):")
        val inputText = generateSequence { readlnOrNull() }
        val textFromUser = inputText.toList()

        cutText(
            textFromUser, writer, arguments.indent, arguments.rangeStart, arguments.rangeEnd
        )
    } else {
        cutText(
            arguments.inputFile!!, writer, arguments.indent, arguments.rangeStart, arguments.rangeEnd
        )
    }

    writer?.close()
    println("Программа завершена.")
}


fun cutString(
    string: String,
    indentParam: String,
    rangeStart: Int,
    rangeEnd: Int?
): String =
    if (rangeStart < string.length)
        when (indentParam) {
            "-c" ->
                string.substring(
                    rangeStart,
                    if (rangeEnd == null || rangeEnd > string.length - 1)
                        string.length
                    else rangeEnd + 1
                )

            "-w" -> {
                val splitStr = Regex("""\s""").split(string)
                splitStr.subList(
                    rangeStart,
                    if (rangeEnd == null || rangeEnd > splitStr.size - 1)
                        splitStr.size
                    else rangeEnd + 1
                ).joinToString(separator = " ")
            }

            else -> throw IllegalArgumentException()
        }
    else ""



fun <E> cutText(
    inputText: E,
    writer: BufferedWriter?,
    indentParam: String,
    rangeStart: Int,
    rangeEnd: Int?
    ): Unit {
    var firstLine = true
    var newLine: String
    when (inputText) {
        is List<*> -> {
            (inputText as List<String>).forEach { line ->
                newLine = cutString(line, indentParam, rangeStart, rangeEnd)
                writeNewLine(newLine, writer, firstLine)
                firstLine = false
            }
        }

        is File -> {
            inputText.forEachLine { line ->
                newLine = cutString(line, indentParam, rangeStart, rangeEnd)
                writeNewLine(newLine, writer, firstLine)
                firstLine = false
            }
        }
    }
}

fun writeNewLine(newLine: String, writer: BufferedWriter?, firstLine: Boolean): Unit {
    if (writer != null) {
        if (!firstLine) writer.newLine()
        writer.write(newLine)
    } else println(newLine)
}