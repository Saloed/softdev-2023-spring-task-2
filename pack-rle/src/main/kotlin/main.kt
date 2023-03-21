fun main() {
}


class Parser private constructor (private val text: String) {
    private val originalLength = text.length
    private fun encode(): String {
        var index = 0
        var result = ""

        fun fromIntToString(count: Int, string: String): String {
            var curCount = count
            var res = ""
            when {
                count < 0 -> {
                    var i = 1
                    curCount *= -1
                    if (curCount > 113) {
                        while (curCount > 113) {
                            res += Char(144) + string.substring(113 * (i - 1), 113 * i)
                            i++
                            curCount -= 113
                        }
                        res += (' ' + curCount - 1) + string.substring(112 * i)
                    }
                    res += (' ' + curCount - 1) + string
                    return res
                }
                count > 1 -> {
                    while (curCount > 112) {       // A * 142 = ~A~A~A!A
                        res += "${Char(255)}$string"         //
                        curCount -= 112
                    }
                    res += if (curCount > 1) ('N' + curCount) + string
                    else "!$string"
                }
                else -> throw IllegalArgumentException()
            }
            return res
        }
        // chars from !(33 ASCII) to O(79) represent count values from -1 to -47
        // chars from P(80 ASCII) to ~(126) represent count values from 2 to 47
        // 32 - 144 145 - 255

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
        fun create(text: String) = Parser(text)
    }
}

