import java.io.File
import kotlinx.cli.*


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
            outputFile.bufferedWriter().use {
                for (line in inputFile.readLines()) it.write(EncodeParser.create(line).encoded)
            }
            println("File saved successfully!/nHave a good day!")
        }
        "-u" -> {
            println("Processing...")
            outputFile.bufferedWriter().use {
                for (line in inputFile.readLines()) it.write(DecodeParser.create(line).decoded)
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
                    localResult.append(Char(144)).append(string.substring(113 * (maxSequenceCounter - 1), 113 * maxSequenceCounter))
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

class DecodeParser private constructor (private val text: String) {
    private fun decode(): String {
        var index = 0
        var result = ""
        while (index < text.length) {
            if (text[index].code in 32..144) {
                result += text.substring(index + 1, index + text[index].code - 30)
                index += text[index].code - 30
            }
            else {
                result += text[index + 1].toString().repeat(text[index].code - 143)
                index += 2
            }
        }
        return result
    }

    val decoded = decode()
    companion object {
        fun create(text: String) = DecodeParser(text)
    }
}