package com.teyyara.flappybirdtest

import android.content.Context
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameScreen() {
    val context = LocalContext.current.applicationContext
    val preferences = remember(context) {
        context.getSharedPreferences("sky_hopper_preferences", Context.MODE_PRIVATE)
    }
    val engine = remember { GameEngine() }
    var status by remember { mutableStateOf(GameStatus.READY) }
    var score by remember { mutableIntStateOf(0) }
    var bestScore by remember { mutableIntStateOf(preferences.getInt("best_score", 0)) }
    var worldWidth by remember { androidx.compose.runtime.mutableFloatStateOf(360f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(status, worldWidth) {
                detectTapGestures {
                    when (status) {
                        GameStatus.READY -> {
                            engine.startGame(worldWidth)
                            score = 0
                            status = GameStatus.PLAYING
                        }
                        GameStatus.PLAYING -> engine.flap()
                        GameStatus.GAME_OVER -> Unit
                    }
                }
            }
    ) {
        GameCanvas(
            engine = engine,
            status = status,
            modifier = Modifier.fillMaxSize().testTag("gameCanvas"),
            onViewportWidthChanged = { worldWidth = it },
            onStatusChanged = { status = it },
            onScoreChanged = { newScore ->
                score = newScore
                if (newScore > bestScore) {
                    bestScore = newScore
                    preferences.edit().putInt("best_score", newScore).apply()
                }
            }
        )

        if (status == GameStatus.PLAYING) {
            Column(
                modifier = Modifier.align(Alignment.TopCenter).padding(top = 24.dp).testTag("scoreHud"),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(color = Color(0xEE19364A), shape = RoundedCornerShape(20.dp)) {
                    Column(
                        modifier = Modifier.padding(horizontal = 30.dp, vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("SCORE", fontSize = 10.sp, letterSpacing = 2.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFFB8EAF3))
                        Text(score.toString(), fontSize = 30.sp, lineHeight = 32.sp, fontWeight = FontWeight.Black, color = Color.White)
                    }
                }
                Spacer(Modifier.height(8.dp))
                Text("BEST  $bestScore", fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp, color = Color(0xFF19364A))
            }
        }

        if (status == GameStatus.READY) {
            Column(
                modifier = Modifier.align(Alignment.Center).offset(y = (-8).dp).fillMaxWidth().padding(horizontal = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "SKY HOPPER",
                    modifier = Modifier.testTag("readyTitle"),
                    fontSize = 38.sp, lineHeight = 42.sp, letterSpacing = 1.5.sp,
                    fontWeight = FontWeight.Black, color = Color(0xFF19364A), textAlign = TextAlign.Center
                )
                Text("FLAP. FLOAT. FLY.", modifier = Modifier.padding(top = 5.dp), fontSize = 12.sp, letterSpacing = 3.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF2D6E82))
                Spacer(Modifier.height(31.dp))
                Surface(color = Color(0xDFFFFFFF), shape = RoundedCornerShape(22.dp)) {
                    Column(
                        modifier = Modifier.padding(horizontal = 25.dp, vertical = 19.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("HOW TO PLAY", fontSize = 11.sp, letterSpacing = 2.sp, fontWeight = FontWeight.Black, color = Color(0xFF4A8A91))
                        Text("Tap to flap. Slip through the pipes.", modifier = Modifier.padding(top = 8.dp), fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF19364A), textAlign = TextAlign.Center)
                    }
                }
                Spacer(Modifier.height(27.dp))
                Text("TAP ANYWHERE TO START", modifier = Modifier.testTag("startPrompt"), fontSize = 12.sp, letterSpacing = 1.5.sp, fontWeight = FontWeight.Black, color = Color(0xFF19364A))
                Text("PERSONAL BEST  $bestScore", modifier = Modifier.padding(top = 10.dp), fontSize = 11.sp, letterSpacing = 1.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2D6E82))
            }
        }

        if (status == GameStatus.GAME_OVER) {
            Surface(
                modifier = Modifier.align(Alignment.Center).fillMaxWidth(0.84f),
                color = Color(0xFFFDF9EF),
                shape = RoundedCornerShape(28.dp),
                shadowElevation = 12.dp
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 25.dp, vertical = 27.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("FLIGHT ENDED", fontSize = 25.sp, letterSpacing = 0.5.sp, fontWeight = FontWeight.Black, color = Color(0xFF19364A))
                    Text("Every try makes you better.", modifier = Modifier.padding(top = 5.dp), fontSize = 13.sp, color = Color(0xFF5C7A83))
                    Spacer(Modifier.height(24.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                        ScoreStat("SCORE", score.toString())
                        Spacer(Modifier.width(28.dp))
                        ScoreStat("BEST", bestScore.toString())
                    }
                    Spacer(Modifier.height(25.dp))
                    Button(
                        onClick = {
                            engine.startGame(worldWidth)
                            score = 0
                            status = GameStatus.PLAYING
                        },
                        modifier = Modifier.fillMaxWidth().height(54.dp),
                        shape = RoundedCornerShape(17.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC83D), contentColor = Color(0xFF19364A))
                    ) {
                        Text("FLY AGAIN", fontSize = 14.sp, letterSpacing = 1.5.sp, fontWeight = FontWeight.Black)
                    }
                    Text("Tap the sky whenever you are ready", modifier = Modifier.padding(top = 12.dp), fontSize = 11.sp, color = Color(0xFF6C898E), textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Composable
private fun ScoreStat(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, fontSize = 11.sp, letterSpacing = 1.5.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF5C7A83))
        Text(value, modifier = Modifier.padding(top = 4.dp), fontSize = 32.sp, fontWeight = FontWeight.Black, color = Color(0xFF19364A))
    }
}
