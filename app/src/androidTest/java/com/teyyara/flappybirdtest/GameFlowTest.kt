package com.teyyara.flappybirdtest

import androidx.compose.ui.test.assertDoesNotExist
import androidx.compose.ui.test.assertExists
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GameFlowTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun mainMenuShowsStartAndPersistentSoundControl() {
        composeRule.mainClock.autoAdvance = false

        composeRule.onNodeWithTag("readyTitle").assertExists()
        composeRule.onNodeWithTag("playButton").assertExists()
        composeRule.onNodeWithTag("soundToggle").assertExists()
    }

    @Test
    fun playerCanStartPauseAndResumeAFlight() {
        composeRule.mainClock.autoAdvance = false

        composeRule.onNodeWithTag("playButton").performClick()
        composeRule.mainClock.advanceTimeByFrame()
        composeRule.onNodeWithTag("scoreHud").assertExists()
        composeRule.onNodeWithTag("readyTitle").assertDoesNotExist()

        composeRule.onNodeWithTag("pauseButton").performClick()
        composeRule.onNodeWithTag("resumeButton").assertExists()
        composeRule.onNodeWithTag("scoreHud").assertDoesNotExist()

        composeRule.onNodeWithTag("resumeButton").performClick()
        composeRule.mainClock.advanceTimeByFrame()
        composeRule.onNodeWithTag("scoreHud").assertExists()
    }
}
