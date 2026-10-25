package com.teyyara.flappybirdtest

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.isActive
import kotlin.math.sin

@Composable
fun GameCanvas(
    engine: GameEngine,
    status: GameStatus,
    modifier: Modifier = Modifier,
    onViewportWidthChanged: (Float) -> Unit,
    onStatusChanged: (GameStatus) -> Unit,
    onScoreChanged: (Int) -> Unit,
    onFlap: () -> Unit
) {
    var frameVersion by remember { mutableIntStateOf(0) }
    var viewportWidth by remember { mutableFloatStateOf(360f) }
    val currentViewportWidth by rememberUpdatedState(viewportWidth)
    val currentStatusCallback by rememberUpdatedState(onStatusChanged)
    val currentScoreCallback by rememberUpdatedState(onScoreChanged)
    val currentFlapCallback by rememberUpdatedState(onFlap)

    LaunchedEffect(engine, status) {
        if (status != GameStatus.PLAYING) return@LaunchedEffect
        var previousFrameNanos = 0L
        var lastReportedScore = engine.score
        while (isActive && engine.status == GameStatus.PLAYING) {
            val now = androidx.compose.runtime.withFrameNanos { it }
            val delta = if (previousFrameNanos == 0L) 0f else
                (now - previousFrameNanos) / 1_000_000_000f
            previousFrameNanos = now
            engine.update(delta, currentViewportWidth)
            frameVersion += 1
            if (engine.score != lastReportedScore) {
                lastReportedScore = engine.score
                currentScoreCallback(engine.score)
            }
            if (engine.status != GameStatus.PLAYING) currentStatusCallback(engine.status)
        }
    }

    Canvas(
        modifier = modifier
            .pointerInput(status) {
                detectTapGestures {
                    if (status == GameStatus.PLAYING) currentFlapCallback()
                }
            }
            .onSizeChanged { size: IntSize ->
                if (size.height > 0) {
                    val scaleFactor = size.height / GameEngine.VIRTUAL_HEIGHT
                    val logicalWidth = size.width / scaleFactor
                    if (logicalWidth > 0f && logicalWidth != viewportWidth) {
                        viewportWidth = logicalWidth
                        onViewportWidthChanged(logicalWidth)
                    }
                }
            }
    ) {
        val animationFrame = frameVersion
        if (animationFrame < 0) return@Canvas
        val scaleFactor = size.height / GameEngine.VIRTUAL_HEIGHT
        val logicalWidth = size.width / scaleFactor
        withTransform({ scale(scaleFactor, scaleFactor, pivot = Offset.Zero) }) {
            drawGameWorld(engine, logicalWidth)
        }
    }
}

