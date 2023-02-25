package softdevSpringTask

import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.Parameters
import picocli.CommandLine.Option
import java.io.File
import java.io.FileInputStream
import java.io.OutputStream
import java.lang.Integer.min


fun addFileContent(fileToCopy: File, outputFileOutputStream: OutputStream) {
    fileToCopy.inputStream().use { fileInputStream ->
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        var bytes = fileInputStream.read(buffer)
        while (bytes >= 0) {
            outputFileOutputStream.write(buffer, 0, bytes)
            bytes = fileInputStream.read(buffer)
        }
    }
}

fun readLine(inputStream: FileInputStream): String {
    val readChar = { inputStream.read().toChar() }
    var c = readChar()
    var string = ""
    while (c != '\n') {
        string += c
        c = readChar()
    }
    return string
}

@Command(
    name = "tar",
    mixinStandardHelpOptions = true,
    description = [
"""
Puts some files together in one (commonly named "archiving") 
or unarchives them from a file (pulls them out)
"""
    ]
)
class Tar : Runnable {
    @Parameters(paramLabel = "files", description = ["one or more files to archive"])
    lateinit var filePaths: List<String>

    @Option(names = ["-out"], description = ["file where to archive (ignored if -u is specified)"])
    lateinit var outputFile: File

    @Option(names = ["-u"], description = ["file to unarchive"])
    var unarchiveFile: File? = null

    override fun run() {
        if (unarchiveFile != null) {
            val toUnarchive = unarchiveFile!!

            toUnarchive.inputStream().use { unarchiveInputStream ->
                val n = readLine(unarchiveInputStream).toInt()

                repeat(n) {
                    val filePath = readLine(unarchiveInputStream)
                    var remainingFileLength = readLine(unarchiveInputStream).toInt()

                    val file = File(filePath)
                    file.outputStream().use { fileOutputStream ->
                        while (remainingFileLength > 0) {
                            val buffer = ByteArray(min(remainingFileLength, DEFAULT_BUFFER_SIZE))
                            unarchiveInputStream.read(buffer)
                            fileOutputStream.write(buffer, 0, min(DEFAULT_BUFFER_SIZE, remainingFileLength))

                            remainingFileLength -= DEFAULT_BUFFER_SIZE
                        }
                    }
                    unarchiveInputStream.skip(1)
                }
            }
        } else {
            val outputFile = outputFile

            /**
             * Output file content:
             * - quantity of files,
             * - for every file:
             *    - filename in UTF-8 characters,
             *    - file content length in bytes,
             *    - file content in bytes
             */

            outputFile.outputStream().use { outputStream ->
                outputStream.write("${filePaths.size}\n".toByteArray())

                for (i in filePaths.indices) {
                    val file = File(filePaths[i])
                    outputStream.write("${filePaths[i]}\n".toByteArray())

                    outputStream.write("${file.length()}\n".toByteArray())

                    addFileContent(file, outputStream)
                    outputStream.write("\n".toByteArray())
                }
            }
        }
    }

}


fun main(args: Array<String>) {
    CommandLine(Tar()).execute(*args)
}