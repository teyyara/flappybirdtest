package com.teyyara.flappybirdtest

import kotlin.random.Random

enum class GameStatus { READY, PLAYING, PAUSED, GAME_OVER }

/** Presets intentionally change both flight physics and the obstacle rhythm. */
enum class FlightDifficulty(
    val label: String,
    val description: String,
    val pipeSpeed: Float,
    val pipeGapHeight: Float,
    val gravity: Float,
    val flapVelocity: Float,
    val pipeSpacing: Float
) {
    CHILL(
        label = "CHILL",
        description = "Roomier gaps and a gentler pace",
        pipeSpeed = 130f,
        pipeGapHeight = 196f,
        gravity = 1_280f,
        flapVelocity = -400f,
        pipeSpacing = 244f
    ),
    CLASSIC(
        label = "CLASSIC",
        description = "The balanced Sky Hopper flight",
        pipeSpeed = 150f,
        pipeGapHeight = 168f,
        gravity = 1_450f,
        flapVelocity = -425f,
        pipeSpacing = 232f
    ),
    WILD(
        label = "WILD",
        description = "Tighter gaps and faster pipes",
        pipeSpeed = 178f,
        pipeGapHeight = 144f,
        gravity = 1_560f,
        flapVelocity = -445f,
        pipeSpacing = 222f
    );

    companion object {
        fun fromPreference(value: String?): FlightDifficulty =
            values().firstOrNull { it.name == value } ?: CLASSIC
    }
}

enum class BirdAccessory { NONE, STAR, LEAF, CREST }

/** Hand-drawn playable birds. Colors and accessories are used by both the menu preview and game. */
enum class BirdCharacter(
    val displayName: String,
    val tagline: String,
    val bodyColor: Int,
    val highlightColor: Int,
    val wingColor: Int,
    val tailColor: Int,
    val beakColor: Int,
    val accessory: BirdAccessory
) {
    SUNNY(
        "SUNNY", "The classic golden flyer",
        0xFFF6B932.toInt(), 0xFFFFD34E.toInt(), 0xFFF1A92E.toInt(),
        0xFFEAA72E.toInt(), 0xFFF47A38.toInt(), BirdAccessory.NONE
    ),
    BERRY(
        "BERRY", "A little purple daydream",
        0xFF9C6ADE.toInt(), 0xFFC3A0F2.toInt(), 0xFF7F4BC7.toInt(),
        0xFF7640B2.toInt(), 0xFFFF9D70.toInt(), BirdAccessory.STAR
    ),
    MINT(
        "MINT", "Cool, calm, and leafy",
        0xFF37C6B1.toInt(), 0xFF7DE8D9.toInt(), 0xFF229C8D.toInt(),
        0xFF178A7C.toInt(), 0xFFFFAB5B.toInt(), BirdAccessory.LEAF
    ),
    EMBER(
        "EMBER", "A tiny bird with big fire",
        0xFFF17855.toInt(), 0xFFFFAA6F.toInt(), 0xFFD95846.toInt(),
        0xFFC94D3B.toInt(), 0xFFFFCF5A.toInt(), BirdAccessory.CREST
    );

    companion object {
        fun fromPreference(value: String?): BirdCharacter =
            values().firstOrNull { it.name == value } ?: SUNNY
    }
}

internal data class Pipe(
    var x: Float,
    val gapCenter: Float,
    var scored: Boolean = false,
    val gapHeight: Float = GameEngine.PIPE_GAP_HEIGHT
)

/** Frame-step gameplay rules, separated from rendering so the core can be JVM-tested. */
class GameEngine(seed: Int = 2026) {
    var status: GameStatus = GameStatus.READY
        private set
    var score: Int = 0
        private set
    var difficulty: FlightDifficulty = FlightDifficulty.CLASSIC
        private set
    var character: BirdCharacter = BirdCharacter.SUNNY
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

