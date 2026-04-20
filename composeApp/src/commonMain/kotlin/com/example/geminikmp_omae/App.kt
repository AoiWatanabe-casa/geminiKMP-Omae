// UPDATE: /Users/watanabeaoi/AndroidStudioProjects/geminiKMP-Omae/composeApp/src/commonMain/kotlin/com/example/geminikmp_omae/App.kt
package com.example.geminikmp_omae

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// チャットメッセージのデータ構造
data class ChatMessage(val text: String, val isUser: Boolean)

@Composable
@Preview
fun App() {
    MaterialTheme {
        // メッセージの履歴を保持するリスト
        val messages = remember { mutableStateListOf(
            ChatMessage("よう！俺専用アプリを作ってくれてサンキューな！何して遊ぶ？", false)
        ) }
        // 入力中のテキスト
        var inputText by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .safeContentPadding() // iOSのノッチやAndroidのステータスバーを避ける
                .fillMaxSize()
        ) {
            // アプリ上部のヘッダー
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primaryContainer,
                shadowElevation = 4.dp
            ) {
                Text(
                    text = "🤖 GeminiKMP-Omae",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            // チャット履歴エリア (重み1で残りスペースを全部取る)
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                reverseLayout = false // 上から下へ配置
            ) {
                items(messages) { message ->
                    ChatBubble(message = message)
                }
            }

            // メッセージ入力エリア
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("メッセージを入力...") },
                    shape = RoundedCornerShape(24.dp),
                    singleLine = true
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Button(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            // ユーザーのメッセージを追加
                            messages.add(ChatMessage(inputText, true))
                            
                            // 俺からの適当な返事を遅延で追加（非同期処理の代わりにシンプルに即座追加）
                            val reply = "「${inputText}」って言ったな！まだ通信機能がないからおうむ返しだぜ！"
                            messages.add(ChatMessage(reply, false))
                            
                            inputText = "" // 入力欄をクリア
                        }
                    },
                    shape = CircleShape,
                    contentPadding = PaddingValues(16.dp)
                ) {
                    Text("送信", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// 吹き出しのUIコンポーネント
@Composable
fun ChatBubble(message: ChatMessage) {
    val alignment = if (message.isUser) Alignment.CenterEnd else Alignment.CenterStart
    val bgColor = if (message.isUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
    val textColor = if (message.isUser) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
    val shape = if (message.isUser) {
        RoundedCornerShape(20.dp, 20.dp, 4.dp, 20.dp)
    } else {
        RoundedCornerShape(20.dp, 20.dp, 20.dp, 4.dp)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = alignment
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 280.dp) // 幅の最大値を設定
                .clip(shape)
                .background(bgColor)
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Text(
                text = message.text,
                color = textColor,
                fontSize = 16.sp
            )
        }
    }
}
