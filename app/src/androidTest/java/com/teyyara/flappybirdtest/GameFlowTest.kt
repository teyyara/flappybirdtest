package com.teyyara.flappybirdtest

import androidx.compose.ui.test.click
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTouchInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GameFlowTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun tappingReadyScreenStartsTheGameAndShowsScoreHud() {
        // The running game owns a continuous frame loop. Freeze auto-advance and explicitly
        // advance a frame after tapping so the resulting Compose state is applied predictably.
        composeRule.mainClock.autoAdvance = false

        composeRule.onNodeWithTag("readyTitle").assertExists()
        composeRule.onNodeWithTag("gameCanvas").performTouchInput { click() }
        composeRule.mainClock.advanceTimeByFrame()

        composeRule.onNodeWithTag("scoreHud").assertExists()
        composeRule.onNodeWithTag("readyTitle").assertDoesNotExist()
    }
}
