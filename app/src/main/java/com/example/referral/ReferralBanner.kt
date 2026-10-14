package com.example.referral

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ReferralBanner(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var progress by remember { mutableStateOf(0.0) }

    LaunchedEffect(Unit) {
        android.util.Log.d("ReferralBanner", "ReferralBanner rendered")
        progress = ReferralManager.getProgress(context)
    }
    
    fun shareContent(isGroup: Boolean) {
        val shareText = "මචං, මාත් මේ ඇප් එක ඩවුන්ලෝඩ් කරගත්තා. පට්ට වැදගත් ඈ! 🔥\n\nඔන්න 10/11 ශ්‍රේණි දෙකේ දරුවන්ට අම්මට සිරිවෙන ඇප් එකක්! 📚✨\n\n👇 පහත ලින්ක් එකෙන් උඹත් දැන්ම ඇප් එක ඩවුන්ලෝඩ් කරගනිං:\n👉 https://play.google.com/store/apps/details?id=com.aistudio.studentportal.slnotes"
        
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        context.startActivity(Intent.createChooser(intent, "Share via"))
        
        scope.launch {
            ReferralManager.addShare(context, isGroup)
            progress = ReferralManager.getProgress(context)
        }
    }

    Surface(
        color = androidx.compose.ui.graphics.Color.Red,
        modifier = modifier.fillMaxWidth().height(60.dp).padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("📊 Progress: ${String.format("%.2f", progress)}%", style = MaterialTheme.typography.bodyMedium)
                LinearProgressIndicator(
                    progress = { (progress / 100.0).toFloat().coerceIn(0f, 1f) },
                    modifier = Modifier.fillMaxWidth().height(6.dp).padding(top = 4.dp),
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = { shareContent(false) }) {
                Text("🟢", style = MaterialTheme.typography.titleLarge)
            }
            IconButton(onClick = { shareContent(true) }) {
                Text("🔵", style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}
