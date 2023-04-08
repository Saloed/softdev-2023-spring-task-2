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
    val byteArray = ByteArray(2048)


    if (!inputFile.exists()) {
        println("File does not exist. Please, check the path.")
        kotlin.system.exitProcess(-1)
        }


    when (type) {
        "-z" -> {
            println("Processing...")
            FileInputStream(inputFile).use { reader ->
                outputFile.bufferedWriter().use { writer ->
                    while (reader.read(byteArray) != -1) {
                        writer.write(EncodeParser.create(String(byteArray)).encoded)
                    }
                }
            }
            println("File saved successfully!/nHave a good day!")
        }
        "-u" -> {
            println("Processing...")
            FileInputStream(inputFile).use { reader ->
                outputFile.bufferedWriter().use { writer ->
                    writer.write(DecodeParser.create(FileInputStream(inputFile)).decoded)
                }
            }
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


class EncodeParser private constructor (private val text: String) {
    private val originalLength = text.length
    private var index = 0
    private var result = StringBuilder()

    private fun fromIntToString(count: Int, string: String): String {
        var curCount = count
        val localResult = StringBuilder()
        when {
            count < 0 -> {
                var maxSequenceCounter = 0
                curCount *= -1
                while (curCount > 113) {
                    maxSequenceCounter++
                    localResult.append(Char(144))
                        .append(string.substring(113 * (maxSequenceCounter - 1), 113 * maxSequenceCounter))
                    curCount -= 113
                }
                localResult.append(' ' + curCount - 1).append(string.substring(113 * maxSequenceCounter, string.length))
                return localResult.toString()
            }
            count > 1 -> {
                while (curCount > 112) {       // A * 142 = ~A~A~A!A
                    localResult.append("${Char(255)}$string")       //
                    curCount -= 112
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
        var cdResult = "${text[index]}"
        while (index < text.length - 1 && text[index] != text[index + 1]) {
            if (index < originalLength - 2 && text[index + 1] == text[index + 2]) break
            count++
            index++
            cdResult += text[index]
        }
        result.append(fromIntToString(-count, cdResult))
    }

    private fun countSim() {
        var count = 1
        while (index < text.length - 1 && text[index] == text[index + 1]) {
            count++
            index++
        }
        result.append(fromIntToString(count, text[index].toString()))
    }

    private fun encode(): String {
        while (index < originalLength) {
            if (text[index] == text[index + 1]) {
                countSim()
            }
            else countDif()
            index++
        }
        return result.toString()
    }

    val encoded = encode()
    val sizeRed = originalLength.toDouble() / encoded.length.toDouble()

    companion object {
        fun create(text: String) = EncodeParser(text)
    }
}

class DecodeParser private constructor (private val FileIS: FileInputStream) {

    private var byteArray = ByteArray(2048)
    private val result = StringBuilder()
    private var text = ""
    private var index = 0

    private fun decode(): String {
        read()
        while (true) {
            if (text[index].code in 32..144) {
                appendDiff(text[index].code)
            }
            else {
                println(text[index].code)
                appendSim(text[index].code)
            }
            if (text[index].code == 0 || (index >= text.length && read() == -1)) break
        }
        FileIS.close()
        return result.toString()
    }

    private fun read(): Int {
        val res = FileIS.read(byteArray)
        text = String(byteArray)
        return res
    }

    private fun appendDiff(int: Int) {
        if (index + int - 30 <= text.length) {
            result.append(text.substring(index + 1, index + int - 30))
            index += int - 30
        }
        else {
            result.append(text.substring(index + 1))
            index = index + int - 30 - text.length
            read()
            result.append(text.substring(0, index))
        }
    }
    private fun appendSim(int: Int) {
        if (index + 1 < text.length) {
            result.append(text[index + 1].toString().repeat(int - 143))
            index += 2
        }
        else {
            read()
            result.append(text[0].toString().repeat(int - 143))
            index = 1
        }
    }


    val decoded = decode()
    companion object {
        fun create(FileIS: FileInputStream) = DecodeParser(FileIS)
    }
}