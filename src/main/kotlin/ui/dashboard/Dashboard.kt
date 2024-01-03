package ui.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DashboardScreen(){
    Column(modifier = Modifier.fillMaxSize().padding(4.dp)) {
        Text(
            text = "Dashboard",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(4.dp)
        )
        Card(
            modifier = Modifier.fillMaxSize().padding(4.dp),
            shape = ShapeDefaults.ExtraLarge
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                for (i in 0..100) {
                    item {
                        Text(i.toString(), modifier = Modifier.padding(12.dp, 4.dp))
                    }
                }
            }
        }
    }
}