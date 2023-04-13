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
    val listOfSizes = mutableListOf<Long>()
    val base = if (thousandBased) 1000 else 1024
    files.forEach {
        if (it.isDirectory) {
            listOfSizes.add(sizeOfDirectory(allFilesFromDirectory(it.listFiles().toList())))
        } else listOfSizes.add(it.length())
    }
    val resList = output(listOfSizes, humRead, base).toMutableList()
    resList.forEach {
        println(it)
    }
    if (needSum) {
        val sum = output(listOf(listOfSizes.sum()), humRead, base)[0]
        println("Суммарный размер файлов равен $sum")
        resList.add(sum)
    }
    return resList
}

fun sizeOfDirectory(list: List<File>): Long {
    var size = 0L
    list.forEach {
        size += it.length()
    }
    return size
}

fun allFilesFromDirectory(list: List<File>): List<File> {
    val newList = mutableListOf<File>()
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

fun output(list: List<Long>, humRead: Boolean, base: Int): List<String> {
    val resList = mutableListOf<String>()
    if (humRead) {
        list.forEach {
            val size = toHumRead(it, base)
            resList.add("${size.first} ${size.second}")
        }
    } else {
        list.forEach {
            resList.add("$it B")
        }
    }
    return resList
}
