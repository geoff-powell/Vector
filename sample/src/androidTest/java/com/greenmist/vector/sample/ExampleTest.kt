package com.greenmist.vector.sample

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import com.greenmist.vector.sample.screens.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by geoff.powell on 11/29/17.
 */
class ExampleTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Before
    fun setup() {
    }

    @Test
    fun testGetSvg() {
        onView(withId(R.id.get_svg))
                .check(matches(isDisplayed()))
                .perform(click())
    }
}