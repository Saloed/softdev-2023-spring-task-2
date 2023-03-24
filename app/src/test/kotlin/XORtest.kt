package softdevSpringTask

/* import org.junit.jupiter.api.Test */

//import org.junit.api.Assert.*
import java.io.File
import kotlin.test.*

class XorTest {
    private fun isEqual(firstFile: String, secondFile: String) {
        val first = File(firstFile).readText()
        val second = File(secondFile).readText()
        assertEquals(first, second)
    }

    @Test
    fun test1() {
        encryption("125F".toByteArray(),"files/text1.txt","files/temp.txt")
        decryption("125F".toByteArray(),"files/temp.txt","files/decryption.txt")
        isEqual("files/text1.txt", "files/decryption.txt")
    }

}