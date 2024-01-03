package ui.elements.table

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.MatchData
import ui.matches.sampleMatchData
import ui.theme.AppTheme
import util.entryMapper

@Composable
fun MatchTable(modifier: Modifier = Modifier, matchData: MatchData) {

    //divide the players to teams; ct, t and unspecified team for handling those entries without any team on demo file
    val (uCTTeam, uTTeam, uUTeam) = matchData.playerData.groupBy {
        when (it.teamName) {
            "CT" -> "CT"
            "TERRORIST" -> "TERRORIST"
            else -> "U_TEAM"
        }
    }.let { grouped ->
        listOf(
            grouped["CT"] ?: emptyList(),
            grouped["TERRORIST"] ?: emptyList(),
            grouped["U_TEAM"] ?: emptyList()
        )
    }

    //sorting the teams with damage given properties for each team
    val ctTeam = uCTTeam.sortedByDescending { it.damage }
    val tTeam = uTTeam.sortedByDescending { it.damage }
    val uTeam = uUTeam.sortedByDescending { it.damage }

    //fields that we want to display; mapped as <field name>:<field values of players>
    val ctEntryValues = entryMapper(ctTeam)
    val tEntryValues = entryMapper(tTeam)
    val uEntryValues = entryMapper(uTeam)
    Column(modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Server: ")
            Text(matchData.serverName, fontWeight = FontWeight.Bold)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Map: ")
            Text(matchData.map, fontWeight = FontWeight.Bold)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Number of Rounds: ")
            Text(matchData.totalRounds.toString(), fontWeight = FontWeight.Bold)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(ctTeam[0].roundsWon.toString(), fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Spacer(Modifier.padding(2.dp))
            Text(" - Counter-Terrorists")
        }
        TeamEntry(ctEntryValues)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(tTeam[0].roundsWon.toString(), fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Spacer(Modifier.padding(2.dp))
            Text(" - Terrorists")
        }
        TeamEntry(tEntryValues)
        if (uTeam.isNotEmpty()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(Modifier.padding(2.dp))
                Text("Unspecified Team")
            }
            TeamEntry(uEntryValues)
        }
    }

}

@Preview
@Composable
fun PreviewTable() {
    AppTheme(darkTheme = true) {
        MatchTable(matchData = sampleMatchData)
    }
}

