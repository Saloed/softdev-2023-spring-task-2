class Parser(private val args: Array<String>) {
    private val str
        get() = if (args.size <= 4) args.joinToString()
            .replace(",", "") else throw IllegalStateException()
    val r: Boolean
        get() = str.contains(Regex("""\[-r]"""))
    val d
        get() = Regex("""(?<=-d )\S+(?=])""").find(str)?.value
            ?: throw IllegalArgumentException("Не введена директория")
    val name: String
        get() {
            val b = str.replace(Regex("""\[-r]"""), "").replace(Regex("""\[-d \S+]"""), "").trim()
            return b.ifEmpty { throw IllegalArgumentException("Отсутствует имя файла") }
        }
}
