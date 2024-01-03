package util

import model.MatchData
import model.MatchScore
import model.PlayerData

fun calculateMatchScore(matchData: MatchData, userSteamId: Long): MatchScore {
    val ctTotal = findFirstValidTeamRounds(matchData.playerData, "CT")
    val tTotal = findFirstValidTeamRounds(matchData.playerData, "TERRORIST")

    return when {
        ctTotal != null && tTotal != null -> {
            val ctWins = ctTotal > tTotal
            val userTeam = matchData.playerData.find { it.steamId == userSteamId }?.teamName
            val userOnWinningTeam = when {
                userTeam == "CT" -> ctWins
                userTeam == "TERRORIST" -> !ctWins
                else -> false
            }
            MatchScore(ctTotal, tTotal, userOnWinningTeam)
        }
        else -> MatchScore(0, 0, false)
    }
}

fun findFirstValidTeamRounds(playerData: List<PlayerData>, teamName: String): Int? {
    val teamRounds = playerData.filter { it.teamName == teamName }.map { it.roundsWon }
    return teamRounds.firstOrNull()
}