package com.example.randommovie.ui.utils

import android.content.Intent
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.example.randommovie.presentation.MainActivity
import com.example.randommovie.presentation.StartScreenActivity
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule


open class BaseTest : TestCase() {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule<MainActivity>(createIntent())

    private fun createIntent() = Intent(
        InstrumentationRegistry.getInstrumentation().targetContext,
        StartScreenActivity::class.java
    ).apply {
    }
}