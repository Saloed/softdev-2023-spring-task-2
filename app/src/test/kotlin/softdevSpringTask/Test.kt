package softdevSpringTask

import java.io.File
import kotlin.test.*

private fun fileOf(name: String, content: String): File {
    val file = File(name)
    file.createNewFile()
    file.bufferedWriter().use { it.write(content) }
    return file
}

class Tests {
    @Test
    fun u() {
        val out = fileOf(
            "out.txt",
            """
            3
            file1.txt
            5
            file1
            file2.txt
            5
            file2
            file3.txt
            6
            file3
            
            
        """.trimIndent()
        )
        val args = arrayOf(
            "-u",
            "out.txt",
        )

        main(args)
        out.delete()

        val file1 = File("file1.txt")
        val file2 = File("file2.txt")
        val file3 = File("file3.txt")

        var expected = "file1"
        var actual = file1.bufferedReader().use { it.readText() }
        file1.delete()
        assertEquals(expected, actual)

        expected = "file2"
        actual = file2.bufferedReader().use { it.readText() }
        file2.delete()
        assertEquals(expected, actual)

        expected = "file3\n"
        actual = file3.bufferedReader().use { it.readText() }
        file3.delete()
        assertEquals(expected, actual)
    }

    @Test
    fun tar() {
        val file1 = fileOf("file1.txt", "file1")
        val file2 = fileOf("file2.txt", "file2")
        val file3 = fileOf("file3.txt", "file3$\n")
        val args = arrayOf(
            "file1.txt",
            "file2.txt",
            "file3.txt",
            "-out",
            "out.txt"
        )
        main(args)
        val out = File("out.txt")
        val actual = out.bufferedReader().use { it.readText() }

        val expected = """
            3
            file1.txt
            5
            file1
            file2.txt
            5
            file2
            file3.txt
            7
            file3$
            
            
        """.trimIndent()
        file1.delete()
        file2.delete()
        file3.delete()

        assertEquals(expected, actual)
    }
}