private fun DrawScope.drawGameWorld(engine: GameEngine, width: Float) {
    val height = GameEngine.VIRTUAL_HEIGHT
    drawRect(
        brush = Brush.verticalGradient(
            colors = listOf(Color(0xFF72D4EF), Color(0xFFC9F1FB), Color(0xFFEAF9FC)),
            startY = 0f, endY = height
        ),
        size = Size(width, height)
    )
    drawCircle(Color(0xFFFFF0AE), radius = 35f, center = Offset(width * 0.79f, 104f))
    drawCircle(Color(0xFFFFF8D2), radius = 27f, center = Offset(width * 0.79f, 104f))

    val cloudTravel = engine.elapsedSeconds * 10f
    drawCloud(wrap(-32f - cloudTravel, width + 100f) - 20f, 148f, 0.95f)
    drawCloud(wrap(width * 0.58f - cloudTravel * 0.72f, width + 120f), 225f, 0.68f)
    drawCloud(wrap(width * 0.29f - cloudTravel * 0.5f, width + 140f), 92f, 0.48f)

    val farHill = Path().apply {
        moveTo(0f, 510f)
        quadraticTo(width * 0.22f, 425f, width * 0.42f, 496f)
        quadraticTo(width * 0.69f, 420f, width, 488f)
        lineTo(width, GameEngine.GROUND_TOP)
        lineTo(0f, GameEngine.GROUND_TOP)
        close()
    }
    drawPath(farHill, Color(0xFFB4E5A0))
    val nearHill = Path().apply {
        moveTo(0f, 535f)
        quadraticTo(width * 0.27f, 482f, width * 0.51f, 530f)
        quadraticTo(width * 0.77f, 477f, width, 522f)
        lineTo(width, GameEngine.GROUND_TOP)
        lineTo(0f, GameEngine.GROUND_TOP)
        close()
    }
    drawPath(nearHill, Color(0xFF87D17B))

    engine.pipes.forEach { pipe ->
        val gapTop = pipe.gapCenter - GameEngine.PIPE_GAP_HEIGHT / 2f
        val gapBottom = pipe.gapCenter + GameEngine.PIPE_GAP_HEIGHT / 2f
        val pw = GameEngine.PIPE_WIDTH
        drawRoundRect(Color(0xFF278C52), Offset(pipe.x - 2f, -2f), Size(pw + 4f, gapTop + 2f), CornerRadius(7f))
        drawRect(Color(0xFF3EAD62), Offset(pipe.x + 5f, 0f), Size(10f, (gapTop - 2f).coerceAtLeast(0f)))
        drawRoundRect(Color(0xFF58C96E), Offset(pipe.x - 6f, gapTop - 22f), Size(pw + 12f, 22f), CornerRadius(5f))
        drawRoundRect(Color(0xFF278C52), Offset(pipe.x - 6f, gapTop - 22f), Size(pw + 12f, 5f), CornerRadius(4f))
        drawRoundRect(Color(0xFF278C52), Offset(pipe.x - 2f, gapBottom), Size(pw + 4f, GameEngine.GROUND_TOP - gapBottom + 8f), CornerRadius(7f))
        drawRect(Color(0xFF3EAD62), Offset(pipe.x + 5f, gapBottom + 3f), Size(10f, (GameEngine.GROUND_TOP - gapBottom).coerceAtLeast(0f)))
        drawRoundRect(Color(0xFF58C96E), Offset(pipe.x - 6f, gapBottom), Size(pw + 12f, 22f), CornerRadius(5f))
        drawRoundRect(Color(0xFF278C52), Offset(pipe.x - 6f, gapBottom + 17f), Size(pw + 12f, 5f), CornerRadius(4f))
    }

    drawRect(Color(0xFFE9D68E), Offset(0f, GameEngine.GROUND_TOP), Size(width, height - GameEngine.GROUND_TOP))
    drawRect(Color(0xFF77C957), Offset(0f, GameEngine.GROUND_TOP), Size(width, 10f))
    drawRect(Color(0xFF4FA747), Offset(0f, GameEngine.GROUND_TOP + 8f), Size(width, 3f))
    var stripeX = -(engine.worldOffset % 42f)
    while (stripeX < width + 42f) {
        drawRoundRect(Color(0xFFC8B76F), Offset(stripeX, GameEngine.GROUND_TOP + 23f), Size(22f, 5f), CornerRadius(3f))
        stripeX += 42f
    }

    val rotation = if (engine.status == GameStatus.READY) sin(engine.elapsedSeconds * 2f) * 4f
        else (engine.birdVelocity / 9f).coerceIn(-24f, 75f)
    rotate(rotation, pivot = Offset(engine.birdX, engine.birdY)) {
        val bx = engine.birdX
        val by = engine.birdY
        val tail = Path().apply {
            moveTo(bx - 12f, by + 1f)
            lineTo(bx - 25f, by - 5f)
            lineTo(bx - 22f, by + 8f)
            close()
        }
        drawPath(tail, Color(0xFFEAA72E))
        drawOval(Color(0xFFF6B932), Offset(bx - 17f, by - 13f), Size(35f, 27f))
        drawOval(Color(0xFFFFD34E), Offset(bx - 12f, by - 13f), Size(25f, 20f))
        drawOval(Color(0xFFEA9D28), Offset(bx - 9f, by + 1f), Size(16f, 10f))
        drawOval(Color(0xFFF1A92E), Offset(bx - 10f, by - 1f), Size(16f, 11f))
        val beak = Path().apply {
            moveTo(bx + 12f, by - 2f)
            lineTo(bx + 28f, by + 3f)
            lineTo(bx + 12f, by + 8f)
            close()
        }
        drawPath(beak, Color(0xFFF47A38))
        drawCircle(Color(0xFFFFFDF1), 6.3f, Offset(bx + 7f, by - 5f))
        drawCircle(Color(0xFF19364A), 3.1f, Offset(bx + 9f, by - 4.5f))
        drawCircle(Color(0xFFFFFFFF), 1.05f, Offset(bx + 10f, by - 5.5f))
    }
}

private fun DrawScope.drawCloud(x: Float, y: Float, scale: Float) {
    val cloudColor = Color(0xDFFFFFFF)
    drawCircle(cloudColor, 17f * scale, Offset(x, y + 2f * scale))
    drawCircle(cloudColor, 23f * scale, Offset(x + 18f * scale, y - 5f * scale))
    drawCircle(cloudColor, 17f * scale, Offset(x + 40f * scale, y + 2f * scale))
    drawRoundRect(
        color = cloudColor,
        topLeft = Offset(x - 1f * scale, y - 1f * scale),
        size = Size(43f * scale, 20f * scale),
        cornerRadius = CornerRadius(10f * scale)
    )
}

private fun wrap(value: Float, period: Float): Float = ((value % period) + period) % period
