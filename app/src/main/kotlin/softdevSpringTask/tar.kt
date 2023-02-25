package softdevSpringTask

import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.Parameters
import picocli.CommandLine.Option
import java.io.File
import java.io.FileInputStream
import java.io.OutputStream


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
                    val fileLength = readLine(unarchiveInputStream).toInt()

                    val file = File(filePath)

                    // TODO: предотвратить кучу места
                    file.writeBytes(unarchiveInputStream.readNBytes(fileLength))
                    unarchiveInputStream.skip(1) // skip \n
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
                    val file = File(filePaths[i]) // TODO: error handling
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