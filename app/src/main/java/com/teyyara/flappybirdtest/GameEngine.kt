package com.teyyara.flappybirdtest

import kotlin.random.Random

enum class GameStatus { READY, PLAYING, PAUSED, GAME_OVER }

internal data class Pipe(
    var x: Float,
    val gapCenter: Float,
    var scored: Boolean = false
)

/** Frame-step gameplay rules, separated from rendering so the core can be JVM-tested. */
class GameEngine(seed: Int = 2026) {
    var status: GameStatus = GameStatus.READY
        private set
    var score: Int = 0
        private set
    internal var birdY: Float = VIRTUAL_HEIGHT * 0.46f
    internal var birdVelocity: Float = 0f
    internal var elapsedSeconds: Float = 0f
    internal var worldOffset: Float = 0f
    internal var worldWidth: Float = 360f
    internal val pipes = mutableListOf<Pipe>()
    private val random = Random(seed)

    val birdX: Float
        get() = worldWidth * BIRD_X_RATIO

    fun startGame(viewportWidth: Float) {
        worldWidth = viewportWidth.coerceAtLeast(250f)
        status = GameStatus.PLAYING
        score = 0
        birdY = VIRTUAL_HEIGHT * 0.46f
        birdVelocity = FLAP_VELOCITY
        elapsedSeconds = 0f
        worldOffset = 0f
        pipes.clear()
        pipes += Pipe(worldWidth + FIRST_PIPE_OFFSET, nextGapCenter())
    }

    fun pauseGame() {
        if (status == GameStatus.PLAYING) status = GameStatus.PAUSED
    }

    fun resumeGame() {
        if (status == GameStatus.PAUSED) status = GameStatus.PLAYING
    }

    /** Return to the idle main menu without leaving stale run state on the game canvas. */
    fun returnToMenu(viewportWidth: Float) {
        worldWidth = viewportWidth.coerceAtLeast(250f)
        status = GameStatus.READY
        score = 0
        birdY = VIRTUAL_HEIGHT * 0.46f
        birdVelocity = 0f
        elapsedSeconds = 0f
        worldOffset = 0f
        pipes.clear()
    }

    /**
     * Resume after a crash while preserving the current score and run time.
     * The bird is respawned safely and obstacles are regenerated ahead of it.
     * The UI owns the product rule that this action may be used once per run.
     */
    fun continueAfterGameOver(viewportWidth: Float): Boolean {
        if (status != GameStatus.GAME_OVER) return false

        worldWidth = viewportWidth.coerceAtLeast(250f)
        status = GameStatus.PLAYING
        birdY = GROUND_TOP * CONTINUE_SPAWN_HEIGHT_RATIO
        birdVelocity = FLAP_VELOCITY * CONTINUE_FLAP_STRENGTH
        pipes.clear()
        pipes += Pipe(worldWidth + FIRST_PIPE_OFFSET, nextGapCenter())
        return true
    }

    fun flap() {
        if (status == GameStatus.PLAYING) birdVelocity = FLAP_VELOCITY
    }

    fun update(deltaSeconds: Float, viewportWidth: Float) {
        if (status != GameStatus.PLAYING) return
        worldWidth = viewportWidth.coerceAtLeast(250f)
        val dt = deltaSeconds.coerceIn(0f, MAX_FRAME_SECONDS)
        if (dt <= 0f) return

        elapsedSeconds += dt
        worldOffset += PIPE_SPEED * dt
        birdVelocity += GRAVITY * dt
        birdY += birdVelocity * dt
        pipes.forEach { it.x -= PIPE_SPEED * dt }

        val lastPipe = pipes.lastOrNull()
        if (lastPipe == null) {
            pipes += Pipe(worldWidth + FIRST_PIPE_OFFSET, nextGapCenter())
        } else if (lastPipe.x < worldWidth - PIPE_SPACING) {
            pipes += Pipe(lastPipe.x + PIPE_SPACING, nextGapCenter())
        }

        val birdLeft = birdX - BIRD_RADIUS
        val birdRight = birdX + BIRD_RADIUS
        val birdTop = birdY - BIRD_RADIUS
        val birdBottom = birdY + BIRD_RADIUS
        if (birdTop <= 0f || birdBottom >= GROUND_TOP) {
            status = GameStatus.GAME_OVER
            return
        }

        for (pipe in pipes) {
            val gapTop = pipe.gapCenter - PIPE_GAP_HEIGHT / 2f
            val gapBottom = pipe.gapCenter + PIPE_GAP_HEIGHT / 2f
            val horizontalOverlap = birdRight > pipe.x && birdLeft < pipe.x + PIPE_WIDTH
            if (horizontalOverlap && (birdTop < gapTop || birdBottom > gapBottom)) {
                status = GameStatus.GAME_OVER
                return
            }
            if (!pipe.scored && pipe.x + PIPE_WIDTH < birdX) {
                pipe.scored = true
                score += 1
            }
        }
        pipes.removeAll { it.x + PIPE_WIDTH < -20f }
    }

    private fun nextGapCenter(): Float = random.nextFloat() * GAP_CENTER_RANGE + GAP_CENTER_MIN

    companion object {
        const val VIRTUAL_HEIGHT = 640f
        const val GROUND_TOP = 568f
        const val PIPE_WIDTH = 62f
        const val PIPE_GAP_HEIGHT = 168f
        const val PIPE_SPACING = 232f
        const val PIPE_SPEED = 150f
        const val BIRD_RADIUS = 14f
        const val BIRD_X_RATIO = 0.28f
        const val FIRST_PIPE_OFFSET = 86f
        const val GRAVITY = 1450f
        const val FLAP_VELOCITY = -425f
        const val MAX_FRAME_SECONDS = 1f / 30f
        private const val CONTINUE_SPAWN_HEIGHT_RATIO = 0.43f
        private const val CONTINUE_FLAP_STRENGTH = 0.72f
        private const val GAP_CENTER_MIN = 175f
        private const val GAP_CENTER_RANGE = 198f
    }
}
