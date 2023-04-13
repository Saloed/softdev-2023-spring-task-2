import java.io.File
import kotlinx.cli.*
import java.io.FileInputStream


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
    private var textArray = Array(3) { reader.read().let { if (it != -1) Char(it) else null} }
    private var textIsRead = false


    private fun fromIntToString(count: Int, string: String): String {
        var curCount = count
        val localResult = StringBuilder()
        val maxSequenceDif = 113
        val maxSequenceSim = 112
        when {
            count < 0 -> {
                var maxSequenceCounter = 0
                curCount *= -1
                while (curCount > maxSequenceDif) {
                    maxSequenceCounter++
                    localResult.append(Char(144))
                        .append(string.substring(113 * (maxSequenceCounter - 1), 113 * maxSequenceCounter))
                    curCount -= maxSequenceDif
                }
                localResult.append(' ' + curCount - 1).append(string.substring(113 * maxSequenceCounter, string.length))
                return localResult.toString()
            }
            count > 1 -> {
                while (curCount > maxSequenceSim) {       // A * 142 = ~A~A~A!A
                    localResult.append("${Char(255)}$string")       //
                    curCount -= maxSequenceSim
                }
                if (curCount > 1) localResult.append(Char(143) + curCount).append(string)
                else localResult.append(Char(32)).append(string)
            }
            else -> throw IllegalArgumentException()
        }
        return localResult.toString()
    }

    private fun countDif() {
        var count = 1
        var cdResult = textArray[0]!!.toString()
        while (!isOutOfBounds(1) && textArray[0] != textArray[1]) {
            if (!isOutOfBounds(2) && textArray[1] == textArray[2]) break
            count++
            moveWindow()
            cdResult += textArray[0]!!
        }
        writer.write(fromIntToString(-count, cdResult))
    }

    private fun countSim() {
        var count = 1
        while (!isOutOfBounds(1) && textArray[0] == textArray[1]) {
            count++
            moveWindow()
        }
        writer.write(fromIntToString(count, textArray[0].toString()))
    }

    fun encode() {
        while (!isOutOfBounds(0)) {
            if (textArray[0] == textArray[1]) {
                countSim()
            }
            else countDif()
            moveWindow()
        }
        writer.close()
        reader.close()
    }

    private fun moveWindow() {
        if (!(textIsRead && isOutOfBounds(0))) {
            val status = reader.read()
                textArray[0] = textArray[1]
                textArray[1] = textArray[2]
            if (status != -1) textArray[2] = Char(status)
            else {
                textArray[2] = null
                textIsRead = true
            }
        }
    }

    private fun isOutOfBounds(localIndex: Int) = textIsRead && textArray[localIndex] == null


    companion object {
        fun create(inputFile: File, outputFile: File) = EncodeParser(inputFile, outputFile)
    }
}

class DecodeParser private constructor (inputFile: File, outputFile: File) {
    private val inputStream = FileInputStream(inputFile)
    private val writer = outputFile.bufferedWriter()
    private var byteArray = ByteArray(2048)
    private var text = ""
    private var index = 0

    fun decode() {
        read()
        while (true) {
            if (text[index].code in 32..144) {
                appendDiff(text[index].code)
            }
            else {
                appendSim(text[index].code)
            }
            if (index >= text.length && read() == -1) break
        }
        inputStream.close()
        writer.close()
    }

    private fun read(): Int {
        val res = inputStream.read(byteArray)
        text = if (res != -1) String(byteArray, 0, res) else ""
        return res
    }

    private fun appendDiff(int: Int) {
        if (index + int - 30 <= text.length) {
            writer.write(text.substring(index + 1, index + int - 30))
            index += int - 30
        }
        else {
            writer.write(text.substring(index + 1, index + int - 30))
            index = index + int - 30 - text.length
            read()
            writer.write(text.substring(index + 1, index + int - 30))
        }
    }
    private fun appendSim(int: Int) {
        if (index + 1 < text.length) {
            writer.write(text.substring(index + 1, index + int - 30))
            index += 2
        }
        else {
            read()
            writer.write(text.substring(index + 1, index + int - 30))
            index = 1
        }
    }


    companion object {
        fun create(inputFile: File, outputFile: File) = DecodeParser(inputFile, outputFile)
    }
}
