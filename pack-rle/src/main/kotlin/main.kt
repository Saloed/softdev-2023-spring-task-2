import java.io.File
import kotlinx.cli.*
import java.io.BufferedReader


fun main(args: Array<String>) {
    val parser = ArgParser("pack-rle")
    val type by parser.option(ArgType.String, shortName = "z|-u", description = "Operation type").required()
    val output by
    parser.option(ArgType.String, shortName = "out", description = "Output file name").default("outputname.txt")
    val input by
    parser.option(ArgType.String, shortName = "in", description = "Input file name").default("inputname.txt")

    parser.parse(args)
    val outputFile = File(output)
    val inputFile = File(input)


    if (!inputFile.exists()) {
        println("File does not exist. Please, check the path.")
        kotlin.system.exitProcess(-1)
        }


    when (type) {
        "-z" -> {
            println("Processing...")
            EncodeParser.create(inputFile, outputFile).encode()
            println("File saved successfully!/nHave a good day!")
        }
        "-u" -> {
            println("Processing...")
            DecodeParser.create(inputFile, inputFile).decode()
            println("File saved successfully!/nHave a good day!")
        }
        else -> {
            println(
                "(-u) and (-z) are the only available arguments./n" +
                        "Choose one of them or do not choose at all"
            )
            kotlin.system.exitProcess(-1)
        }
    }
}


class EncodeParser private constructor (inputFile: File, outputFile: File) {
    private val writer = outputFile.bufferedWriter()
    private val reader = inputFile.bufferedReader()
    private val window = MovingWindow(Array(3) { reader.read().let { if (it != -1) Char(it) else null} }, reader)



    private fun fromIntToString(count: Int, string: String): String {
        var curCount = count
        val localResult = StringBuilder()
        val maxSequenceDif = 113
        val maxSequenceSim = 112
        val maxSequenceCharDif = Char(144)
        val maxSequenceCharSim = Char(255)
        val zeroSequenceCharDif = Char(31)
        val zeroSequenceCharSim = Char(143)
        when {
            count < 0 -> {
                var maxSequenceCounter = 0
                curCount *= -1
                while (curCount > maxSequenceDif) {
                    maxSequenceCounter++
                    localResult.append(maxSequenceCharDif)
                        .append(string.substring(maxSequenceDif * (maxSequenceCounter - 1),
                            maxSequenceDif * maxSequenceCounter))
                    curCount -= maxSequenceDif
                }
                localResult.append(zeroSequenceCharDif + curCount)
                    .append(string.substring(maxSequenceDif * maxSequenceCounter, string.length))
                return localResult.toString()
            }
            count > 1 -> {
                while (curCount > maxSequenceSim) {       // A * 142 = ~A~A~A!A
                    localResult.append(maxSequenceCharSim).append(string)
                    curCount -= maxSequenceSim
                }
                if (curCount > 1) localResult.append(zeroSequenceCharSim + curCount).append(string)
                else localResult.append(zeroSequenceCharDif + 1).append(string)
            }
            else -> throw IllegalArgumentException()
        }
        return localResult.toString()
    }

    private fun countDif() {
        var count = 1
        val cdResult = StringBuilder()
        cdResult.append(window[0])
        while (!window.isOutOfBounds(1) && window[0] != window[1]) {
            if (!window.isOutOfBounds(2) && window[1] == window[2]) break
            count++
            window.move()
            cdResult.append(window[0])
        }
        writer.write(fromIntToString(-count, cdResult.toString()))
    }

    private fun countSim() {
        var count = 1
        while (!window.isOutOfBounds(1) && window[0] == window[1]) {
            count++
            window.move()
        }
        writer.write(fromIntToString(count, window[0].toString()))
    }

    fun encode() {
        while (!window.isOutOfBounds(0)) {
            if (window[0] == window[1]) {
                countSim()
            }
            else countDif()
            window.move()
        }
        writer.close()
        reader.close()
    }

    companion object {
        fun create(inputFile: File, outputFile: File) = EncodeParser(inputFile, outputFile)
    }
}

class DecodeParser private constructor (inputFile: File, outputFile: File) {
    private val writer = outputFile.bufferedWriter()
    private val reader = inputFile.bufferedReader()
    private val window = MovingWindow(Array(2) { reader.read().let { if (it != -1) Char(it) else null} }, reader)


    fun decode() {
        while (!window.isOutOfBounds(1)) {
            if (window[0]!!.code in 32..144) {
                appendDiff()
            }
            else {
                appendSim()
            }
            window.move()
        }
        writer.close()
        reader.close()
    }


    private fun appendDiff() {
        val count = window[0]!!.code - 31
        for (i in 0 until count) {
            window.move()
            writer.write(window[0].toString())
        }
    }
    private fun appendSim() {
        val count = window[0]!!.code - 143
        writer.write(window[1].toString())
        for (i in 0 until count) window.move()
    }


    companion object {
        fun create(inputFile: File, outputFile: File) = DecodeParser(inputFile, outputFile)
    }
}

class MovingWindow (private val array: Array<Char?>,private val reader: BufferedReader) {
    private val lastIndex = array.size - 1

    fun move() {
        val code = reader.read()
        for (i in 0 until lastIndex) array[i] = array[i + 1]
        if (code != -1) array[lastIndex] = Char(code)
        else {
            array[lastIndex] = null
        }
    }

    fun isOutOfBounds(index: Int) = array[index] == null

    operator fun get(index: Int) = array[index]
}
