import java.nio.file.Path
import java.io.File
import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.FileVisitor
import java.nio.file.attribute.BasicFileAttributes
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.name
import kotlin.io.path.walk
import java.nio.file.Files

class RiftWalker(private val r: Boolean, d: String, private val name: String) {
    private val directory: Path = File(d).toPath()

    class Finder : FileVisitor<Path> {
        val list = mutableListOf<Path>()
        override fun preVisitDirectory(dir: Path?, attrs: BasicFileAttributes?): FileVisitResult {
            return FileVisitResult.CONTINUE
        }

        override fun visitFile(file: Path?, attrs: BasicFileAttributes?): FileVisitResult {
            if (attrs != null) {
                if (file != null && attrs.isRegularFile) {
                    list.add(file)
                }
            }
            return FileVisitResult.CONTINUE
        }

        override fun visitFileFailed(file: Path?, exc: IOException?): FileVisitResult {
            return FileVisitResult.CONTINUE
        }

        override fun postVisitDirectory(dir: Path?, exc: IOException?): FileVisitResult {
            return FileVisitResult.TERMINATE
        }
    }

    @OptIn(ExperimentalPathApi::class)
    fun find(): List<Path> {
        return if (r) directory.walk().filter { it.name.endsWith(name) }.toList()
        else {
            val finder = Finder()
            Files.walkFileTree(directory, mutableSetOf(), 1, finder)
            finder.list.filter { it.name.endsWith(name) }
        }
    }
}

fun main(args: Array<String>) {
    val parser = Parser(args)
    try {
        println(RiftWalker(parser.r, parser.d, parser.name).find())
    }
    catch (e: IllegalArgumentException){
        println("You have written strange arguments. Please, check the example and try again!")
        print("Example: [-r] [-d directory] filename.txt ([-r] is optional)")
    }
    catch (e: IllegalStateException){
        println("You put too many arguments for this command. Please, remove extra and run again!")
    }
}