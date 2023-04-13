
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File


class Tests {

    private val outputFile = File("output")

    private fun createFile(string: String): File {
        val file = File("foo")
        file.bufferedWriter().use {
            it.write(string)
        }
        return file
    }

    private fun readFrom(file: File): String {
        file.bufferedReader().use { return it.readLines().joinToString("/n") }
    }

    @Test
    fun encode() {


        EncodeParser.create(createFile("AAAABABABBBBBaba"), outputFile).encode()
        assertEquals("${Char(147)}A${Char(35)}BABA${Char(148)}B${Char(34)}aba",
            readFrom(outputFile))

        EncodeParser.create(createFile("A".repeat(112)), outputFile).encode()
        assertEquals("${Char(255)}A",
            readFrom(outputFile))

        EncodeParser.create(createFile("A".repeat(113)), outputFile).encode()
        assertEquals("${Char(255)}A${Char(32)}A",
            readFrom(outputFile))

        EncodeParser.create(createFile("AB".repeat(56)), outputFile).encode()
        assertEquals("${Char(143)}${"AB".repeat(56)}",
            readFrom(outputFile))

        EncodeParser.create(createFile("AB".repeat(113)), outputFile).encode()
        assertEquals("${Char(144)}${"AB".repeat(56)}A${Char(144)}B${"AB".repeat(56)}",
            readFrom(outputFile))
    }

    @Test
    fun decode() {
        DecodeParser.create(createFile("${Char(144)}${"AB".repeat(56)}A" +
                "${Char(144)}B${"AB".repeat(56)}"), outputFile).decode()
        assertEquals("AB".repeat(113), readFrom(outputFile))
    }
}