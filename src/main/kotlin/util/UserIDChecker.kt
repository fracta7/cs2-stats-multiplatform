package util

import java.io.File

fun configExists(userIdFilePath: String): Boolean {
    val file = File(userIdFilePath)
    return file.exists() && file.readText().isNotEmpty()
}

fun writeTextToFile(filePath: String, content: String) {
    val file = File(filePath)
    file.writeText(content)
}

fun readCFG(filePath: String): Long?{
    val file = File(filePath)
    return if (file.exists()) {
        try {
            file.readText().trim().toLong()
        } catch (e: NumberFormatException) {
            null // If the content cannot be parsed as Long
        }
    } else {
        null // If the file does not exist
    }
}