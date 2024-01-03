package ui.elements.table

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TeamEntry(entryValues: Map<String, List<String>>){
    Row(Modifier.height(IntrinsicSize.Min)) {
        entryValues.forEach { (entryName, entries) ->
            ColumnEntry(entryName = entryName, entries = entries)
            VerticalDivider()
        }
    }
}