package softdevSpringTask

import picocli.CommandLine
import picocli.CommandLine.Option
import picocli.CommandLine.Parameters
import picocli.CommandLine.ArgGroup
import java.io.*
import java.util.concurrent.Callable
import kotlin.experimental.xor
import kotlin.system.exitProcess

fun crypto(key: ByteArray, inputName: String, outputName: String) {
    FileInputStream(inputName).use { inputStream ->
        val file = DataInputStream(inputStream)
        FileOutputStream(outputName).use { outputStream ->
            val data = DataOutputStream(outputStream)
            file.use { reader ->
                data.use { writer ->
                    val text = ByteArray(32)
                    var y = 0
                    while (true) {
                        val n = reader.read(text)
                        if (n == -1) break
                        for (idx in 0 until n) {
                            val i = text[idx]
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
}

fun main(args: Array<String>): Unit = exitProcess(CommandLine(XOR()).execute(*args))

class XOR : Callable<Unit> {

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
        return if (key.isNotEmpty()) crypto(key.toByteArray(), inputName, outputName)
        else crypto(dkey.toByteArray(), inputName, outputName)
    }
}