    /** Mode selection is a menu action; a live or paused run keeps its rules. */
    fun selectDifficulty(selection: FlightDifficulty) {
        if (status == GameStatus.READY || status == GameStatus.GAME_OVER) {
            difficulty = selection
        }
    }

    /** Character selection is a menu action; current flights keep their chosen bird. */
    fun selectCharacter(selection: BirdCharacter) {
        if (status == GameStatus.READY || status == GameStatus.GAME_OVER) {
            character = selection
        }
    }

    fun startGame(viewportWidth: Float) {
        worldWidth = viewportWidth.coerceAtLeast(250f)
        status = GameStatus.PLAYING
        score = 0
        birdY = VIRTUAL_HEIGHT * 0.46f
        birdVelocity = difficulty.flapVelocity
        elapsedSeconds = 0f
        worldOffset = 0f
        pipes.clear()
        pipes += createPipe(worldWidth + FIRST_PIPE_OFFSET)
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
     * Resume after a crash while preserving the current score and selected rules.
     * The bird is respawned safely and obstacles are regenerated ahead of it.
     * The UI owns the product rule that this action may be used once per run.
     */
    fun continueAfterGameOver(viewportWidth: Float): Boolean {
        if (status != GameStatus.GAME_OVER) return false

        worldWidth = viewportWidth.coerceAtLeast(250f)
        status = GameStatus.PLAYING
        birdY = GROUND_TOP * CONTINUE_SPAWN_HEIGHT_RATIO
        birdVelocity = difficulty.flapVelocity * CONTINUE_FLAP_STRENGTH
        pipes.clear()
        pipes += createPipe(worldWidth + FIRST_PIPE_OFFSET)
        return true
    }

    fun flap() {
        if (status == GameStatus.PLAYING) birdVelocity = difficulty.flapVelocity
    }

    fun update(deltaSeconds: Float, viewportWidth: Float) {
        if (status != GameStatus.PLAYING) return
        worldWidth = viewportWidth.coerceAtLeast(250f)
        val dt = deltaSeconds.coerceIn(0f, MAX_FRAME_SECONDS)
        if (dt <= 0f) return

        elapsedSeconds += dt
        worldOffset += difficulty.pipeSpeed * dt
        birdVelocity += difficulty.gravity * dt
        birdY += birdVelocity * dt
        pipes.forEach { it.x -= difficulty.pipeSpeed * dt }

        val lastPipe = pipes.lastOrNull()
        if (lastPipe == null) {
            pipes += createPipe(worldWidth + FIRST_PIPE_OFFSET)
        } else if (lastPipe.x < worldWidth - difficulty.pipeSpacing) {
            pipes += createPipe(lastPipe.x + difficulty.pipeSpacing)
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
            val gapTop = pipe.gapCenter - pipe.gapHeight / 2f
            val gapBottom = pipe.gapCenter + pipe.gapHeight / 2f
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

    private fun createPipe(x: Float): Pipe {
        val gapHeight = difficulty.pipeGapHeight
        return Pipe(
            x = x,
            gapCenter = nextGapCenter(gapHeight),
            gapHeight = gapHeight
        )
    }

    private fun nextGapCenter(gapHeight: Float): Float {
        val minCenter = GAP_CENTER_MIN
        val maxCenter = GROUND_TOP - gapHeight / 2f - GAP_BOTTOM_MARGIN
        val range = (maxCenter - minCenter).coerceAtLeast(1f)
        return minCenter + random.nextFloat() * range
    }

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
        const val GRAVITY = 1_450f
        const val FLAP_VELOCITY = -425f
        const val MAX_FRAME_SECONDS = 1f / 30f
        private const val CONTINUE_SPAWN_HEIGHT_RATIO = 0.43f
        private const val CONTINUE_FLAP_STRENGTH = 0.72f
        private const val GAP_CENTER_MIN = 175f
        private const val GAP_BOTTOM_MARGIN = 72f
    }
}
