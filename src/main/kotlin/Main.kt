import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import ui.dashboard.DashboardScreen
import ui.matches.MatchesScreen
import ui.settings.SettingsScreen
import ui.theme.AppTheme
import util.configExists
import util.readCFG
import util.writeTextToFile
import kotlin.system.exitProcess

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    AppTheme(darkTheme = true) {

        val cfgExists = configExists("user_id")
        val openDialog = remember { mutableStateOf(!cfgExists) }
        var steamId by remember { mutableStateOf(0L) }

        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = { exitProcess(0) },
            ) {
                Column {
                    Text("Please enter your steam ID below. Dismissing will close the application")
                    var steamIdInput by remember { mutableStateOf("") }
                    TextField(
                        steamIdInput,
                        onValueChange = { steamIdInput = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        label = { Text("steamID") },
                        shape = ShapeDefaults.Large
                    )
                    Button(onClick = {
                        writeTextToFile(filePath = "user_id", content = steamIdInput)
                        steamId = steamIdInput.toLong()
                        openDialog.value = false
                    }){
                        Text("Save")
                    }
                }
            }
        }
        if (cfgExists){
            steamId = readCFG("user_id")!!
        }

        var selectedItem by remember { mutableIntStateOf(0) }
        val items = listOf("Home", "Matches", "Settings")
        val icons = listOf(Icons.Filled.Home, Icons.Filled.List, Icons.Filled.Settings)
        val draggableState = rememberDraggableState { }
        Column(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
                .draggable(state = draggableState, orientation = Orientation.Horizontal)
        ) {
            Row {
                NavigationRail {
                    items.forEachIndexed { index, item ->
                        NavigationRailItem(
                            icon = { Icon(icons[index], contentDescription = item) },
                            label = { Text(item) },
                            selected = selectedItem == index,
                            onClick = { selectedItem = index }
                        )
                    }
                }
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    when (selectedItem) {
                        0 -> DashboardScreen()
                        1 -> MatchesScreen(steamId)
                        2 -> SettingsScreen()
                    }
                }
            }
        }

    }
}

fun main() = application {

    val state = rememberWindowState()

    AppTheme(darkTheme = true) {
        Window(
            onCloseRequest = ::exitApplication,
            title = "CS2 Stats",
            undecorated = true,
            transparent = true,
            state = state
        ) {

            Card(shape = ShapeDefaults.Medium) {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    WindowDraggableArea {
                        Text(
                            "CS2 Stats",
                            modifier = Modifier.padding(8.dp, 0.dp),
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.Bold
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth().height(32.dp),
                            horizontalArrangement = Arrangement.End
                        ) {

                            IconButton(
                                onClick = { state.isMinimized = state.isMinimized.not() }
                            ) {
                                Icon(
                                    Icons.Filled.KeyboardArrowDown,
                                    contentDescription = "Minimize",
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                            }
                            IconButton(onClick = {
                                state.placement = if (state.placement == WindowPlacement.Maximized)
                                    WindowPlacement.Floating else WindowPlacement.Maximized
                            }) {
                                Icon(
                                    Icons.Filled.KeyboardArrowUp,
                                    contentDescription = "close",
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                            }
                            IconButton(onClick = { exitProcess(0) }) {
                                Icon(
                                    Icons.Filled.Close,
                                    contentDescription = "close",
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }
                    App()
                }
            }
        }
    }
}
