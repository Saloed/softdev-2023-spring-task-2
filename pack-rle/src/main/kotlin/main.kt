import java.io.File
import java.lang.Exception

fun main(args: Array<String>) {
    if (!File(args[1]).exists()) throw Exception("File does not exist. Please, check the path.")
    val text = File(args[1]).readLines().toString()
    when (args.first()) {
        "-z" -> {
            println("Processing...")
            File(args.last()).bufferedWriter().use { EncodeParser.create(text).encoded }
            println("${args.last()} saved successfully!/nHave a good day!")
        }
        "-u" -> {
            println("Processing...")
            File(args.last()).bufferedWriter().use { DecodeParser.create(text).decoded }
            println("${args.last()} saved successfully!/nHave a good day!")
        }
        else -> throw IllegalArgumentException("(-u) and (-z) are the only available arguments./n" +
                "Choose one of them or do not choose at all")
    }
}


class EncodeParser private constructor (private val text: String) {
    private val originalLength = text.length
    private fun encode(): String {
        var index = 0
        var result = ""

        fun fromIntToString(count: Int, string: String): String {
            var curCount = count
            var res = ""
            when {
                count < 0 -> {
                    var i = 0
                    curCount *= -1
                    while (curCount > 113) {
                        i++
                        res += Char(144) + string.substring(113 * (i - 1), 113 * i)
                        curCount -= 113
                    }
                    res += (' ' + curCount - 1) + string.substring(113 * i, string.length)
                    return res
                }
                count > 1 -> {
                    while (curCount > 112) {       // A * 142 = ~A~A~A!A
                        res += "${Char(255)}$string"         //
                        curCount -= 112
                    }
                    res += if (curCount > 1) (Char(143) + curCount) + string
                    else Char(32) + string
                }
                else -> throw IllegalArgumentException()
            }
            return res
        }

        fun countDif() {
            var count = 1
            var cdResult = "${text[index]}"
            while (index < text.length - 1 && text[index] != text[index + 1]) {
                if (index < originalLength - 2 && text[index + 1] == text[index + 2]) break
                count++
                index++
                cdResult += text[index]
            }
            result += fromIntToString(-count, cdResult)
        }

        fun countSim() {
            var count = 1
            while (index < text.length - 1 && text[index] == text[index + 1]) {
                count++
                index++
            }
            result += fromIntToString(count, text[index].toString())
        }
        while (index < originalLength) {
            if (text[index] == text[index + 1]) {
                countSim()
            }
            else countDif()
            index++
        }
        return result
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