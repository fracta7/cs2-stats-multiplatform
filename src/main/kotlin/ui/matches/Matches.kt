package ui.matches

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import ui.elements.match_list.MatchList
import ui.elements.table.MatchTable
import util.parseMatchDataFromDirectory
import java.awt.GraphicsConfiguration

/**
 * MatchScreen is screen for viewing all match history.
 *
 * @param steamId steamID of the user.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchesScreen(steamId: Long) {
    var state by mutableStateOf(MatchesState())
    var openDialog by remember { mutableStateOf(false) }
    var matchIndex by remember { mutableIntStateOf(0)}
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
            if (openDialog) {
                Window(
                    create ={
                        ComposeWindow()
                    },
                    dispose = {}){
                    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)) {
                        MatchTable(matchData = state.matches[matchIndex])
                    }
                }
                /*
                AlertDialog(
                    modifier = Modifier.fillMaxSize(0.95f),
                    onDismissRequest = {
                    openDialog = !openDialog
                }){

                }

                 */
            }
            LazyColumn(modifier = Modifier.padding(12.dp, 4.dp)) {
                state.matches.forEachIndexed { index, matchData ->
                    item {
                        //MatchTable(matchData = matchData)
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxSize().clickable {
                            openDialog = !openDialog
                            matchIndex = index
                        }) {
                            MatchList(matchData = matchData, userSteamID = steamId)
                            Text(text = matchData.serverName, modifier = Modifier.padding(4.dp))
                        }
                        Divider(modifier = Modifier.padding(4.dp), color = MaterialTheme.colorScheme.background)
                    }
                }
            }
        }
    }
}