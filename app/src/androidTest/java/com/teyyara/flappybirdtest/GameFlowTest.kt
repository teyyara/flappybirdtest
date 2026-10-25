package com.teyyara.flappybirdtest

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.lifecycle.Lifecycle
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GameFlowTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun mainMenuShowsStartAndSoundControls() {
        composeRule.mainClock.autoAdvance = false

        composeRule.onNodeWithTag("readyTitle").assertExists()
        composeRule.onNodeWithTag("playButton").assertExists()
        composeRule.onNodeWithTag("soundToggle").assertExists()
        composeRule.onNodeWithTag("difficultyCHILL").assertExists()
        composeRule.onNodeWithTag("difficultyCLASSIC").assertExists()
        composeRule.onNodeWithTag("difficultyWILD").assertExists()
        composeRule.onNodeWithTag("birdCharacterSUNNY").assertExists()
        composeRule.onNodeWithTag("birdCharacterBERRY").assertExists()
        composeRule.onNodeWithTag("birdCharacterMINT").assertExists()
        composeRule.onNodeWithTag("birdCharacterEMBER").assertExists()
    }

    @Test
    fun characterSelectionPersistsAndUpdatesTheLaunchMenu() {
        composeRule.mainClock.autoAdvance = false
        val preferences = composeRule.activity.getSharedPreferences("sky_hopper_preferences", android.content.Context.MODE_PRIVATE)

        composeRule.onNodeWithTag("birdCharacterBERRY").performClick()
        composeRule.mainClock.advanceTimeByFrame()
        composeRule.onNodeWithTag("selectedBirdLabel").assertTextContains("BERRY")
        composeRule.runOnIdle {
            assertEquals("BERRY", preferences.getString("bird_character", ""))
        }

        composeRule.onNodeWithTag("birdCharacterMINT").performClick()
        composeRule.mainClock.advanceTimeByFrame()
        composeRule.onNodeWithTag("selectedBirdLabel").assertTextContains("MINT")
        composeRule.runOnIdle {
            assertEquals("MINT", preferences.getString("bird_character", ""))
        }

        // Restore the classic gold bird so other tests start from the default.
        composeRule.onNodeWithTag("birdCharacterSUNNY").performClick()
        composeRule.mainClock.advanceTimeByFrame()
        composeRule.runOnIdle {
            assertEquals("SUNNY", preferences.getString("bird_character", ""))
        }
    }

    @Test
    fun difficultySelectionPersistsAndCanReturnToClassic() {
        composeRule.mainClock.autoAdvance = false
        val preferences = composeRule.activity.getSharedPreferences("sky_hopper_preferences", android.content.Context.MODE_PRIVATE)

        composeRule.onNodeWithTag("difficultyWILD").performClick()
        composeRule.mainClock.advanceTimeByFrame()
        composeRule.runOnIdle {
            assertEquals("WILD", preferences.getString("flight_difficulty", ""))
        }

        composeRule.onNodeWithTag("difficultyCHILL").performClick()
        composeRule.mainClock.advanceTimeByFrame()
        composeRule.runOnIdle {
            assertEquals("CHILL", preferences.getString("flight_difficulty", ""))
        }

        // Restore Classic so later flow tests have predictable, balanced physics.
        composeRule.onNodeWithTag("difficultyCLASSIC").performClick()
        composeRule.mainClock.advanceTimeByFrame()
        composeRule.runOnIdle {
            assertEquals("CLASSIC", preferences.getString("flight_difficulty", ""))
        }
    }

    @Test
    fun soundToggleUpdatesAndPersistsItsPreference() {
        composeRule.mainClock.autoAdvance = false
        val preferences = composeRule.activity.getSharedPreferences("sky_hopper_preferences", android.content.Context.MODE_PRIVATE)
        val originalValue = preferences.getBoolean("sound_enabled", true)

        composeRule.onNodeWithTag("soundToggle").performClick()
        composeRule.mainClock.advanceTimeByFrame()
        composeRule.runOnIdle {
            assertEquals(!originalValue, preferences.getBoolean("sound_enabled", true))
        }

        composeRule.onNodeWithTag("soundToggle").performClick()
        composeRule.mainClock.advanceTimeByFrame()
        composeRule.runOnIdle {
            assertEquals(originalValue, preferences.getBoolean("sound_enabled", true))
        }
    }

    @Test
    fun howToPlayOverlayCanBeOpenedAndDismissed() {
        composeRule.mainClock.autoAdvance = false

        composeRule.onNodeWithTag("howToPlayButton").performClick()
        composeRule.mainClock.advanceTimeByFrame()
        composeRule.onNodeWithTag("howToPlayCloseButton").assertExists()

        composeRule.onNodeWithTag("howToPlayCloseButton").performClick()
        composeRule.mainClock.advanceTimeByFrame()
        composeRule.onNodeWithTag("playButton").assertExists()
    }

    @Test
    fun systemBackPausesFlightAndReturnsToMenuFromPause() {
        composeRule.mainClock.autoAdvance = false

        composeRule.onNodeWithTag("playButton").performClick()
        composeRule.mainClock.advanceTimeByFrame()
        composeRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }
        composeRule.mainClock.advanceTimeByFrame()
        composeRule.onNodeWithTag("resumeButton").assertExists()

        composeRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }
        composeRule.mainClock.advanceTimeByFrame()
        composeRule.onNodeWithTag("playButton").assertExists()
        composeRule.onNodeWithTag("resumeButton").assertDoesNotExist()
    }

    @Test
    fun backgroundingAppPausesActiveFlight() {
        composeRule.mainClock.autoAdvance = false

        composeRule.onNodeWithTag("playButton").performClick()
        composeRule.mainClock.advanceTimeByFrame()
        composeRule.activityRule.scenario.moveToState(Lifecycle.State.STARTED)
        composeRule.activityRule.scenario.moveToState(Lifecycle.State.RESUMED)
        composeRule.mainClock.advanceTimeByFrame()

        composeRule.onNodeWithTag("resumeButton").assertExists()
        composeRule.onNodeWithTag("scoreHud").assertDoesNotExist()
    }

    @Test
    fun playerCanStartPauseAndResumeAFlight() {
        composeRule.mainClock.autoAdvance = false

        composeRule.onNodeWithTag("playButton").performClick()
        composeRule.mainClock.advanceTimeByFrame()
        composeRule.onNodeWithTag("scoreHud").assertExists()
        composeRule.onNodeWithTag("readyTitle").assertDoesNotExist()

        composeRule.onNodeWithTag("pauseButton").performClick()
        composeRule.mainClock.advanceTimeByFrame()
        composeRule.onNodeWithTag("resumeButton").assertExists()
        composeRule.onNodeWithTag("scoreHud").assertDoesNotExist()

        composeRule.onNodeWithTag("resumeButton").performClick()
        composeRule.mainClock.advanceTimeByFrame()
        composeRule.onNodeWithTag("scoreHud").assertExists()
    }

    @Test
    fun playerCanContinueOnceAfterGameOver() {
        composeRule.mainClock.autoAdvance = false

        composeRule.onNodeWithTag("playButton").performClick()
        composeRule.mainClock.advanceTimeBy(1800L)

        composeRule.onNodeWithTag("continueButton").assertExists()
        composeRule.onNodeWithTag("continueButton").performClick()
        composeRule.mainClock.advanceTimeByFrame()
        composeRule.onNodeWithTag("scoreHud").assertExists()

        composeRule.mainClock.advanceTimeBy(1800L)
        composeRule.onNodeWithTag("continueButton").assertDoesNotExist()
        composeRule.onNodeWithTag("flyAgainButton").assertExists()
    }
}
