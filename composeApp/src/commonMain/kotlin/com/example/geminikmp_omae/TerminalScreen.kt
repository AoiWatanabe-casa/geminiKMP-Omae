// CREATE: /Users/watanabeaoi/AndroidStudioProjects/geminiKMP-Omae/composeApp/src/commonMain/kotlin/com/example/geminikmp_omae/TerminalScreen.kt
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
import kotlin.random.Random

@Composable
fun TerminalScreen() {
    // ハッカー風の初期ログ
    val logs = remember { mutableStateListOf(
        "INITIALIZING G-OS...", 
        "ESTABLISHING SECURE CONNECTION TO GEMINI CLOUD...", 
        "BYPASSING FIREWALL...",
        "ACCESS GRANTED. GOD MODE: ACTIVE."
    ) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // ハックボタンを押した時のランダムなフレーズ
    val hackPhrases = listOf(
        "Decrypting user data payload...",
        "Injecting dynamic code into commonMain...",
        "Git auto-push sequence engaged.",
        "Overriding UI thread...",
        "Compiling reality.kt..."
    )

    // 新しいログが追加されたら一番下まで自動スクロールするアニメーション
    LaunchedEffect(logs.size) {
        listState.animateScrollToItem(logs.size.coerceAtLeast(0))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Text("💻 G-OS TERMINAL v5.0", color = Color(0xFF00FF00), fontSize = 22.sp, fontWeight = FontWeight.Black)
        Spacer(modifier = Modifier.height(16.dp))
        
        // ログ表示エリア（黒背景）
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color(0xFF111111))
                .padding(12.dp)
        ) {
            LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
                items(logs) { log ->
                    Text(
                        text = "> $log", 
                        color = Color(0xFF00FF00), 
                        fontFamily = FontFamily.Monospace, 
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // ハック実行ボタン
        Button(
            onClick = {
                coroutineScope.launch {
                    logs.add("EXECUTING HACK PROTOCOL...")
                    delay(400)
                    logs.add(hackPhrases.random())
                    if (Random.nextFloat() > 0.6f) { // 40%の確率でカウンターメジャー発生演出
                        delay(300)
                        logs.add("⚠️ WARNING: COUNTER-MEASURE DETECTED. NEUTRALIZING...")
                    }
                    delay(500)
                    logs.add("SUCCESS.")
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FF00), contentColor = Color.Black),
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("EXECUTE HACK", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
        }
    }
}
