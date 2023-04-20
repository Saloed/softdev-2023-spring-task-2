import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.File

class Tests{
    @Test
    fun parser(){
        val randomString= arrayOf("aaaaa")
        val strWithoutDirectory = arrayOf("[-r]", "[-d ]", "filename")
        val strWithoutFilename = arrayOf("[-r]", "[-d C:\\Users\\Egor\\IdeaProjects\\MyTask2]")
        val normalString = arrayOf("[-r]","[-d C:\\Users\\Egor\\IdeaProjects\\MyTask2]", "filename")
        val a = Parser(normalString)
        assertThrows<Throwable> {Parser(randomString).d}
        assertThrows<Throwable> {Parser(strWithoutDirectory).d}
        assertThrows<Throwable> {Parser(strWithoutFilename).name}
        assertEquals(true, a.r)
        assertEquals("C:\\Users\\Egor\\IdeaProjects\\MyTask2", a.d)
        assertEquals("filename", a.name)
    }

    @Test
    fun find(){
        val arg = arrayOf("[-d C:\\Users\\Egor\\IdeaProjects\\MyTask2\\src\\main\\Test]", "filename")
        val argWithR = arrayOf("[-r]", "[-d C:\\Users\\Egor\\IdeaProjects\\MyTask2\\src\\main]", "filename")
        assertEquals(listOf(File("C:\\Users\\Egor\\IdeaProjects\\MyTask2\\src\\main\\Test\\filename").toPath()),
            RiftWalker(Parser(arg).r,Parser(arg).d,Parser(arg).name).find())
        assertEquals(
            listOf(File("C:\\Users\\Egor\\IdeaProjects\\MyTask2\\src\\main\\Test\\filename").toPath(),
        File("C:\\Users\\Egor\\IdeaProjects\\MyTask2\\src\\main\\Test\\1\\filename").toPath()).sorted(),
            RiftWalker(Parser(argWithR).r,Parser(argWithR).d,Parser(argWithR).name).find().sorted())
    }
}