package softdevSpringTask

import java.io.*
class XOR(private val key: String) {

    @Throws(IOException::class)
    fun encryption(inputName: String, outputName: String) {
        FileInputStream(inputName).use { inputStream ->
            FileOutputStream(outputName).use { outputStream ->
                InputStreamReader(inputStream).use { reader ->
                    OutputStreamWriter(outputStream).use { writer ->
                        val text = reader.readText()
                        var y = 0
                        for (i in text.indices) {
                            val temp = text[i].code xor key[y].code
                            writer.write(temp)
                            y++
                            if (y >= key.length) y = 0
                        }
                    }
                }
            }
        }
    }

    @Throws(IOException::class)
    fun decryption(inputName: String, outputName: String) {
        FileInputStream(inputName).use { inputStream ->
            FileOutputStream(outputName).use { outputStream ->
                InputStreamReader(inputStream).use { reader ->
                    OutputStreamWriter(outputStream).use { writer ->
                        val text = reader.readText()
                        var y = 0
                        for (i in text) {
                            val temp = i.code xor key[y].code
                            writer.write(temp)
                            y++
                            if (y >= key.length) y = 0
                        }
                    }
                }
            }
        }
    }
}
