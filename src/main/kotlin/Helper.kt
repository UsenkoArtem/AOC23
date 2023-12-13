private object Helper


fun readTextByLines(fileName: String) =
    Helper.javaClass.classLoader.getResource(fileName)!!.readText().lines()

fun readText(fileName: String) =
    Helper.javaClass.classLoader.getResource(fileName)!!.readText()

