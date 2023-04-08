package org.example.softdev2023springtask2

import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import picocli.CommandLine.Parameters
import java.io.File

fun main(args: Array<String>) {
    CommandLine(Parser()).execute(*args)
}

fun task(inputFiles: List<String>?, outputFile: File?, characters: Int?, numLines: Int?) {
    val fileOutput = outputFile?.bufferedWriter()
    var fileInput = inputFiles
    if (fileInput.isNullOrEmpty()) {
        File("files/writer.txt").writeText(readln())
        fileInput = listOf("files/writer.txt")
    }
    for (file in fileInput) {
        val fileReadlines = File(file).readLines()
        if (fileInput.size > 1) {
            if (fileInput.indexOf(file) == 0 && fileOutput != null) fileOutput.write(file + "\n")
            else if (fileOutput == null) println(file) else fileOutput.write("\n" + file + "\n")
        }
        if (characters == null && numLines == null) {
            if (fileOutput == null) println(fileReadlines.takeLast(10).joinToString("\n"))
            else fileOutput.write( fileReadlines.takeLast(10).joinToString("\n") )
        } else {
            if (characters != null) {
                if (fileOutput == null) println(File(file).readText().takeLast(characters))
                else fileOutput.write(File(file).readText().takeLast(characters))
            }
            if (numLines != null) {
                if (fileOutput == null) println(fileReadlines.takeLast(numLines).joinToString("\n"))
                else fileOutput.write( fileReadlines.takeLast(numLines).joinToString("\n"))
            }
        }
    }

    fileOutput?.close()
    println("\nSuccessful execution of the operation!")
}





@Command(name = "parser", mixinStandardHelpOptions = true)
class Parser : Runnable {
    @Parameters(paramLabel = "inputFiles", description = ["one or more files"])
    var inputFiles: List<String>? = null

    @Option(names = ["-o"], description = ["outputFile"])
    var outputFile: File? = null

    @Option(names = ["-c"], description = ["num of characters"])
    var characters: Int? = null

    @Option(names = ["-n"], description = ["num of lines"])
    var numLines: Int? = null

    override fun run() {
        if (characters != null && numLines != null) throw Exception("Error: Can't enter -c and -n together")
            else task(inputFiles, outputFile, characters, numLines)
    }
}