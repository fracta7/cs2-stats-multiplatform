package model

import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class MatchDataDeserializer : JsonDeserializer<MatchData> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): MatchData {
        val jsonObject = json?.asJsonObject

        val serverName = jsonObject?.get("server_name")?.asString ?: ""
        val map = jsonObject?.get("map")?.asString ?: ""
        val totalRoundsPlayed =
            jsonObject?.get("total_rounds_played")?.asString?.toIntOrNull() ?: 0
        val players = parsePlayers(jsonObject?.getAsJsonArray("players"))

        return MatchData(serverName, map, totalRoundsPlayed, players)
    }

    private fun parsePlayers(playersArray: JsonArray?): List<PlayerData> {
        val list = mutableListOf<PlayerData>()

        playersArray?.forEach { player ->
            val teamName = player.asJsonObject.get("team_name").asString ?: ""
            val roundsWon = player.asJsonObject.get("rounds_won").asString.toFloatOrNull()?.toInt()
                ?: 0
            val steamId = player.asJsonObject.get("steam_id").asString.toLongOrNull()
                ?: 0L
            val name = player.asJsonObject.get("name").asString ?: ""
            val rank = player.asJsonObject.get("rank").asString.toIntOrNull() ?: 0
            val kills = player.asJsonObject.get("kills").asString.toIntOrNull() ?: 0
            val deaths = player.asJsonObject.get("deaths").asString.toIntOrNull() ?: 0
            val assists = player.asJsonObject.get("assists").asString.toIntOrNull() ?: 0
            val headshotKills = player.asJsonObject.get("headshot_kills").asString.toIntOrNull()
                ?: 0
            val damage = player.asJsonObject.get("damage").asString.toIntOrNull() ?: 0
            val utilityDamage = player.asJsonObject.get("utility_damage").asString.toIntOrNull()
                ?: 0
            val enemiesFlashed =
                player.asJsonObject.get("enemies_flashed").asString.toIntOrNull() ?: 0
            val mvps = player.asJsonObject.get("mvps").asString.toIntOrNull() ?: 0
            val ping = player.asJsonObject.get("ping").asString.toIntOrNull() ?: 0
            val aces = player.asJsonObject.get("aces").asString.toIntOrNull() ?: 0
            val fourKRounds = player.asJsonObject.get("four_k_rounds").asString.toIntOrNull()
                ?: 0
            val threeKRounds =
                player.asJsonObject.get("three_k_rounds").asString.toIntOrNull() ?: 0

            val data = PlayerData(
                teamName, roundsWon, steamId, name, rank, kills, deaths, assists,
                headshotKills, damage, utilityDamage, enemiesFlashed, mvps, ping,
                aces, fourKRounds, threeKRounds
            )
            list.add(data)
        }

        return list
    }
}