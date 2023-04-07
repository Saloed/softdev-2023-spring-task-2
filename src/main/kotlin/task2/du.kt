package task2

import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import picocli.CommandLine.Parameters
import java.io.File
import java.io.IOException
import java.util.concurrent.Callable

@Command(name = "du", mixinStandardHelpOptions = true)
class Du : Callable<List<String>> {
    @Option(names = ["-h"], description = ["output in human-readable format"])
    var humRead = false

    @Option(names = ["-c"], description = ["display the total size"])
    var needSum = false

    @Option(names = ["--si"], description = ["base 1000 should be used for all units of measurement used"])
    var thousandBased = false

    @Parameters(paramLabel = "files", description = ["files to find its size"])
    var files: List<File> = emptyList()

    override fun call(): List<String> {
        if (files.isEmpty()) throw IOException("Не было подано ни одного файла")
        files.forEach {
            if (!it.exists()) {
                throw IOException("Данного(-ых) файла(-ов) не существует")
            }
        }
        return getAllSizes(humRead, needSum, thousandBased, files)
    }
}

fun main(args: Array<String>) {
    CommandLine(Du()).execute(*args)
}

fun getAllSizes(humRead: Boolean, needSum: Boolean, thousandBased: Boolean, files: List<File>): List<String> {
    val listOfSizes = emptyList<Long>().toMutableList()
    val resList = emptyList<String>().toMutableList()
    val base = if (thousandBased) 1000 else 1024
    files.forEach {
        var size = 0L
        if (it.isDirectory) {
            it.listFiles()?.let { it1 ->
                allFilesFromDirectory(it1.toList()).forEach { itFile ->
                    size += itFile.length()
                }
            }
        } else size = it.length()
        listOfSizes.add(size)
        if (humRead) {
            val hRSize = toHumRead(size, base)
            val sizeInString = "${hRSize.first} ${hRSize.second}"
            resList.add(sizeInString)
            println("Размер файла $it =  $sizeInString")
        } else {
            val sizeInString = "$size B"
            resList.add(sizeInString)
            println("Размер файла $it = $sizeInString")
        }
    }
    if (needSum) {
        val sum = listOfSizes.sum()
        listOfSizes.add(sum)
        if (humRead) {
            val hRSize = toHumRead(sum, base)
            val sizeInString = "${hRSize.first} ${hRSize.second}"
            resList.add(sizeInString)
            println("Суммарный размер = $sizeInString")
        } else {
            val sizeInString = "$sum B"
            resList.add(sizeInString)
            println("Суммарный размер = $sizeInString")
        }
    }
    return resList
}

fun allFilesFromDirectory(list: List<File>): List<File> {
    val newList = emptyList<File>().toMutableList()
    list.forEach {
        if (it.isDirectory) {
            it.listFiles()?.let { it1 -> allFilesFromDirectory(it1.toList()) }?.let { it2 -> newList.addAll(it2) }
        } else newList.add(it)
    }
    return newList
}

fun toHumRead(size: Long, base: Int): Pair<Double, String> {
    val units = listOf("B", "KB", "MB", "GB")
    var newSize = size.toDouble()
    var count = 0
    while (newSize > base) {
        newSize /= base
        count++
    }
    return Pair(newSize, units[count])
}

