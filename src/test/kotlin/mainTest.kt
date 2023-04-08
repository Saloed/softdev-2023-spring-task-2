package org.example.softdev2023springtask2
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.assertThrows
import java.io.File
import java.util.function.Predicate.isEqual
import kotlin.test.*

class MainTest {
    @Test
    fun test1() {
        val args = arrayOf(
            "-c5",
            "files/input.txt",
            "-ofiles/output.txt"
        )
        main(args)
        val file1 = File("src/test/kotlin/TrueOutputFiles/TrueOutputFile1.txt").readText()
        val file2 = File("files/output.txt").readText()
        assertEquals(file1, file2)
    }
    @Test
    fun test2() {
        val args = arrayOf(
            "-n2",
            "files/input.txt", "files/input2.txt",
            "-ofiles/output.txt"
        )
        main(args)
        val file1 = File("src/test/kotlin/TrueOutputFiles/TrueOutputFile2.txt").readText()
        val file2 = File("files/output.txt").readText()
        assertEquals(file1, file2)
    }
    @Test
    fun test3() {
        val args = arrayOf(
            "files/input.txt", "files/input2.txt",
            "-ofiles/output.txt"
        )
        main(args)
        val file1 = File("src/test/kotlin/TrueOutputFiles/TrueOutputFile3.txt").readText()
        val file2 = File("files/output.txt").readText()
        assertEquals(file1, file2)
    }
}