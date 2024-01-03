package ui.matches

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.elements.match_list.MatchList
import ui.elements.table.MatchTable
import util.parseMatchDataFromDirectory

@Composable
fun MatchesScreen(steamId: Long) {
    var state by mutableStateOf(MatchesState())
    LaunchedEffect(Unit) {
        state = state.copy(matches = parseMatchDataFromDirectory("matches/").toMutableList())
    }
    Column(modifier = Modifier.fillMaxSize().padding(4.dp)) {
        Text(
            text = "Match History", fontSize = 28.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(4.dp)
        )
        Card(
            modifier = Modifier.fillMaxSize().padding(4.dp), shape = ShapeDefaults.ExtraLarge
        ) {
            LazyColumn(modifier = Modifier.padding(12.dp, 4.dp)) {
                state.matches.forEach { matchData ->
                    item {
                        //MatchTable(matchData = matchData)
                        MatchList(matchData = matchData, userSteamID = steamId)
                        Divider(modifier = Modifier.padding(4.dp), color = MaterialTheme.colorScheme.background)
                    }
                }
            }
        }
    }
}