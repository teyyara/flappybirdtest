package com.teyyara.flappybirdtest

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
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
    val lifecycleOwner = LocalLifecycleOwner.current
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val toneGenerator = remember(context) {
        ToneGenerator(AudioManager.STREAM_MUSIC, 35)
    }
    DisposableEffect(toneGenerator) {
        onDispose { toneGenerator.release() }
    }

    var status by remember { mutableStateOf(GameStatus.READY) }
    var score by remember { mutableIntStateOf(0) }
    var bestScore by remember { mutableIntStateOf(preferences.getInt("best_score", 0)) }
    var soundEnabled by remember { mutableStateOf(preferences.getBoolean("sound_enabled", true)) }
    var selectedDifficulty by remember {
        mutableStateOf(FlightDifficulty.fromPreference(preferences.getString("flight_difficulty", FlightDifficulty.CLASSIC.name)))
    }
    var hasUsedContinue by remember { mutableStateOf(false) }
    var showHowToPlay by remember { mutableStateOf(false) }
    var worldWidth by remember { androidx.compose.runtime.mutableFloatStateOf(360f) }

    val playSound: (Int, Int) -> Unit = { tone, durationMillis ->
        if (soundEnabled) toneGenerator.startTone(tone, durationMillis)
    }
    val startNewRun = {
        engine.selectDifficulty(selectedDifficulty)
        engine.startGame(worldWidth)
        score = 0
        hasUsedContinue = false
        status = GameStatus.PLAYING
        playSound(ToneGenerator.TONE_PROP_BEEP, 55)
    }
    val returnToMenu = {
        engine.returnToMenu(worldWidth)
        score = 0
        hasUsedContinue = false
        status = GameStatus.READY
    }
    val toggleSound = {
        val nextSoundState = !soundEnabled
        soundEnabled = nextSoundState
        preferences.edit().putBoolean("sound_enabled", nextSoundState).apply()
        if (nextSoundState) toneGenerator.startTone(ToneGenerator.TONE_PROP_ACK, 60)
    }

    // Never let a flight keep advancing underneath another app or a locked screen.
    DisposableEffect(lifecycleOwner, engine) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_PAUSE && engine.status == GameStatus.PLAYING) {
                engine.pauseGame()
                status = GameStatus.PAUSED
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    // Android back follows the same navigation rules as the visible controls.
    BackHandler(enabled = showHowToPlay || status != GameStatus.READY) {
        if (showHowToPlay) {
            showHowToPlay = false
        } else {
            when (status) {
                GameStatus.PLAYING -> {
                    engine.pauseGame()
                    status = GameStatus.PAUSED
                }
                GameStatus.PAUSED, GameStatus.GAME_OVER -> returnToMenu()
                GameStatus.READY -> Unit
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GameCanvas(
            engine = engine,
            status = status,
            modifier = Modifier.fillMaxSize().testTag("gameCanvas"),
            onViewportWidthChanged = { worldWidth = it },
            onStatusChanged = { newStatus ->
                status = newStatus
                if (newStatus == GameStatus.GAME_OVER) {
                    playSound(ToneGenerator.TONE_PROP_NACK, 160)
                }
            },
            onScoreChanged = { newScore ->
                score = newScore
                if (newScore > bestScore) {
                    bestScore = newScore
                    preferences.edit().putInt("best_score", newScore).apply()
                }
                playSound(ToneGenerator.TONE_PROP_ACK, 70)
            },
            onFlap = {
                engine.flap()
                playSound(ToneGenerator.TONE_PROP_BEEP, 35)
            }
        )

        if (status == GameStatus.PLAYING) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 20.dp).testTag("scoreHud"),
                verticalAlignment = Alignment.Top
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Surface(color = Color(0xEE19364A), shape = RoundedCornerShape(18.dp)) {
                        Column(
                            modifier = Modifier.padding(horizontal = 18.dp, vertical = 6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("SCORE", fontSize = 9.sp, letterSpacing = 2.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFFB8EAF3))
                            Text(score.toString(), fontSize = 27.sp, lineHeight = 29.sp, fontWeight = FontWeight.Black, color = Color.White)
                        }
                    }
                    Text("BEST  $bestScore", modifier = Modifier.padding(top = 6.dp), fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.5.sp, color = Color(0xFF19364A))
                }
                Spacer(Modifier.weight(1f))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    SoundToggleButton(
                        enabled = soundEnabled,
                        compact = true,
                        onToggle = toggleSound,
                        modifier = Modifier.size(width = 54.dp, height = 43.dp)
                    )
                    Button(
                        onClick = {
                            engine.pauseGame()
                            status = GameStatus.PAUSED
                        },
                        modifier = Modifier.size(width = 62.dp, height = 43.dp).testTag("pauseButton").semantics {
                            contentDescription = "Pause game"
                        },
                        shape = RoundedCornerShape(14.dp),
                        contentPadding = PaddingValues(horizontal = 4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xEE19364A), contentColor = Color.White)
                    ) {
                        Text("Ⅱ", fontSize = 18.sp, fontWeight = FontWeight.Black)
                    }
                }
            }
        }

        if (status == GameStatus.READY) {
            Column(
                modifier = Modifier.align(Alignment.Center).offset(y = (-8).dp).fillMaxWidth()
                    .heightIn(max = screenHeight * 0.88f).verticalScroll(rememberScrollState()).padding(horizontal = 28.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Surface(color = Color(0xE6FFFFFF), shape = RoundedCornerShape(28.dp), shadowElevation = 14.dp) {
                    Column(
                        modifier = Modifier.padding(horizontal = 25.dp, vertical = 25.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "SKY HOPPER",
                            modifier = Modifier.testTag("readyTitle"),
                            fontSize = 35.sp,
                            lineHeight = 40.sp,
                            letterSpacing = 1.5.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF19364A),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            "FLAP. FLOAT. FLY.",
                            modifier = Modifier.padding(top = 5.dp),
                            fontSize = 11.sp,
                            letterSpacing = 3.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF2D6E82)
                        )
                        Spacer(Modifier.height(22.dp))
                        Surface(color = Color(0xFFF0FAF8), shape = RoundedCornerShape(18.dp)) {
                            Column(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("YOUR LITTLE ADVENTURE", fontSize = 10.sp, letterSpacing = 1.3.sp, fontWeight = FontWeight.Black, color = Color(0xFF4A8A91))
                                Text(
                                    "Tap to flap. Slip through the pipes.",
                                    modifier = Modifier.padding(top = 7.dp),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF19364A),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                        Spacer(Modifier.height(17.dp))
                        DifficultySelector(
                            selected = selectedDifficulty,
                            onSelect = { selection ->
                                selectedDifficulty = selection
                                engine.selectDifficulty(selection)
                                preferences.edit().putString("flight_difficulty", selection.name).apply()
                            }
                        )
                        Spacer(Modifier.height(19.dp))
                        ActionButton(
                            label = "START FLIGHT",
                            testTag = "playButton",
                            onClick = startNewRun,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(10.dp))
                        SoundToggleButton(
                            enabled = soundEnabled,
                            compact = false,
                            onToggle = toggleSound,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(9.dp))
                        ActionButton(
                            label = "HOW TO PLAY",
                            testTag = "howToPlayButton",
                            onClick = { showHowToPlay = true },
                            primary = false,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            "PERSONAL BEST  $bestScore",
                            modifier = Modifier.padding(top = 16.dp),
                            fontSize = 10.sp,
                            letterSpacing = 1.1.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2D6E82)
                        )
                    }
                }
            }
        }

        if (showHowToPlay) {
            OverlayCard {
                Text("HOW TO PLAY", fontSize = 25.sp, letterSpacing = 0.6.sp, fontWeight = FontWeight.Black, color = Color(0xFF19364A))
                Text("A tiny bird. A big sky. One more try.", modifier = Modifier.padding(top = 5.dp), fontSize = 12.sp, color = Color(0xFF5C7A83), textAlign = TextAlign.Center)
                Spacer(Modifier.height(19.dp))
                InstructionRow("01", "TAP TO FLAP", "Tap anywhere in the sky to lift your bird.")
                Spacer(Modifier.height(13.dp))
                InstructionRow("02", "THREAD THE PIPES", "Fly through each gap. Every pipe pair adds a point.")
                Spacer(Modifier.height(13.dp))
                InstructionRow("03", "PAUSE WHENEVER", "Use the pause button or your phone's Back action.")
                Spacer(Modifier.height(13.dp))
                InstructionRow("04", "ONE EXTRA CHANCE", "After a crash, continue once to keep your score.")
                Spacer(Modifier.height(21.dp))
                ActionButton(
                    label = "GOT IT, LET'S FLY",
                    testTag = "howToPlayCloseButton",
                    onClick = { showHowToPlay = false },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        if (status == GameStatus.PAUSED) {
            OverlayCard {
                Text("FLIGHT PAUSED", fontSize = 24.sp, letterSpacing = 0.4.sp, fontWeight = FontWeight.Black, color = Color(0xFF19364A))
                Text("Catch your breath, captain.", modifier = Modifier.padding(top = 5.dp), fontSize = 13.sp, color = Color(0xFF5C7A83))
                Spacer(Modifier.height(23.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    ScoreStat("SCORE", score.toString())
                    Spacer(Modifier.width(30.dp))
                    ScoreStat("BEST", bestScore.toString())
                }
                Spacer(Modifier.height(23.dp))
                ActionButton(
                    label = "RESUME FLIGHT",
                    testTag = "resumeButton",
                    onClick = {
                        engine.resumeGame()
                        status = GameStatus.PLAYING
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(9.dp))
                ActionButton(
                    label = "RESTART RUN",
                    testTag = "restartButton",
                    onClick = startNewRun,
                    primary = false,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(9.dp))
                ActionButton(
                    label = "MAIN MENU",
                    testTag = "mainMenuButton",
                    onClick = returnToMenu,
                    primary = false,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(10.dp))
                SoundToggleButton(
                    enabled = soundEnabled,
                    compact = false,
                    onToggle = toggleSound,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        if (status == GameStatus.GAME_OVER) {
            OverlayCard {
                Text("FLIGHT ENDED", fontSize = 25.sp, letterSpacing = 0.5.sp, fontWeight = FontWeight.Black, color = Color(0xFF19364A))
                Text("Every try makes you better.", modifier = Modifier.padding(top = 5.dp), fontSize = 13.sp, color = Color(0xFF5C7A83))
                Spacer(Modifier.height(22.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    ScoreStat("SCORE", score.toString())
                    Spacer(Modifier.width(30.dp))
                    ScoreStat("BEST", bestScore.toString())
                }
                Spacer(Modifier.height(22.dp))
                if (!hasUsedContinue) {
                    ActionButton(
                        label = "CONTINUE RUN",
                        testTag = "continueButton",
                        onClick = {
                            if (engine.continueAfterGameOver(worldWidth)) {
                                hasUsedContinue = true
                                score = engine.score
                                status = GameStatus.PLAYING
                                playSound(ToneGenerator.TONE_PROP_ACK, 75)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        "Free in this build · rewarded-ad slot reserved for later",
                        modifier = Modifier.padding(top = 7.dp),
                        fontSize = 9.sp,
                        color = Color(0xFF6C898E),
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(8.dp))
                } else {
                    Text(
                        "CONTINUE USED FOR THIS RUN",
                        modifier = Modifier.padding(vertical = 7.dp),
                        fontSize = 10.sp,
                        letterSpacing = 0.8.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6C898E),
                        textAlign = TextAlign.Center
                    )
                }
                ActionButton(
                    label = "FLY AGAIN",
                    testTag = "flyAgainButton",
                    onClick = startNewRun,
                    primary = false,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(9.dp))
                ActionButton(
                    label = "MAIN MENU",
                    testTag = "mainMenuButton",
                    onClick = returnToMenu,
                    primary = false,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(10.dp))
                SoundToggleButton(
                    enabled = soundEnabled,
                    compact = false,
                    onToggle = toggleSound,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun BoxScope.OverlayCard(content: @Composable () -> Unit) {
    Surface(
        modifier = Modifier.align(Alignment.Center).fillMaxWidth(0.86f)
            .heightIn(max = (LocalConfiguration.current.screenHeightDp * 0.84f).dp),
        color = Color(0xFFFDF9EF),
        shape = RoundedCornerShape(28.dp),
        shadowElevation = 16.dp
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 23.dp, vertical = 24.dp).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
    }
}

@Composable
private fun DifficultySelector(
    selected: FlightDifficulty,
    onSelect: (FlightDifficulty) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            "CHOOSE YOUR FLIGHT",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 10.sp,
            letterSpacing = 1.2.sp,
            fontWeight = FontWeight.Black,
            color = Color(0xFF4A8A91),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            FlightDifficulty.values().forEach { difficulty ->
                val isSelected = difficulty == selected
                Button(
                    onClick = { onSelect(difficulty) },
                    modifier = Modifier.weight(1f).height(43.dp).testTag("difficulty${difficulty.name}"),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 3.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) Color(0xFFFFC83D) else Color(0xFFE7F3F1),
                        contentColor = Color(0xFF19364A)
                    )
                ) {
                    Text(
                        difficulty.label,
                        fontSize = 9.sp,
                        letterSpacing = 0.4.sp,
                        fontWeight = FontWeight.Black,
                        maxLines = 1
                    )
                }
            }
        }
        Text(
            selected.description,
            modifier = Modifier.fillMaxWidth().padding(top = 7.dp),
            fontSize = 10.sp,
            color = Color(0xFF5C7A83),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun SoundToggleButton(
    enabled: Boolean,
    compact: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onToggle,
        modifier = modifier
            .height(if (compact) 43.dp else 46.dp)
            .testTag("soundToggle")
            .semantics { contentDescription = if (enabled) "Mute sound" else "Enable sound" },
        shape = RoundedCornerShape(14.dp),
        contentPadding = PaddingValues(horizontal = if (compact) 4.dp else 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (compact) Color(0xEE19364A) else Color(0xFF2D6E82),
            contentColor = Color.White
        )
    ) {
        Text(
            text = if (compact) {
                if (enabled) "♫ ON" else "♫ OFF"
            } else {
                if (enabled) "♫  SOUND: ON" else "♫  SOUND: OFF"
            },
            fontSize = if (compact) 10.sp else 11.sp,
            letterSpacing = 0.7.sp,
            fontWeight = FontWeight.Black,
            maxLines = 1
        )
    }
}

@Composable
private fun ActionButton(
    label: String,
    testTag: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    primary: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(52.dp).testTag(testTag),
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(horizontal = 18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (primary) Color(0xFFFFC83D) else Color(0xFF2D6E82),
            contentColor = if (primary) Color(0xFF19364A) else Color.White
        )
    ) {
        Text(label, fontSize = 13.sp, letterSpacing = 1.2.sp, fontWeight = FontWeight.Black)
    }
}

@Composable
private fun InstructionRow(number: String, title: String, description: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Surface(color = Color(0xFFFFE29B), shape = RoundedCornerShape(12.dp)) {
            Text(
                number,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                fontSize = 12.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF19364A)
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontSize = 11.sp, letterSpacing = 0.8.sp, fontWeight = FontWeight.Black, color = Color(0xFF19364A))
            Text(description, modifier = Modifier.padding(top = 3.dp), fontSize = 12.sp, color = Color(0xFF5C7A83), lineHeight = 16.sp)
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
