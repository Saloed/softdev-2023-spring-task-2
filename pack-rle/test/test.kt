
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileInputStream

class Tests {

    @Test
    fun encode() {
        var text = EncodeParser.create("AAAABABABBBBBaba")
        assertEquals("${Char(147)}A${Char(35)}BABA${Char(148)}B${Char(34)}aba", text.encoded)
        println(text.sizeRed)
        text = EncodeParser.create("A".repeat(112))
        assertEquals("${Char(255)}A", text.encoded)
        println(text.sizeRed)
        text = EncodeParser.create("A".repeat(113))
        assertEquals("${Char(255)}A${Char(32)}A", text.encoded)
        println(text.sizeRed)
        text = EncodeParser.create("AB".repeat(56))
        assertEquals("${Char(143)}${"AB".repeat(56)}", text.encoded)
        text = EncodeParser.create("AB".repeat(113))
        assertEquals("${Char(144)}${"AB".repeat(56)}A${Char(144)}B${"AB".repeat(56)}", text.encoded)
    }

    /*
    @Test
    fun decode() {
        var text = DecodeParser.create("${Char(147)}A${Char(35)}BABA${Char(148)}B${Char(34)}aba")
        assertEquals("AAAABABABBBBBaba", text.decoded)
        text = DecodeParser.create("${Char(255)}A")
        assertEquals("A".repeat(112), text.decoded)
        text = DecodeParser.create("${Char(255)}A${Char(32)}A")
        assertEquals("A".repeat(113), text.decoded)
        text = DecodeParser.create("${Char(143)}${"AB".repeat(56)}")
        assertEquals("AB".repeat(56), text.decoded)
        text = DecodeParser.create("${Char(144)}${"AB".repeat(56)}A${Char(144)}B${"AB".repeat(56)}")
        assertEquals("AB".repeat(113), text.decoded)
        }
        */
    @Test
    fun decode() {

        val text = DecodeParser.create(FileInputStream(createFile("${Char(144)}" +
                "${"AB".repeat(56)}A" +
                "${Char(144)}B${"AB".repeat(56)}")))
        assertEquals("AB".repeat(113), text.decoded)
    }

    private fun createFile(string: String):File {
        val file = File("foo")
        file.bufferedWriter().use {
            it.write(string)
        }
        return file
    }

}