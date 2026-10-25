package com.teyyara.flappybirdtest

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class GameEngineTest {
    @Test
    fun newGameStartsReady() {
        val engine = GameEngine(seed = 1)
        assertEquals(GameStatus.READY, engine.status)
        assertEquals(0, engine.score)
    }

    @Test
    fun startingRoundAppliesUpwardFlapAndCreatesPipe() {
        val engine = GameEngine(seed = 1)
        engine.startGame(360f)
        assertEquals(GameStatus.PLAYING, engine.status)
        assertEquals(1, engine.pipes.size)
        assertTrue(engine.birdVelocity < 0f)
    }

    @Test
    fun pipePassIncrementsScoreOnlyOnce() {
        val engine = GameEngine(seed = 1)
        engine.startGame(360f)
        engine.birdY = 300f
        engine.birdVelocity = 0f
        engine.pipes.clear()
        engine.pipes += Pipe(engine.birdX - GameEngine.PIPE_WIDTH - 1f, 300f)

        engine.update(1f / 60f, 360f)
        assertEquals(1, engine.score)
        engine.update(1f / 60f, 360f)
        assertEquals(1, engine.score)
    }

    @Test
    fun ceilingCollisionEndsRound() {
        val engine = GameEngine(seed = 1)
        engine.startGame(360f)
        engine.birdY = GameEngine.BIRD_RADIUS - 1f
        engine.birdVelocity = -100f
        engine.update(1f / 60f, 360f)
        assertEquals(GameStatus.GAME_OVER, engine.status)
    }

    @Test
    fun groundCollisionEndsRound() {
        val engine = GameEngine(seed = 1)
        engine.startGame(360f)
        engine.birdY = GameEngine.GROUND_TOP - GameEngine.BIRD_RADIUS
        engine.birdVelocity = 160f
        engine.update(1f / 60f, 360f)
        assertEquals(GameStatus.GAME_OVER, engine.status)
    }

    @Test
    fun restartingAfterGameOverCreatesFreshRound() {
        val engine = GameEngine(seed = 1)
        engine.startGame(360f)
        engine.birdY = GameEngine.GROUND_TOP
        engine.update(1f / 60f, 360f)
        assertEquals(GameStatus.GAME_OVER, engine.status)
        engine.startGame(360f)
        assertEquals(GameStatus.PLAYING, engine.status)
        assertEquals(0, engine.score)
        assertEquals(1, engine.pipes.size)
    }

    @Test
    fun pausingFreezesPhysicsUntilResumed() {
        val engine = GameEngine(seed = 1)
        engine.startGame(360f)
        val yBeforePause = engine.birdY

        engine.pauseGame()
        engine.update(1f / 30f, 360f)

        assertEquals(GameStatus.PAUSED, engine.status)
        assertEquals(yBeforePause, engine.birdY)

        engine.resumeGame()
        engine.update(1f / 30f, 360f)
        assertEquals(GameStatus.PLAYING, engine.status)
        assertTrue(engine.birdY != yBeforePause)
    }

    @Test
    fun continuingAfterGameOverPreservesScoreAndCreatesSafeRunway() {
        val engine = GameEngine(seed = 1)
        engine.startGame(360f)
        engine.birdY = 300f
        engine.birdVelocity = 0f
        engine.pipes.clear()
        engine.pipes += Pipe(engine.birdX - GameEngine.PIPE_WIDTH - 1f, 300f)
        engine.update(1f / 60f, 360f)
        assertEquals(1, engine.score)

        engine.birdY = GameEngine.GROUND_TOP
        engine.birdVelocity = 0f
        engine.update(1f / 60f, 360f)
        assertEquals(GameStatus.GAME_OVER, engine.status)

        assertTrue(engine.continueAfterGameOver(360f))
        assertEquals(GameStatus.PLAYING, engine.status)
        assertEquals(1, engine.score)
        assertEquals(1, engine.pipes.size)
        assertTrue(engine.pipes.single().x > engine.worldWidth)
        assertTrue(engine.birdY > GameEngine.BIRD_RADIUS)
        assertTrue(engine.birdY < GameEngine.GROUND_TOP - GameEngine.BIRD_RADIUS)
    }

    @Test
    fun continuingWhileNotGameOverIsRejected() {
        val engine = GameEngine(seed = 1)
        engine.startGame(360f)
        assertFalse(engine.continueAfterGameOver(360f))
        assertEquals(GameStatus.PLAYING, engine.status)
    }

    @Test
    fun returningToMenuClearsThePreviousRun() {
        val engine = GameEngine(seed = 1)
        engine.startGame(360f)
        engine.returnToMenu(360f)
        assertEquals(GameStatus.READY, engine.status)
        assertEquals(0, engine.score)
        assertTrue(engine.pipes.isEmpty())
    }
}
