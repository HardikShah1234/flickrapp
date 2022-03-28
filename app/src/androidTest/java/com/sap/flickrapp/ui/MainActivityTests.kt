package com.sap.flickrapp.ui

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sap.flickrapp.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTests {
    @Rule
    @JvmField
    var activityRule = ActivityScenarioRule<MainActivity>(MainActivity::class.java)

    private val searchTerm = "Heidelberg"

    @Test
    fun checkSearchBox() {
        Espresso.onView(withId(R.id.simpleSearchView)).perform(ViewActions.typeText(searchTerm))
    }
}