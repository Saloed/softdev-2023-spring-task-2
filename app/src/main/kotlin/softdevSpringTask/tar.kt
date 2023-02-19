package softdevSpringTask

import java.io.File
import java.io.FileInputStream
import java.io.OutputStream

fun addFileContent(fileToCopy: File, outputFileOutputStream: OutputStream) {
    val fileInputStream = fileToCopy.inputStream()
    try {
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        var bytes = fileInputStream.read(buffer)
        while (bytes >= 0) {
            outputFileOutputStream.write(buffer, 0, bytes)
            bytes = fileInputStream.read(buffer)
        }
    } catch (exception: Exception) {
        exception.printStackTrace()
    } finally {
        outputFileOutputStream.flush()
        fileInputStream.close()
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


fun main(args: Array<String>) {
    val usageHelp = "Usage:\n" +
            "   tar -u filename.txt [-r] (-r makes it paste files to the directory they have been archived from)\n" +
            "   tar file1.txt file2.txt … -out output.txt\n"
    if (args.size < 2) print(usageHelp)

    if (args[0] == "-u") {
        val useOnlyFilenames = !args.contains("-r")

        val toUnarchive = File(args[1])
        val unarchiveInputStream = toUnarchive.inputStream()
        val n = readLine(unarchiveInputStream).toInt()


        repeat(n) {
            var filePath = readLine(unarchiveInputStream)
            val fileLength = readLine(unarchiveInputStream).toInt()
            if (useOnlyFilenames) filePath = filePath.slice(
                filePath.indexOfLast {it == '\\' } + 1..filePath.lastIndex
            )
            val file = File(filePath)
            if (file.createNewFile()) println("Created $filePath")

            // TODO: предотвратить кучу места
            file.writeBytes(unarchiveInputStream.readNBytes(fileLength))
            unarchiveInputStream.read() // skip \n

        }

    } else if (args.indexOf("-out") == args.size-2) {

        val outputFile = File(args.last())
        if (outputFile.createNewFile()) println("Created ${args.last()}")

        /**
         * Output file content:
         * - quantity of files,
         * - for every file:
         *    - filename in UTF-8 characters,
         *    - file content length in bytes,
         *    - file content
         */

        val outputFileStream = outputFile.outputStream()
        outputFileStream.write("${args.size-2}\n".toByteArray())

        for (i in 0..args.size-3) {
            //outputFileStream.write("${args[i].length}\n".toByteArray())
            outputFileStream.write("${args[i]}\n".toByteArray())

            val file = File(args[i]) // TODO: error handling
            outputFileStream.write("${file.length()}\n".toByteArray())

            addFileContent(file, outputFileStream)
            outputFileStream.write("\n".toByteArray())
        }
        outputFileStream.close()
    } else print(usageHelp)


}