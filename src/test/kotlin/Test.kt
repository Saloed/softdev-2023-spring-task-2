import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.useLines
import kotlin.test.assertTrue

private fun isEqual(firstFile: Path, secondFile: Path): Boolean {
    if (Files.size(firstFile) != Files.size(secondFile)) {
        return false
    }
    val first = Files.readString(firstFile)
    val second = Files.readString(secondFile)
    return first!!.contentEquals(second)
}

class Test {

    @Test // даны необходимые файлы, ошибок в передаваемых аргументах нет
    fun testMain() {
        val argsExamples =
            listOf(
                arrayOf("-c", "-o", "src/test/testFiles/output.txt", "src/test/testFiles/input.txt", "2-5"),
                arrayOf("-w", "-o", "src/test/testFiles/output.txt", "src/test/testFiles/input.txt", "-2"),
            )
        val results =
            listOf(
                File("src/test/testFiles/result1.txt"),
                File("src/test/testFiles/result2.txt"),

            )

        for (example in argsExamples.indices) {
            main(argsExamples[example])

            results[example].useLines { System.err.println(it) }
            File("src/test/testFiles/output.txt").useLines { System.err.println(it) }

            assertTrue(
                isEqual(
                    results[example].toPath(),
                    File("src/test/testFiles/output.txt").toPath()
                )
            )
        }
    }
}