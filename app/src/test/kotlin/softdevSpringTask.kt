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
        XOR("125F").encryption("C:/Users/79509/IdeaProjects/softdev-2023-spring-task-2/files/text1.txt",
            "C:/Users/79509/IdeaProjects/softdev-2023-spring-task-2/files/temp.txt")
        XOR("125F").decryption("C:/Users/79509/IdeaProjects/softdev-2023-spring-task-2/files/temp.txt",
            "C:/Users/79509/IdeaProjects/softdev-2023-spring-task-2/files/decryption.txt")
        isEqual("C:/Users/79509/IdeaProjects/softdev-2023-spring-task-2/files/text1.txt",
            "C:/Users/79509/IdeaProjects/softdev-2023-spring-task-2/files/decryption.txt")
    }

}