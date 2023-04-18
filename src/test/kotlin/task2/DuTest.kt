package task2

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test
import picocli.CommandLine

class DuTest {
    private val cmd = CommandLine(Du())



    @Test
    fun test1() {
        val args = arrayOf("files/otherfiles/file4.mp3")
        cmd.execute(*args)
        val result = cmd.getExecutionResult<List<String>>()
        assertArrayEquals(arrayOf("8266188 B"), result.toTypedArray())
    }

    @Test
    fun test2() {
        val args = arrayOf("-ch", "files/file1.txt", "files/otherfiles")
        cmd.execute(*args)
        val result = cmd.getExecutionResult<List<String>>()
        assertArrayEquals(
            arrayOf("19.1142578125 KB", "8.12485408782959 MB", "8.14352035522461 MB"),
            result.toTypedArray()
        )
    }

    @Test
    fun test3() {
        val args = arrayOf("--si", "files/file2.docx", "files/otherfiles/anotherfiles")
        cmd.execute(*args)
        val result = cmd.getExecutionResult<List<String>>()
        assertArrayEquals(arrayOf("17576 B", "253339 B"), result.toTypedArray())
    }
}