package util

import com.google.gson.GsonBuilder
import model.MatchData
import model.MatchDataDeserializer
import java.io.File

fun parseMatchData(json: String): MatchData? {
    val gsonBuilder = GsonBuilder()
    gsonBuilder.registerTypeAdapter(MatchData::class.java, MatchDataDeserializer())
    val gson = gsonBuilder.create()
    return gson.fromJson(json, MatchData::class.java)
}

/**
 * parseMatchDataFromDirectory parses all json files containing match data in given path.
 *
 * @param directoryPath path containing all json files.
 * @return list of MatchData.
 */
fun parseMatchDataFromDirectory(directoryPath: String): List<MatchData> {
    val directory = File(directoryPath)
    val matchDataList = mutableListOf<MatchData>()

    if (directory.exists() && directory.isDirectory) {
        val jsonFiles = directory.listFiles { file ->
            file.isFile && file.extension.lowercase() == "json"
        }

        jsonFiles?.forEach { file ->
            val jsonString: String = file.readText()
            val matchData = parseMatchData(jsonString)
            if (matchData != null) {
                matchDataList.add(matchData)
            }
        }
    }

    return matchDataList
}

fun parseMatchDataFromFile(filePath: String): MatchData? {
    val file = File(filePath)
    if (file.exists() && file.isFile) {
        val jsonString: String = file.readText()
        return parseMatchData(jsonString)
    }
    return null
}