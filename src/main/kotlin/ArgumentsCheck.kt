import kotlinx.cli.*
import java.io.File
import java.lang.IllegalArgumentException

class ArgumentsCheck(args: Array<String>) {

    private val parser = ArgParser("cut")

    val indent by parser.argument(ArgType.String,
        description = "Selects indentation by characters or by words")
    private val oFilePath by parser.option(ArgType.String, shortName = "o",
        description = "The file to which the result will be output")
    private var iFilePath by parser.argument(ArgType.String,
        description = "The file with input data").optional()
    private var rangeParam by parser.argument(ArgType.String,
        description = "Index range").optional()

    init {
        parser.parse(args)
    }

    var outputFile: File? = null
    var inputFile: File? = null
    val rangeStart: Int
    val rangeEnd: Int?

    init {
        if (indent != "-c" && indent != "-w")
            throw java.lang.IllegalArgumentException()

        try {
            if (oFilePath != null)
                outputFile = File(oFilePath)

            if (iFilePath != null)
                if (rangeParam == null) {
                    rangeParam = iFilePath
                    iFilePath = null
                } else inputFile = File(iFilePath)

            val range = Regex("""\D""").split(rangeParam!!)
            if (range[1] <= range[0]) throw Exception()
            rangeStart =
                if (range[0].isNotBlank() && range[0][0] != '-') range[0].toInt()
                else 0
            rangeEnd =
                if (range[1].isNotBlank() && range[1][0] != '-') range[1].toInt()
                else null
        } catch (e: Exception) {
            throw IllegalArgumentException()
        }
    }

}


