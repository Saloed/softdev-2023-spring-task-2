package softdevSpringTask

/* import org.junit.jupiter.api.Test */

//import org.junit.api.Assert.*
import picocli.CommandLine
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
        crypto("5676".toByteArray(), "files/text1.txt", "files/temp.txt")
        crypto("5676".toByteArray(), "files/temp.txt", "files/decryption.txt")
        isEqual("files/text1.txt", "files/decryption.txt")
    }
    @Test
    fun test2() {
        val cmd = CommandLine(XOR())
        try {
            cmd.parseArgs("files/temp.txt", "-o files/decryption.txt")
        } catch (ex: CommandLine.MissingParameterException) {
            assert("Error: Missing required argument (specify one of these): (-c=<key> | -d=<dkey>)" == ex.message)
        }
    }

    @Test
    fun test3() {
        val cmd = CommandLine(XOR())
        try {
            cmd.parseArgs("-c 5676", "-d 5676","files/temp.txt", "-o files/decryption.txt")
        } catch (ex: CommandLine.MutuallyExclusiveArgsException) {
            assert("Error: -c=<key>, -d=<dkey> are mutually exclusive (specify only one)" == ex.message)
        }
    }
    @Test
    fun test4() {
        val cmd = CommandLine(XOR())
        try {
            cmd.parseArgs("-c", "files/temp.txt", "-o files/decryption.txt")
        } catch (ex: CommandLine.MissingParameterException) {
            assert("Missing required parameter: '<inputName>'" == ex.message)
        }
    }
}