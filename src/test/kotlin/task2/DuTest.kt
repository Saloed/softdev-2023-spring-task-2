package task2

import org.junit.Assert
import org.junit.Test
import picocli.CommandLine

class DuTest {
    private val cmd = CommandLine(Du())

    @Test
    fun test1() {
        val args = arrayOf("-h", "files/file1.txt")
        cmd.execute(*args)
        val result = cmd.getExecutionResult<List<String>>()
        System.err.println(arrayOf("17,123046875 KB"))
        System.err.println(result.toTypedArray())
        Assert.assertArrayEquals(arrayOf("17,123046875 KB"), result.toTypedArray())
    }

    @Test
    fun test2() {
        val args = arrayOf("-ch", "files/file1.txt", "files/otherFiles")
        cmd.execute(*args)
        val result = cmd.getExecutionResult<List<String>>()
        println("ERROR")
        System.err.println(arrayOf("17,123046875 KB", "284,560546875 KB", "301,68359375 KB"))
        System.err.println(result.toTypedArray())
        Assert.assertArrayEquals(
            arrayOf("17,123046875 KB", "284,560546875 KB", "301,68359375 KB"),
            result.toTypedArray()
        )
    }

    @Test
    fun test3() {
        val args = arrayOf("--si", "files/file2.txt", "files/otherFiles/anotherFiles")
        cmd.execute(*args)
        val result = cmd.getExecutionResult<List<String>>()
        System.err.println(arrayOf("12350 B", "253339 B"))
        System.err.println(result.toTypedArray())
        Assert.assertArrayEquals(arrayOf("12350 B", "253339 B"), result.toTypedArray())
    }
}