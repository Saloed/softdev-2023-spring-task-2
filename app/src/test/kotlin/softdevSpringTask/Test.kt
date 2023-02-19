package softdevSpringTask

import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class Test {
    @Test
    fun u() {
        val args = arrayOf(
            "-u",
            "out.txt",
        )
        var expected = File("C:\\Users\\Plezha\\IdeaProjects\\softdev-2023-spring-task-2\\app\\src\\test\\kotlin\\softdevSpringTask\\file1.txt").inputStream().readAllBytes()
        var actual = File("file1.txt").inputStream().readAllBytes()
        assertEquals(expected.size, actual.size)
        for (i in expected.indices) assertEquals(expected[i], actual[i])

        expected = File("C:\\Users\\Plezha\\IdeaProjects\\softdev-2023-spring-task-2\\app\\src\\test\\kotlin\\softdevSpringTask\\file2.txt").inputStream().readAllBytes()
        actual = File("file2.txt").inputStream().readAllBytes()
        assertEquals(expected.size, actual.size)
        for (i in expected.indices) assertEquals(expected[i], actual[i])

        expected = File("C:\\Users\\Plezha\\IdeaProjects\\softdev-2023-spring-task-2\\app\\src\\test\\kotlin\\softdevSpringTask\\file3.txt").inputStream().readAllBytes()
        actual = File("file3.txt").inputStream().readAllBytes()
        assertEquals(expected.size, actual.size)
        for (i in expected.indices) assertEquals(expected[i], actual[i])
    }

    @Test
    fun out() {
        val args = arrayOf(
            "C:\\Users\\Plezha\\IdeaProjects\\softdev-2023-spring-task-2\\app\\src\\test\\kotlin\\softdevSpringTask\\file1.txt",
            "C:\\Users\\Plezha\\IdeaProjects\\softdev-2023-spring-task-2\\app\\src\\test\\kotlin\\softdevSpringTask\\file2.txt",
            "C:\\Users\\Plezha\\IdeaProjects\\softdev-2023-spring-task-2\\app\\src\\test\\kotlin\\softdevSpringTask\\file3.txt",
            "-out",
            "out.txt"
        )
        main(args)
        val expected = File("expectedOut.txt").inputStream().readAllBytes()
        val actual = File("out.txt").inputStream().readAllBytes()

        assertEquals(expected.size, actual.size)
        for (i in expected.indices) assertEquals(expected[i], actual[i])
    }
}
