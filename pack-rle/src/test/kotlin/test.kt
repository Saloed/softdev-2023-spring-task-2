
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Tests {
    /*@Test
    fun countDif() {
        assertEquals(Pair("-7ABACABA", 6), Parser.create("ABACABA").countDif(0))
    }
    @Test
    fun countSim() {
        assertEquals(Pair("5A", 6), Parser.create("ABAAAAAb").countSim(2))
    }*/

    @Test
    fun encode() {
        val text = Parser.create("AAAABABABBBBBaba")
        println(text.encoded)
        println(text.sizeRed)
    }

    @Test
    fun general() {
        println(Char(30000).toString()[0].code)
    }
}