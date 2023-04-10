package org.example.softdev2023springtask2

import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import picocli.CommandLine.Parameters
import java.io.File

fun main(args: Array<String>) {
    CommandLine(Parser()).execute(*args)
}

fun inputNull (outputFile: File?, characters: Int?, numLines: Int?) {
    val inputStream = readln()
    val outputStream = outputFile?.bufferedWriter()
    if (characters == null && numLines == null) {
        if (outputStream == null) println(inputStream)
        else outputStream.write(inputStream)
    } else {
        if (characters != null) {
            if (outputStream == null) println(inputStream.takeLast(characters))
            else outputStream.write(inputStream.takeLast(characters))
        }
        if (numLines != null) {
            if (outputStream == null) println(inputStream)
            else outputStream.write(inputStream)
        }
    }
    outputStream?.close()
    println("\nSuccessful execution of the operation!!")
}





fun task(inputFiles: List<String>, outputFile: File?, characters: Int?, numLines: Int?) {
    val fileOutput = outputFile?.bufferedWriter()
    for (file in inputFiles) {
        val fileReadLines = File(file).readLines()
        if (inputFiles.size > 1) {
            if (inputFiles.indexOf(file) == 0 && fileOutput != null) fileOutput.write(file + "\n")
            else if (fileOutput == null) println(file)
                 else fileOutput.write("\n" + file + "\n")
        }
        if (characters == null && numLines == null) {
            if (fileOutput == null) println(fileReadLines.takeLast(10))
            else fileOutput.write( fileReadLines.takeLast(10).joinToString("\n"))
        } else {
            if (characters != null) {
                if (fileOutput == null) println(File(file).readText().takeLast(characters))
                else fileOutput.write(File(file).readText().takeLast(characters))
            }
            if (numLines != null) {
                if (fileOutput == null) println(fileReadLines.takeLast(numLines))
                else fileOutput.write( fileReadLines.takeLast(numLines).joinToString("\n"))
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
            else if (inputFiles == null) inputNull(outputFile, characters, numLines)
                 else task(inputFiles!!, outputFile, characters, numLines)
    }
}