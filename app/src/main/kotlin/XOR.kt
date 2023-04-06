package softdevSpringTask

import picocli.CommandLine
import picocli.CommandLine.Option
import picocli.CommandLine.Parameters
import picocli.CommandLine.ArgGroup
import java.io.*
import java.util.concurrent.Callable
import kotlin.experimental.xor
import kotlin.system.exitProcess

fun encryption(key: ByteArray, inputName: String, outputName: String) {
    FileInputStream(inputName).use { inputStream ->
        val file = DataInputStream(inputStream)
        FileOutputStream(outputName).use { outputStream ->
            val data = DataOutputStream(outputStream)
            file.use { reader ->
                data.use { writer ->
                    val text = reader.readBytes()
                    var y = 0
                    for (i in text) {
                        val temp = i xor key[y]
                        writer.writeByte(temp.toInt())
                        y++
                        if (y >= key.size) y = 0
                    }
                }
            }
        }
    }
}

    fun decryption(key: ByteArray, inputName: String, outputName: String) {
        FileInputStream(inputName).use { inputStream ->
            val file = DataInputStream(inputStream)
            FileOutputStream(outputName).use { outputStream ->
                val data = DataOutputStream(outputStream)
                file.use { reader ->
                    data.use { writer ->
                        val text = reader.readBytes()
                        var y = 0
                        for (i in text) {
                            val temp = i xor key[y]
                            writer.writeByte(temp.toInt())
                            y++
                            if (y >= key.size) y = 0
                        }
                    }
                }
            }
        }
    }

fun main(args: Array<String>): Unit = exitProcess(CommandLine(XOR()).execute(*args))

class XOR : Callable<Any> {

    @Parameters(index = "0")
    lateinit var inputName: String

    @ArgGroup(exclusive = true, multiplicity = "1")
    lateinit var exclusive: Exclusive

    class Exclusive {
        @Option(names = ["-c"], required = true)
        var key: String = ""

        @Option(names = ["-d"], required = true)
        var dkey: String = ""
    }

    @Option(names = ["-o"], required = true)
    lateinit var outputName: String

    override fun call() {
        val key = exclusive.key
        val dkey = exclusive.dkey
        return if (key.isNotEmpty()) encryption(key.toByteArray(), inputName, outputName)
        else decryption(dkey.toByteArray(), inputName, outputName)
    }
}