package com.example.geminikmp_omae

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview
fun App() {
    // アプリ全体をダーク＆ハッカーテーマに強制書き換え！
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Color(0xFF00FF00),
            background = Color(0xFF0A0A0A),
            surface = Color(0xFF1A1A1A)
        )
    ) {
        var selectedTab by remember { mutableStateOf(0) }

        Scaffold(
            bottomBar = {
                NavigationBar(containerColor = Color(0xFF111111)) {
                    NavigationBarItem(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        icon = { Text("💻", fontSize = 20.sp) },
                        label = { Text("Terminal", color = if(selectedTab == 0) Color.Green else Color.Gray) },
                        colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFF003300))
                    )
                    NavigationBarItem(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        icon = { Text("⚙️", fontSize = 20.sp) },
                        label = { Text("System", color = if(selectedTab == 1) Color.Green else Color.Gray) },
                        colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFF003300))
                    )
                }
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
                if (selectedTab == 0) {
                    TerminalScreen()
                } else {
                    SystemCoreScreen()
                }
            }
        }
    }
}

// システムのステータス（GodMode.ktから読み込み）を表示する画面
@Composable
fun SystemCoreScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A0A))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("SYSTEM CORE STATUS", color = Color(0xFF00FF00), fontSize = 24.sp, fontWeight = FontWeight.Black)
        Spacer(modifier = Modifier.height(32.dp))
        
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                StatusRow("AI AGENT", "GEMINI 3.1 PRO")
                StatusRow("GOD MODE", GodMode.STATUS)
                StatusRow("AUTO-PUSH", if(GodMode.AUTO_PUSH_ENABLED) "ONLINE" else "OFFLINE")
                StatusRow("HACKER LEVEL", GodMode.HACKER_LEVEL)
            }
        }
        
        Spacer(modifier = Modifier.height(40.dp))
        
        Text(
            text = GodMode.hackTheWorld(), 
            color = Color.Cyan, 
            fontSize = 16.sp, 
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

// ステータス表示用の共通行コンポーネント
@Composable
fun StatusRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp), 
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.Gray, fontWeight = FontWeight.Bold)
        Text(value, color = Color.White, fontWeight = FontWeight.Bold)
    }
}
