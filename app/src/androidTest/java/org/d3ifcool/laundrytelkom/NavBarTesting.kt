package org.d3ifcool.laundrytelkom

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.d3ifcool.laundrytelkom.ui.home.HomeActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavBarTesting {
    @Test
    fun testNavbar() {
        val activityScenario = ActivityScenario.launch(
            HomeActivity::class.java
        )
//        Lakukan Aksi

//      Goes To Testing
        onView(withId(R.id.navigation_home)).perform(click())
        onView(withId(R.id.navigation_profile)).perform(click())
        pressBack()
        onView(withId(R.id.logout_account)).perform(click())
        Thread.sleep(3000)
        activityScenario.close()
    }
}