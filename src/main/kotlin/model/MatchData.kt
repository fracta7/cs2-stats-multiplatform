package model

data class MatchData(
    val serverName: String,
    val map: String,
    val totalRounds: Int,
    val playerData: List<PlayerData>
)