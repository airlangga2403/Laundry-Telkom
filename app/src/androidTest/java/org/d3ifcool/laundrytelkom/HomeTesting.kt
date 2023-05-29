package org.d3ifcool.laundrytelkom

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.d3ifcool.laundrytelkom.ui.home.HomeActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeTesting {

    @Test
    fun testHome() {
        val activityScenario = ActivityScenario.launch(HomeActivity::class.java)

        onView(withId(R.id.satuan)).perform(click())
        pressBack()
        onView(withId(R.id.karpet)).perform(click())
        pressBack()
        onView(withId(R.id.setrika)).perform(click())
        pressBack()
//        Sprei
        onView(withId(R.id.ekspress)).perform(click())
        pressBack()
        onView(withId(R.id.question)).perform(click())
        pressBack()
        onView(withId(R.id.maps)).perform(click())

    }
}