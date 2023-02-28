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
    fun fullCheck() {
        var file1 = fileOf("file1.txt", "file1")
        var file2 = fileOf("file2.txt", "file2")
        var file3 = fileOf("file3.txt", "file3$\n")

        var args = arrayOf(
            "file1.txt",
            "file2.txt",
            "file3.txt",
            "-out",
            "out.txt"
        )

        main(args)

        file1.delete()
        file2.delete()
        file3.delete()

        args = arrayOf(
            "-u",
            "out.txt",
        )

        main(args)

        File("out.txt").delete()

        file1 = File("file1.txt")
        file2 = File("file2.txt")
        file3 = File("file3.txt")

        assertEquals("file1", file1.bufferedReader().use { it.readText() })
        assertEquals("file2", file2.bufferedReader().use { it.readText() })
        assertEquals("file3\$\n", file3.bufferedReader().use { it.readText() })

        file1.delete()
        file2.delete()
        file3.delete()
    }

    @Test
    fun u() {
        val out = fileOf(
            "out.txt",
            """
            3
            9
            file1.txt5
            file19
            file2.txt5
            file29
            file3.txt6
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

        val expected = """
            3
            9
            file1.txt5
            file19
            file2.txt5
            file29
            file3.txt7
            file3$
            
            """.trimIndent()

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
        file1.delete()
        file2.delete()
        file3.delete()
        out.delete()

        assertEquals(expected, actual)
    }
}
