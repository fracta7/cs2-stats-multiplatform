package model

data class PlayerData(
    val teamName: String,
    val roundsWon: Int,
    val steamId: Long,
    val name: String,
    val rank: Int,
    val kills: Int,
    val deaths: Int,
    val assists: Int,
    val headshotKills: Int,
    val damage: Int,
    val utilityDamage: Int,
    val enemiesFlashed: Int,
    val mvps: Int,
    val ping: Int,
    val aces: Int,
    val fourKRounds: Int,
    val threeKRounds: Int
)