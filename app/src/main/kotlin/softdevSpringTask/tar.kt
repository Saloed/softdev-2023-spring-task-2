package softdevSpringTask


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


fun main(args: Array<String>) {
    val usageExamples =
        """
        Usage:
            tar -u filename.txt
            tar file1.txt file2.txt … -out output.txt
        """.trimIndent()

    if (args.size < 2) print(usageExamples)

    if (args[0] == "-u") {
        val toUnarchive = File(args[1])

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

    } else if (args.indexOf("-out") == args.size-2) {

        val outputFile = File(args.last())

        /**
         * Output file content:
         * - quantity of files,
         * - for every file:
         *    - filename in UTF-8 characters,
         *    - file content length in bytes,
         *    - file content in bytes
         */

        outputFile.outputStream().use { outputStream ->
            outputStream.write("${args.size - 2}\n".toByteArray())

            for (i in 0..args.size - 3) {
                val file = File(args[i]) // TODO: error handling
                outputStream.write("${args[i]}\n".toByteArray())

                outputStream.write("${file.length()}\n".toByteArray())

                addFileContent(file, outputStream)
                outputStream.write("\n".toByteArray())
            }
        }
    } else print(usageExamples)


}