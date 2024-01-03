package util

import model.PlayerData

fun entryMapper(playerData: List<PlayerData>): Map<String, List<String>> {
    val entryValues = mapOf(
        "#" to (1..playerData.size).map { it.toString() },
        "Ping" to playerData.map { it.ping.toString() },
        "Name" to playerData.map { it.name },
        "Rank" to playerData.map { it.rank.toString() },
        "Kills" to playerData.map { it.kills.toString() },
        "Deaths" to playerData.map { it.deaths.toString() },
        "Assists" to playerData.map { it.assists.toString() },
        "HS%" to playerData.map {
            if (it.headshotKills == 0) "0%" else ((it.headshotKills.toFloat() / it.kills.toFloat()) * 100).toInt()
                .toString() + "%"
        },
        "DMG" to playerData.map { it.damage.toString() },
        "UD" to playerData.map { it.utilityDamage.toString() },
        "EF" to playerData.map { it.enemiesFlashed.toString() },
        "MVPs" to playerData.map { it.mvps.toString() },
        "5k" to playerData.map { it.aces.toString() },
        "4k" to playerData.map { it.fourKRounds.toString() },
        "3k" to playerData.map { it.threeKRounds.toString() }
    )
    return entryValues
}