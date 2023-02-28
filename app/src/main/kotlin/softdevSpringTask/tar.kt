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
    var c = inputStream.read().toChar()
    var string = ""
    while (c != '\n') {
        string += c
        c = inputStream.read().toChar()
    }
    return string
}

fun unarchive(toUnarchive: File) {
    toUnarchive.inputStream().use { unarchiveInputStream ->
        val n = readLine(unarchiveInputStream).toInt()

        repeat(n) {
            val filePathLen = readLine(unarchiveInputStream).toInt()
            var filePath = ""
            repeat (filePathLen) {
                filePath += unarchiveInputStream.read().toChar()
            }
            var remainingFileLength = readLine(unarchiveInputStream).toInt()

            val file = File(filePath)
            file.outputStream().use { fileOutputStream ->
                var buffer: ByteArray
                while (remainingFileLength > 0) {
                    buffer = ByteArray(min(remainingFileLength, DEFAULT_BUFFER_SIZE))
                    unarchiveInputStream.read(buffer)
                    fileOutputStream.write(buffer, 0, min(DEFAULT_BUFFER_SIZE, remainingFileLength))

                    remainingFileLength -= DEFAULT_BUFFER_SIZE
                }
            }
        }
    }
}

fun archive(outputFile: File, filePaths: List<String>) {
    /**
     * Output file content:
     * - quantity of files and a line break,
     * - for every file:
     *      - filename length in UTF-8 characters and a line break,
     *      - filename in UTF-8 characters,
     *      - file content length in bytes and a line break,
     *      - file content in bytes
     */

    outputFile.outputStream().use { outputStream ->
        outputStream.write("${filePaths.size}\n".toByteArray())

        for (i in filePaths.indices) {
            val file = File(filePaths[i])
            outputStream.write("${filePaths[i].length}\n".toByteArray())
            outputStream.write(filePaths[i].toByteArray())

            outputStream.write("${file.length()}\n".toByteArray())

            addFileContent(file, outputStream)
        }
    }
}

@Command(
    name = "tar",
    mixinStandardHelpOptions = true,
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
            unarchive(unarchiveFile!!)
        } else {
            archive(outputFile, filePaths)
        }
    }
}


fun main(args: Array<String>) {
    CommandLine(Tar()).execute(*args)
}