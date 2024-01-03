package ui.elements.match_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.MatchData
import util.calculateMatchScore

@Composable
fun MatchList(matchData: MatchData, userSteamID: Long) {
    val matchScore = calculateMatchScore(matchData = matchData, userSteamId = userSteamID)
    val mapList = listOf(
        "cs_italy",
        "cs_office",
        "de_ancient",
        "de_anubis",
        "de_dust2",
        "de_inferno",
        "de_mirage",
        "de_nuke",
        "de_overpass",
        "de_vertigo"
    )
    val mapImage = if (mapList.contains(matchData.map)) matchData.map + ".png" else "other.png"

    Row {
        Image(painter = painterResource("map_icons/$mapImage"), contentDescription = "map icon", modifier = Modifier.size(72.dp))
        Column {
            Row {
                Text(matchScore.ctScore.toString()+":", fontWeight = FontWeight.Bold, fontSize = 32.sp)
                Text(matchScore.tScore.toString(), fontWeight = FontWeight.Bold, fontSize = 32.sp)
            }
            val winState = if (matchScore.winner) "Win" else "Loss"
            Text(winState)
        }
    }
}