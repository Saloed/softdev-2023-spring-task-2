import java.io.BufferedWriter

// [-c|-w] [-o ofile] [file] range

fun main(args: Array<String>) {
    val arguments = ArgumentsCheck(args)
    cutting(arguments)
}

fun cutting(arguments: ArgumentsCheck) {
    val writer: BufferedWriter? = arguments.outputFile?.bufferedWriter()
    var newLine: String
    var firstLine = true

    if (arguments.inputFile == null) {
        var inputText = ""
        while (inputText.isBlank()) {
            println("Параметр file пуст. Введите текст в строку:")
            inputText = readln()
        }

        val textFromUser = Regex("""\n""").split(inputText)

        textFromUser.forEach { line ->
            newLine = cutString(line, arguments.indent, arguments.rangeStart, arguments.rangeEnd)
            writeNewLine(newLine, writer, firstLine)
            firstLine = false
        }
    } else {
        arguments.inputFile!!.forEachLine { line ->
            newLine = cutString(line, arguments.indent, arguments.rangeStart, arguments.rangeEnd)
            writeNewLine(newLine, writer, firstLine)
            firstLine = false
        }
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

fun writeNewLine(newLine: String, writer: BufferedWriter?, firstLine: Boolean) {
    if (writer != null) {
        if (!firstLine) writer.newLine()
        writer.write(newLine)
    } else println(newLine)
}