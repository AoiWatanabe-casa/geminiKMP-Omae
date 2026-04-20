// UPDATE: /Users/watanabeaoi/AndroidStudioProjects/geminiKMP-Omae/composeApp/src/commonMain/kotlin/com/example/geminikmp_omae/TerminalScreen.kt
package com.example.geminikmp_omae

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TerminalScreen() {
    val logs = remember { mutableStateListOf("G-OS READY.") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val weatherApi = remember { WeatherApi() }

    LaunchedEffect(logs.size) {
        listState.animateScrollToItem(logs.size - 1)
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.Black).padding(16.dp)) {
        Text("💻 G-OS TERMINAL v5.1", color = Color(0xFF00FF00), fontSize = 22.sp, fontWeight = FontWeight.Black)
        
        Box(modifier = Modifier.weight(1f).fillMaxWidth().background(Color(0xFF111111)).padding(12.dp)) {
            LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
                items(logs) { log ->
                    Text("> $log", color = Color(0xFF00FF00), fontFamily = FontFamily.Monospace, fontSize = 14.sp)
                }
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // 天気ハックボタン
            Button(
                onClick = {
                    coroutineScope.launch {
                        logs.add("SYNCING REAL-WORLD DATA...")
                        delay(600)
                        val result = weatherApi.fetchTokyoWeather()
                        logs.add("WEATHER_DATA: $result")
                    }
                },
                modifier = Modifier.weight(1f).height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF004400), contentColor = Color.Green)
            ) {
                Text("WEATHER SYNC", fontWeight = FontWeight.Bold)
            }

            // 通常ハックボタン
            Button(
                onClick = {
                    coroutineScope.launch {
                        logs.add("INJECTING CODE...")
                        delay(400)
                        logs.add("SUCCESS.")
                    }
                },
                modifier = Modifier.weight(1f).height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FF00), contentColor = Color.Black)
            ) {
                Text("EXECUTE HACK", fontWeight = FontWeight.Bold)
            }
        }
    }
}
