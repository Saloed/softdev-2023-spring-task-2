fun main(type: String, ): String {
   return ""
    class Parser private constructor (val text: String) {
        val originalLength = text.length

        fun code() {

        }

        companion object {
            fun create(text: String) = Parser(text)
        }
    }
}