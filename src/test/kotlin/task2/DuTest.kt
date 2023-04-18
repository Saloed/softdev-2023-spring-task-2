package task2

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test
import picocli.CommandLine

class DuTest {
    private val cmd = CommandLine(Du())



    @Test
    fun test1() {
        val args = arrayOf("files")
        cmd.execute(*args)
        val result = cmd.getExecutionResult<List<String>>()
        System.err.println(result.toTypedArray())
        System.err.println(arrayOf("339952 B"))
        assertArrayEquals(arrayOf("339952 B"), result.toTypedArray())
    }
//
//    @Test
//    fun test2() {
//        val args = arrayOf("-ch", "files/file1.txt", "files/otherfiles")
//        cmd.execute(*args)
//        val result = cmd.getExecutionResult<List<String>>()
//        Assert.assertArrayEquals(
//            arrayOf("17.123046875 KB", "284.560546875 KB", "301.68359375 KB"),
//            result.toTypedArray()
//        )
//    }
//
//    @Test
//    fun test3() {
//        val args = arrayOf("--si", "files/file2.txt", "files/otherfiles/anotherfiles")
//        cmd.execute(*args)
//        val result = cmd.getExecutionResult<List<String>>()
//        Assert.assertArrayEquals(arrayOf("12350 B", "253339 B"), result.toTypedArray())
//    }
}